package pl.kukla.krzys.msscbeerinventoryservice.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;
import pl.kukla.krzys.brewery.model.BeerOrderDto;
import pl.kukla.krzys.brewery.model.event.DeallocateOrderRequestEvent;
import pl.kukla.krzys.msscbeerinventoryservice.config.JmsConfig;
import pl.kukla.krzys.msscbeerinventoryservice.service.AllocationService;

/**
 * @author Krzysztof Kukla
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class DeallocationListener {

    private final AllocationService allocationService;

    @JmsListener(destination = JmsConfig.DEALLOCATE_ORDER_QUEUE)
    public void listen(Message<DeallocateOrderRequestEvent> message){
        log.debug("Listening on {}",JmsConfig.DEALLOCATE_ORDER_QUEUE);

        DeallocateOrderRequestEvent deallocateOrderRequestEvent = message.getPayload();

        BeerOrderDto beerOrderDto = deallocateOrderRequestEvent.getBeerOrderDto();

        allocationService.deallocateOrder(beerOrderDto);
    }
}
