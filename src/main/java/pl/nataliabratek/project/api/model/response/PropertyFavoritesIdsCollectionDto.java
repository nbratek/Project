package pl.nataliabratek.project.api.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@AllArgsConstructor
@Getter
@Setter
public class PropertyFavoritesIdsCollectionDto {
    private Set<Integer> favoritesPropertyIds;
}
