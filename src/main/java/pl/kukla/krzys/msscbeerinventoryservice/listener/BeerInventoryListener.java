package pl.kukla.krzys.msscbeerinventoryservice.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import pl.kukla.krzys.common.events.BeerDto;
import pl.kukla.krzys.common.events.NewInventoryEvent;
import pl.kukla.krzys.msscbeerinventoryservice.config.JmsConfig;
import pl.kukla.krzys.msscbeerinventoryservice.domain.BeerInventory;
import pl.kukla.krzys.msscbeerinventoryservice.repository.BeerInventoryRepository;

/**
 * @author Krzysztof Kukla
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class BeerInventoryListener {
    private final BeerInventoryRepository beerInventoryRepository;

    @JmsListener(destination = JmsConfig.NEW_INVENTORY_QUEUE)
    public void listen(NewInventoryEvent newInventoryEvent) {
        log.debug("Got inventory: " + newInventoryEvent.toString());

        BeerDto beerDto = newInventoryEvent.getBeerDto();
        BeerInventory beerInventory = BeerInventory.builder()
            .beerId(beerDto.getId())
            .upc(beerDto.getUpc())
            .quantityOnHand(beerDto.getQuantityOnHand())
            .build();

        beerInventoryRepository.save(beerInventory);
    }

}
