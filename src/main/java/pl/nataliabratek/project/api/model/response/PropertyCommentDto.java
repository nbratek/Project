package pl.nataliabratek.project.api.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
public class PropertyCommentDto {
    private Integer userId;
    private String userFirstName;
    private String userLastName;
    private Integer propertyId;
    private String message;
    private String createdAt;
}
