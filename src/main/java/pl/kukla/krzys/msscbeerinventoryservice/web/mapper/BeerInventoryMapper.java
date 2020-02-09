package pl.kukla.krzys.msscbeerinventoryservice.web.mapper;

import org.mapstruct.Mapper;
import pl.kukla.krzys.msscbeerinventoryservice.domain.BeerInventory;
import pl.kukla.krzys.msscbeerinventoryservice.web.model.BeerInventoryDto;

/**
 * @author Krzysztof Kukla
 */
@Mapper(uses = {DateMapper.class})
public interface BeerInventoryMapper {

    BeerInventory beerInventoryDtoToBeerInventory(BeerInventoryDto beerInventoryDTO);

    BeerInventoryDto beerInventoryToBeerInventoryDto(BeerInventory beerInventory);
}
