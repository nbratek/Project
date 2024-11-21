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
import pl.nataliabratek.project.data.favorites.PropertyFavoritesRepository;
import pl.nataliabratek.project.data.favorites.PropertyFavoritesEntity;
import pl.nataliabratek.project.data.properties.PropertyEntity;
import pl.nataliabratek.project.data.properties.PropertyRepository;
import pl.nataliabratek.project.data.users.UserEntity;
import pl.nataliabratek.project.data.users.UserRepository;
import pl.nataliabratek.project.domain.exception.NotFoundException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class PropertyService {
    private PropertyRepository propertyRepository;
    private PropertyDtoMapper propertyDtoMapper;
    private UserRepository userRepository;
    private PropertyFavoritesRepository favoritesRepository;

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
        List<PropertyEntity> propertyEntities;
        int totalCount;
        if (userId == null) {
            propertyEntities = propertyRepository.findAllByUserId(userId, pageable);
            totalCount = (int) propertyRepository.count();
        } else{
            propertyEntities = propertyRepository.findAllByUserId(userId, pageable);
            totalCount = propertyRepository.countByUserId(userId);

        }
        List<PropertyDto> propertyDtos = propertyEntities.stream()
                .map(propertyEntity -> propertyDtoMapper.mapToPropertyDto( propertyEntity))
                .collect(Collectors.toList());
        return new PropertyCollectionDto(propertyDtos, totalCount);

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

    public void addToFavorites(Integer userId, Integer propertyId){
        UserEntity userEntity = userRepository.findById(userId) //zamienic findById na exist
                .orElseThrow(NotFoundException::new);
        PropertyEntity propertyEntity = propertyRepository.findById(propertyId)
                .orElseThrow(NotFoundException::new);
        PropertyFavoritesEntity favorites = new PropertyFavoritesEntity(null, propertyId, userId);
        favoritesRepository.save(favorites);
    }

    public void deleteFromFavorites(Integer userId, Integer propertyId) {
        PropertyFavoritesEntity favorite = favoritesRepository
                .findByUserIdAndPropertyId(userId, propertyId)
                .orElseThrow(NotFoundException::new);
        favoritesRepository.deleteById(favorite.getId());
    }

    public Set<Integer> getFavoritePropertyIds(Integer userId, Set<Integer> filterByPropertyIds){
        Set<Integer> favoritesPropertyIds = favoritesRepository.findAllPropertyIdsByUserIdAndPropertyIdIn(userId, filterByPropertyIds);
        return favoritesPropertyIds;
    }

}
