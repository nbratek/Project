package pl.nataliabratek.project.api.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
public class PropertyDto {
    private Integer id;
    private String title;
    private BigDecimal price;
    private String description;
    private String createdAt;

}
