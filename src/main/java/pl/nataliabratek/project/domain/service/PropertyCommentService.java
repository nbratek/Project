package pl.nataliabratek.project.domain.service;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import pl.nataliabratek.project.api.mapper.CommentDtoMapper;
import pl.nataliabratek.project.api.model.response.PropertyCollectionDto;
import pl.nataliabratek.project.api.model.response.PropertyCommentCollectionDto;
import pl.nataliabratek.project.api.model.response.PropertyCommentDto;
import pl.nataliabratek.project.api.model.response.PropertyDto;
import pl.nataliabratek.project.data.comments.PropertyCommentEntity;
import pl.nataliabratek.project.data.comments.PropertyCommentRepository;
import pl.nataliabratek.project.data.properties.PropertyEntity;
import pl.nataliabratek.project.data.properties.PropertyRepository;
import pl.nataliabratek.project.data.users.UserEntity;
import pl.nataliabratek.project.data.users.UserRepository;
import pl.nataliabratek.project.domain.exception.NotFoundException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class PropertyCommentService {
    private UserRepository userRepository;
    private PropertyRepository propertyRepository;
    private PropertyCommentRepository propertyCommentRepository;
    private CommentDtoMapper commentDtoMapper;

    public PropertyCommentDto addComment(Integer userId, Integer propertyId, String message){
        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(NotFoundException::new);

        if (!propertyRepository.existsById(propertyId)){
            throw new NotFoundException();
        }
        PropertyCommentEntity commentEntity = new PropertyCommentEntity(null, userId, propertyId, message, LocalDateTime.now());
        propertyCommentRepository.save(commentEntity);
        PropertyCommentDto propertyCommentDto = commentDtoMapper.mapToPropertyCommentDto(commentEntity, userEntity);
        return propertyCommentDto;
    }

    public PropertyCommentCollectionDto getComments(Set<Integer> propertyIds, Integer pageNumber, Integer pageSize){
        Sort sort = Sort.sort(PropertyCommentEntity.class)
                .by(PropertyCommentEntity::getId)
                .ascending();
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        List<PropertyCommentEntity> commentEntities;
        int totalCount;
        commentEntities = propertyCommentRepository.findByPropertyIdIn(propertyIds, pageable);
        totalCount = propertyCommentRepository.countByPropertyIdIn(propertyIds);


        Set<Integer> userIds = commentEntities.stream()
                .map(comment -> comment.getUserId())
                .collect(Collectors.toSet());

        List<UserEntity> users = userRepository.findAllById(userIds);

//        UserEntity userEntity = users.stream()
//                .filter(userEntity1 -> userEntity1.getId().equals(251))
//                .findFirst()
//                .orElse(null);
        Map<Integer, UserEntity> idToUserMap = users.stream()
                .collect(Collectors.toMap(userEntity -> userEntity.getId(), userEntity -> userEntity));

        List<PropertyCommentDto> commentDtos = commentEntities.stream()
                .map(commentEntity -> commentDtoMapper.mapToPropertyCommentDto(commentEntity,
                        idToUserMap.get(commentEntity.getUserId())))
                .collect(Collectors.toList());
        return new PropertyCommentCollectionDto(commentDtos, totalCount);
    }
    }
