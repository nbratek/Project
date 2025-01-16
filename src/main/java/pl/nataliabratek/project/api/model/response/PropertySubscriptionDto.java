package pl.nataliabratek.project.api.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PropertySubscriptionDto {

    private Integer id;
    private String location;
    private String propertyType;


}
