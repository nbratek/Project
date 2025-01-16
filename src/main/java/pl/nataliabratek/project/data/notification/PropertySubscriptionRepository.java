package pl.nataliabratek.project.data.notification;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;


public interface PropertySubscriptionRepository extends JpaRepository<PropertySubscriptionEntity, Integer> {
    List<PropertySubscriptionEntity> findAllByUserIdIn(Set<Integer> userIds, Pageable pageable);


    int countByUserIdIn(Set<Integer> userIds);
}
