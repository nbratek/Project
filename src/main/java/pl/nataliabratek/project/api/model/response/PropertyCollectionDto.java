package pl.nataliabratek.project.api.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class PropertyCollectionDto {
    private List<PropertyDto> properties;
    private Integer totalCount;
}
