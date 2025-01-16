package pl.nataliabratek.project.domain.service;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import pl.nataliabratek.project.api.mapper.PropertySubscriptionDtoMapper;
import pl.nataliabratek.project.api.model.request.CreatePropertySubscriptionDto;
import pl.nataliabratek.project.api.model.response.PropertySubscriptionCollectionDto;
import pl.nataliabratek.project.api.model.response.PropertySubscriptionDto;
import pl.nataliabratek.project.data.notification.PropertySubscriptionEntity;
import pl.nataliabratek.project.data.notification.PropertySubscriptionRepository;
import pl.nataliabratek.project.domain.exception.NotFoundException;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class PropertySubscriptionService {
    private PropertySubscriptionRepository propertySubscriptionRepository;
    private PropertySubscriptionDtoMapper propertySubscriptionDtoMapper;

    public PropertySubscriptionDto createSubscription(CreatePropertySubscriptionDto dto, Integer userId){
        PropertySubscriptionEntity propertySubscriptionEntity = new PropertySubscriptionEntity(null, dto.getLocation(), dto.getPropertyType(), userId);
        propertySubscriptionRepository.save(propertySubscriptionEntity);
        return propertySubscriptionDtoMapper.mapToPropertySubscriptionDto(propertySubscriptionEntity);

    }

    public void deleteSubscription(Integer subscriptionId){
        propertySubscriptionRepository.findById(subscriptionId)
                .orElseThrow(NotFoundException::new);
        propertySubscriptionRepository.deleteById(subscriptionId);
    }

    public PropertySubscriptionCollectionDto getSubscriptions(Set<Integer> userIds, Integer pageNumber, Integer pageSize){
        Sort sort = Sort.sort(PropertySubscriptionEntity.class)
                .by(PropertySubscriptionEntity::getId)
                .ascending();
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        List<PropertySubscriptionEntity> subscriptionEntities = propertySubscriptionRepository.findAllByUserIdIn(userIds, pageable);
        int totalCount = propertySubscriptionRepository.countByUserIdIn(userIds);
        List<PropertySubscriptionDto> propertySubscriptionDtos = subscriptionEntities.stream()
                .map(it -> propertySubscriptionDtoMapper.mapToPropertySubscriptionDto(it))
                .collect(Collectors.toList());

        return new PropertySubscriptionCollectionDto(propertySubscriptionDtos, totalCount);
    }
}
