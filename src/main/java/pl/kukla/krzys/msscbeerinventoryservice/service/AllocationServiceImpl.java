package pl.kukla.krzys.msscbeerinventoryservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.kukla.krzys.brewery.model.BeerInventoryDto;
import pl.kukla.krzys.brewery.model.BeerOrderDto;
import pl.kukla.krzys.brewery.model.BeerOrderLineDto;
import pl.kukla.krzys.msscbeerinventoryservice.domain.BeerInventory;
import pl.kukla.krzys.msscbeerinventoryservice.repository.BeerInventoryRepository;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Krzysztof Kukla
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class AllocationServiceImpl implements AllocationService {

    private final BeerInventoryRepository beerInventoryRepository;

    @Override
    public Boolean allocateOrder(BeerOrderDto beerOrderDto) {
        log.debug("Allocating OrderId: {}", beerOrderDto.getId());

        AtomicInteger totalOrdered = new AtomicInteger();
        AtomicInteger totalAllocated = new AtomicInteger();

        beerOrderDto.getBeerOrderLines().forEach(beerOrderLine -> {
            int orderQuantity = beerOrderLine.getOrderQuantity() != null ? beerOrderLine.getOrderQuantity() : 0;
            int quantityAllocated = beerOrderLine.getQuantityAllocated() != null ? beerOrderLine.getQuantityAllocated() : 0;
            if ((orderQuantity - quantityAllocated) > 0) {
                allocateBeerOrderLine(beerOrderLine);
            }
            totalOrdered.set(totalOrdered.get() + beerOrderLine.getOrderQuantity());
            totalAllocated.set(totalAllocated.get() + (beerOrderLine.getQuantityAllocated() != null ? beerOrderLine.getQuantityAllocated() : 0));
        });

        log.debug("Total Ordered: {}, Total Allocated: {}", totalOrdered.get(), totalAllocated.get());

        return totalOrdered.get() == totalAllocated.get();
    }

    @Override
    public void deallocateOrder(BeerOrderDto beerOrderDto) {
        beerOrderDto.getBeerOrderLines().forEach(line->{
            BeerInventory beerInventory = BeerInventory.builder()
                .beerId(line.getId())
                .upc(line.getUpc())
                .quantityOnHand(line.getOrderQuantity())
                .build();

            BeerInventory savedBeerInventory = beerInventoryRepository.save(beerInventory);

            log.debug("Saved Inventory for beerUpc->{}, inventoryId->{}",savedBeerInventory.getUpc(), savedBeerInventory.getId());
        });
    }

    private void allocateBeerOrderLine(BeerOrderLineDto beerOrderLine) {
        List<BeerInventory> beerInventoryList = beerInventoryRepository.findAllByUpc(beerOrderLine.getUpc());

        beerInventoryList.forEach(beerInventory -> {
            int inventory = (beerInventory.getQuantityOnHand() == null) ? 0 : beerInventory.getQuantityOnHand();
            int orderQty = (beerOrderLine.getOrderQuantity() == null) ? 0 : beerOrderLine.getOrderQuantity();
            int allocatedQty = (beerOrderLine.getQuantityAllocated() == null) ? 0 : beerOrderLine.getQuantityAllocated();
            int qtyToAllocate = orderQty - allocatedQty;

            if (inventory >= qtyToAllocate) { // full allocation
                inventory = inventory - qtyToAllocate;
                beerOrderLine.setQuantityAllocated(orderQty);
                beerInventory.setQuantityOnHand(inventory);

                beerInventoryRepository.save(beerInventory);
            } else if (inventory > 0) { //partial allocation
                beerOrderLine.setQuantityAllocated(allocatedQty + inventory);
                beerInventory.setQuantityOnHand(0);

                beerInventoryRepository.delete(beerInventory);
            }
        });

    }

}
