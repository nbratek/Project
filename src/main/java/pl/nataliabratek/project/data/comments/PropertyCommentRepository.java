package pl.nataliabratek.project.data.comments;

import io.micrometer.common.lang.Nullable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.nataliabratek.project.data.properties.PropertyEntity;

import java.util.List;
import java.util.Set;


public interface PropertyCommentRepository extends JpaRepository<PropertyCommentEntity, Integer> {

    @Query("select p from PropertyCommentEntity p where p.propertyId IN :propertyIds")
    List<PropertyCommentEntity> findByPropertyIdIn(Set<Integer> propertyIds, Pageable pageable);

    @Query("select count (p) from PropertyCommentEntity p where p.propertyId IN :propertyIds")
    int countByPropertyIdIn(Set<Integer> propertyIds);
}