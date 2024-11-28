package pl.nataliabratek.project.domain.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.nataliabratek.project.api.model.response.PropertyCommentCollectionDto;
import pl.nataliabratek.project.api.model.response.PropertyCommentDto;

import java.util.Set;

@AllArgsConstructor
@Service
public class PropertyCommentService {

    public PropertyCommentDto addComment(Integer userId, Integer propertyId, String message){

    }

    public PropertyCommentCollectionDto getComments(Set<Integer> propertyIds, Integer pageNumber, Integer pageSize){

    }

}
