package pl.nataliabratek.project.domain.service;

import org.springframework.lang.Nullable;
import pl.nataliabratek.project.api.model.request.CreateOrUpdatePropertyDto;
import pl.nataliabratek.project.api.model.response.PropertyCollectionDto;
import pl.nataliabratek.project.api.model.response.PropertyDto;

import java.math.BigDecimal;

public class PropertyService {

    public PropertyDto createProperty(String title, BigDecimal price, String description) {

    }

    public PropertyCollectionDto getProperties(@Nullable Integer userId, Integer pageNumber, Integer pageSize) {

    }

    public PropertyDto getPropertyById(Integer id) {
    }

    public PropertyDto updateProperty(Integer id, CreateOrUpdatePropertyDto body) {
    }


    public void deleteProperty(Integer id) {

    }

}
