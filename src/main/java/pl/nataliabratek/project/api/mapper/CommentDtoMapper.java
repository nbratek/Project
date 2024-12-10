package pl.nataliabratek.project.api.mapper;

import org.springframework.stereotype.Service;
import pl.nataliabratek.project.api.model.response.PropertyCommentDto;
import pl.nataliabratek.project.api.model.response.PropertyDto;
import pl.nataliabratek.project.api.utils.DateUtils;
import pl.nataliabratek.project.data.comments.PropertyCommentEntity;
import pl.nataliabratek.project.data.properties.PropertyEntity;
import pl.nataliabratek.project.data.users.UserEntity;

@Service
public class CommentDtoMapper {
    public PropertyCommentDto mapToPropertyCommentDto(PropertyCommentEntity propertyCommentEntity, UserEntity userEntity){
        return new PropertyCommentDto(
                propertyCommentEntity.getUserId(),
                userEntity.getName(),
                userEntity.getLastName(),
                propertyCommentEntity.getPropertyId(),
                propertyCommentEntity.getMessage(),
                DateUtils.format(propertyCommentEntity.getCreatedAt()));
    }
}

