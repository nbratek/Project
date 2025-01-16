package pl.nataliabratek.project.api.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import pl.nataliabratek.project.domain.model.PropertyType;

@Getter
@Setter
@AllArgsConstructor
public class CreatePropertySubscriptionDto {
    private String location;
    private PropertyType propertyType;

}
