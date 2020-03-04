package pl.kukla.krzys.brewery.model.event;

import lombok.NoArgsConstructor;

/**
 * @author Krzysztof Kukla
 */
//Jackson wants to have @NoArgsConstructor
@NoArgsConstructor
public class NewInventoryEvent extends BeerEvent {
    public NewInventoryEvent(BeerDto beerDto) {
        super(beerDto);
    }

}
