package pl.nataliabratek.project.api.model.response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Setter
@Getter
public class PropertySubscriptionCollectionDto {

    private List<PropertySubscriptionDto> notification;
    private Integer totalCounts;
}
