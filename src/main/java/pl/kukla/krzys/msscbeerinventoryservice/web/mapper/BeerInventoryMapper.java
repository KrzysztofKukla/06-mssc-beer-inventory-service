package pl.kukla.krzys.msscbeerinventoryservice.web.mapper;

import org.mapstruct.Mapper;
import pl.kukla.krzys.brewery.model.BeerInventoryDto;
import pl.kukla.krzys.msscbeerinventoryservice.domain.BeerInventory;

/**
 * @author Krzysztof Kukla
 */
@Mapper(uses = {DateMapper.class})
public interface BeerInventoryMapper {

    BeerInventory beerInventoryDtoToBeerInventory(BeerInventoryDto beerInventoryDTO);

    BeerInventoryDto beerInventoryToBeerInventoryDto(BeerInventory beerInventory);
}
