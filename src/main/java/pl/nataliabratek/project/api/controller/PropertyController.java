package pl.nataliabratek.project.api.controller;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;
import pl.nataliabratek.project.api.model.request.CreateOrUpdatePropertyDto;
import pl.nataliabratek.project.api.model.response.PropertyCollectionDto;
import pl.nataliabratek.project.api.model.response.PropertyFavoritesIdsCollectionDto;
import pl.nataliabratek.project.api.model.response.UserDto;
import pl.nataliabratek.project.api.model.response.PropertyDto;
import pl.nataliabratek.project.api.utils.ParameterUtils;
import pl.nataliabratek.project.domain.model.PropertyType;
import pl.nataliabratek.project.domain.service.PropertyService;
import pl.nataliabratek.project.domain.service.TokenService;

import java.lang.reflect.Array;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

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
        PropertyDto dto = propertyService.createProperty(userId, body.getTitle(), body.getPrice(), body.getDescription(), body.getLocation(), body.getType());
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


    @PutMapping("/{propertyId}/favorites")
    public ResponseEntity<Void> addToFavorites(
            @PathVariable(value = "propertyId") Integer propertyId,
            @RequestHeader(name="Authorization") String token
    ){
        Integer userId = tokenService.getUserId(token);
        Objects.requireNonNull(userId);
        propertyService.addToFavorites(userId, propertyId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/{propertyId}/favorites")
    public ResponseEntity<Void> deleteFromFavorites(
            @PathVariable(value = "propertyId") Integer propertyId,
            @RequestHeader(name="Authorization") String token) {
        Integer userId = tokenService.getUserId(token);
        Objects.requireNonNull(userId);
        propertyService.deleteFromFavorites(userId, propertyId);
        return ResponseEntity.noContent().build();

    }

    @GetMapping("/favourites")
    public ResponseEntity<PropertyFavoritesIdsCollectionDto> getFavoritePropertyIds(
            @RequestHeader(name = "Authorization") String token,
            @RequestParam(value = "filterByPropertyId", required = false) @Nullable Set<String> propertyIdsStrings) {
        Integer userId = tokenService.getUserId(token);
        Objects.requireNonNull(userId);


        Set<Integer> filterByPropertyIds = propertyIdsStrings.stream()
                .map(s -> ParameterUtils.convertToInteger(s))
                .filter(intOpt -> intOpt.isPresent())
                .map(intOpt -> intOpt.get())
                .collect(Collectors.toSet());


//        Set<Integer> propertyIds2 = new HashSet<>();
//        for (String s : propertyIdsStrings){
//            Optional<Integer> intOpt = ParameterUtils.convertToInteger(s);
//            if (intOpt.isPresent()){
//                propertyIds2.add(intOpt.get());
//            }
//        }
        Set<Integer> favoritePropertyIds = propertyService.getFavoritePropertyIds(userId, filterByPropertyIds);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new PropertyFavoritesIdsCollectionDto(favoritePropertyIds));
    }


}

