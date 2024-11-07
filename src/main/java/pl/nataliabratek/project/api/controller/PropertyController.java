package pl.nataliabratek.project.api.controller;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;
import pl.nataliabratek.project.api.model.request.CreateOrUpdatePropertyDto;
import pl.nataliabratek.project.api.model.response.PropertyCollectionDto;
import pl.nataliabratek.project.api.model.response.UserDto;
import pl.nataliabratek.project.api.model.response.PropertyDto;
import pl.nataliabratek.project.api.utils.ParameterUtils;
import pl.nataliabratek.project.domain.service.PropertyService;
import pl.nataliabratek.project.domain.service.TokenService;

import java.util.Objects;
import java.util.Optional;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/properties")
public class PropertyController {
    private PropertyService propertyService;
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity<PropertyDto> createProperty(@RequestBody CreateOrUpdatePropertyDto body,
                                                      @RequestHeader(name="Authorization") String token) {
        Integer userId = tokenService.getUserId(token);
        Objects.requireNonNull(userId);
        PropertyDto dto = propertyService.createProperty(userId, body.getTitle(), body.getPrice(), body.getDescription());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(dto);
    }

    @GetMapping
    public ResponseEntity<PropertyCollectionDto> getProperties(
            @RequestParam(value = "filterByUserId", required = false) @Nullable String userId,
            @RequestParam(value = "pageNumber", required = false) @Nullable String pageNumber,
            @RequestParam(value = "pageSize", required = false) @Nullable String pageSize
    ) {
        Integer userIdInt = ParameterUtils.convertToInteger(userId).orElse(null);
        Integer pageNumberInt = ParameterUtils.convertToInteger(pageNumber).orElse(0);
        Integer pageSizeInt = ParameterUtils.convertToInteger(pageSize).orElse(50);
        PropertyCollectionDto dto = propertyService.getProperties(userIdInt, pageNumberInt, pageSizeInt);
        return ResponseEntity.status(HttpStatus.OK).body(dto);
    }


    @GetMapping("/{id}")
    public ResponseEntity<PropertyDto> getPropertyById(
            @PathVariable(value = "id") Integer id) {
        PropertyDto dto = propertyService.getPropertyById(id);
        return ResponseEntity.status(HttpStatus.OK).body(dto);
    }


    @PutMapping("/{id}")
    public ResponseEntity<PropertyDto> updateProperty(
            @PathVariable Integer id,
            @RequestBody CreateOrUpdatePropertyDto body) {
        PropertyDto propertyDto = propertyService.updateProperty(id, body);
        return ResponseEntity.status(HttpStatus.OK).body(propertyDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProperty(
            @PathVariable(value = "id") Integer id) {
        propertyService.deleteProperty(id);
        return ResponseEntity.noContent().build();
    }



//jak zapisac do bazy obiekt
}

