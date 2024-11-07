package pl.nataliabratek.project.api.mapper;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.nataliabratek.project.api.model.response.PropertyDto;
import pl.nataliabratek.project.api.model.response.UserDto;
import pl.nataliabratek.project.api.utils.DateUtils;
import pl.nataliabratek.project.data.properties.PropertyEntity;
import pl.nataliabratek.project.data.users.UserEntity;

@Service
public class PropertyDtoMapper {
    public PropertyDto mapToPropertyDto(PropertyEntity propertyEntity){
        return new PropertyDto(propertyEntity.getId(),
                propertyEntity.getTitle(),
                propertyEntity.getPrice(),
                propertyEntity.getDescription(),
                DateUtils.format(propertyEntity.getCreatedAt()));
    }
}
