package pl.nataliabratek.project.data.properties;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.nataliabratek.project.data.users.UserEntity;

import java.util.List;
import java.util.Optional;


public interface PropertyRepository extends JpaRepository<PropertyEntity, Integer> {
    Optional<PropertyEntity> findById(Integer id);
    //@Query("select p from PropertyEntity p")
    //List<PropertyEntity> findAllByFilters(Pageable pageable);

    @Query("select p from PropertyEntity p where (:userId is null or p.userId = :userId)")
    List<PropertyEntity> findAllByUserId(Integer userId, Pageable pageable);

    int countByUserId(int userId);
}
