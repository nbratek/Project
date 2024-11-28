package pl.nataliabratek.project.data.favorites;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.Set;

public interface PropertyFavoritesRepository extends JpaRepository<PropertyFavoritesEntity, Integer> {
    Optional<PropertyFavoritesEntity> findByUserIdAndPropertyId(Integer userId, Integer propertyId);

    @Query("select p.propertyId from PropertyFavoritesEntity p where p.userId = :userId and p.propertyId in :propertyIds")
    Set<Integer> findAllPropertyIdsByUserIdAndPropertyIdIn(Integer userId, Set<Integer> propertyIds);

    boolean existsByUserIdAndPropertyId(Integer userId, Integer propertyId);
}
