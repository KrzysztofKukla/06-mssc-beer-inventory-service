package pl.kukla.krzys.msscbeerinventoryservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.kukla.krzys.msscbeerinventoryservice.domain.BeerInventory;

import java.util.List;
import java.util.UUID;

/**
 * @author Krzysztof Kukla
 */
public interface BeerInventoryRepository extends JpaRepository<BeerInventory, UUID> {

    List<BeerInventory> findAllByBeerId(UUID beerId);
}