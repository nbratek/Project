package pl.nataliabratek.project.api.mapper;

import org.springframework.stereotype.Service;
import pl.nataliabratek.project.api.model.response.PropertySubscriptionDto;
import pl.nataliabratek.project.data.notification.PropertySubscriptionEntity;

@Service
public class PropertySubscriptionDtoMapper {

    public PropertySubscriptionDto mapToPropertySubscriptionDto(PropertySubscriptionEntity propertySubscriptionEntity){
        return new PropertySubscriptionDto(
                propertySubscriptionEntity.getId(),
                propertySubscriptionEntity.getLocation(),
                propertySubscriptionEntity.getPropertyType().name());
    }
}
