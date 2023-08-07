package repository;

import DTO.shopSearchCriteria;
import entity.shopInformation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface shopInformationRepository extends JpaRepository<shopInformation, Long> {

    @Query("SELECT s FROM shopInformation s WHERE (:name IS NULL OR s.name LIKE %:name%) " +
            "AND (:location IS NULL OR s.location LIKE %:location%)")
    List<shopInformation> findShopsByCriteria(shopSearchCriteria criteria);
}
