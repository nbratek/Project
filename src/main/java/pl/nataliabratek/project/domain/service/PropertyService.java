package pl.nataliabratek.project.domain.service;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import pl.nataliabratek.project.api.mapper.PropertyDtoMapper;
import pl.nataliabratek.project.api.model.request.CreateOrUpdatePropertyDto;
import pl.nataliabratek.project.api.model.response.PropertyCollectionDto;
import pl.nataliabratek.project.api.model.response.PropertyDto;
import pl.nataliabratek.project.data.properties.PropertyEntity;
import pl.nataliabratek.project.data.properties.PropertyRepository;
import pl.nataliabratek.project.data.users.UserEntity;
import pl.nataliabratek.project.domain.exception.NotFoundException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class PropertyService {
    private PropertyRepository propertyRepository;
    private PropertyDtoMapper propertyDtoMapper;

    public PropertyDto createProperty(Integer userId, String title, BigDecimal price, String description) {
        PropertyEntity propertyEntity = new PropertyEntity(null, title, userId, price, description, LocalDateTime.now());
        propertyEntity = propertyRepository.save(propertyEntity);
        PropertyDto propertyDto = propertyDtoMapper.mapToPropertyDto(propertyEntity);
        return propertyDto;

    }

    public PropertyCollectionDto getProperties(@Nullable Integer userId, Integer pageNumber, Integer pageSize) {
        Sort sort = Sort.sort(PropertyEntity.class)
                .by(PropertyEntity::getId)
                .ascending();
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        List<PropertyDto> propertyDtos;
        int totalCount;
        if (userId == null) {
            propertyDtos = propertyRepository.findAllByFilters(pageable)
                    .stream()
                    .map(propertyEntity -> propertyDtoMapper.mapToPropertyDto(propertyEntity))
                    .collect(Collectors.toList());
            totalCount = (int) propertyRepository.count();
        } else{
            propertyDtos = propertyRepository.findAllByUserId(userId, pageable)
                    .stream()
                    .map(propertyEntity -> propertyDtoMapper.mapToPropertyDto(propertyEntity))
                    .collect(Collectors.toList());
            totalCount = propertyRepository.countByUserId(userId);

        }
        PropertyCollectionDto dto = new PropertyCollectionDto(propertyDtos, totalCount);
        return dto;
    }
    //uproscic, query

    public PropertyDto getPropertyById(Integer id) {
        return propertyRepository.findById(id)
                .map(propertyDtoMapper::mapToPropertyDto)
                .orElseThrow(NotFoundException::new);
    }

    public PropertyDto updateProperty(Integer id, CreateOrUpdatePropertyDto body) {
        PropertyEntity propertyEntity = propertyRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        propertyEntity.setDescription(body.getDescription());
        propertyEntity.setTitle(body.getTitle());
        propertyEntity.setPrice(body.getPrice());
        propertyEntity = propertyRepository.save(propertyEntity);
        return propertyDtoMapper.mapToPropertyDto(propertyEntity);
    }


    public void deleteProperty(Integer id) {
        // mozna zmienic na exist
        propertyRepository.findById(id)
                .orElseThrow(NotFoundException::new);

        propertyRepository.deleteById(id);
    }

}
