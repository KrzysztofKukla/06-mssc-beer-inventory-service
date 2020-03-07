package pl.kukla.krzys.msscbeerinventoryservice.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;
import pl.kukla.krzys.brewery.model.BeerOrderDto;
import pl.kukla.krzys.brewery.model.event.AllocateOrderRequestEvent;
import pl.kukla.krzys.brewery.model.event.AllocateOrderResultEvent;
import pl.kukla.krzys.msscbeerinventoryservice.config.JmsConfig;
import pl.kukla.krzys.msscbeerinventoryservice.service.AllocationService;

/**
 * @author Krzysztof Kukla
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class AllocationListener {

    private final AllocationService allocationService;
    private final JmsTemplate jmsTemplate;

    @JmsListener(destination = JmsConfig.ALLOCATE_ORDER_QUEUE)
    public void listen(AllocateOrderRequestEvent allocateOrderRequestEvent) {
        BeerOrderDto beerOrderDto = allocateOrderRequestEvent.getBeerOrderDto();
        Boolean allocatedResult = allocationService.allocateOrder(beerOrderDto);

        AllocateOrderResultEvent allocateOrderResultEvent = AllocateOrderResultEvent.builder()
            .beerOrderDto(beerOrderDto)
            .allocationError(false)
            .pendingInventory(!allocatedResult)
            .build();

        jmsTemplate.convertAndSend(JmsConfig.ALLOCATE_ORDER_RESPONSE_QUEUE, allocateOrderResultEvent);

        log.debug("Sent allocation order result ");
    }

}
