package pl.nataliabratek.project.api.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import pl.nataliabratek.project.domain.model.PropertyType;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
public class CreateOrUpdatePropertyDto {
    private String title;
    private BigDecimal price;
    private String description;
    private String location;
    private PropertyType type;

}
