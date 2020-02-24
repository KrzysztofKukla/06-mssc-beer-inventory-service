package pl.kukla.krzys.common.events;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author Krzysztof Kukla
 */
@Data
@AllArgsConstructor
@Builder
//Jackson wants to have @NoArgsConstructor
@NoArgsConstructor
public class BeerEvent implements Serializable {

    static final long serialVersionUID = -8898740913569738304L;

    private BeerDto beerDto;

}
