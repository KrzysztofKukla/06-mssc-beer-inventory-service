package pl.kukla.krzys.msscbeerinventoryservice.service;

import pl.kukla.krzys.brewery.model.BeerOrderDto;

/**
 * @author Krzysztof Kukla
 */
public interface AllocationService {

    Boolean allocateOrder(BeerOrderDto beerOrderDto);

    void deallocateOrder(BeerOrderDto beerOrderDto);

}
