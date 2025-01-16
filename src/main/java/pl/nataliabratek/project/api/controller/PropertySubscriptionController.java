package pl.nataliabratek.project.api.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;
import pl.nataliabratek.project.api.model.request.CreatePropertySubscriptionDto;
import pl.nataliabratek.project.api.model.response.PropertySubscriptionCollectionDto;
import pl.nataliabratek.project.api.model.response.PropertySubscriptionDto;
import pl.nataliabratek.project.api.utils.ParameterUtils;
import pl.nataliabratek.project.domain.service.PropertySubscriptionService;
import pl.nataliabratek.project.domain.service.TokenService;

import java.util.Objects;
import java.util.Set;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/properties/notifications/subscription")
public class PropertySubscriptionController {

    private TokenService tokenService;
    private PropertySubscriptionService propertySubscriptionService;

    @PostMapping
    public ResponseEntity<PropertySubscriptionDto> createSubscription(@RequestBody CreatePropertySubscriptionDto body,
                                                                      @RequestHeader(name = "Authorization") String token) {
        Integer userId = tokenService.getUserId(token);
        Objects.requireNonNull(userId);
        PropertySubscriptionDto notificationDto = propertySubscriptionService.createSubscription(body, userId);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(notificationDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSubscription(
            @PathVariable(value = "id") Integer id
    ) {
        propertySubscriptionService.deleteSubscription(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<PropertySubscriptionCollectionDto> getSubsciptions(
            @RequestParam(value = "filterByUserId", required = false) @Nullable Set<String> userIds,
            @RequestParam(value = "pageNumber", required = false) @Nullable String pageNumber,
            @RequestParam(value = "pageSize", required = false) @Nullable String pageSize) {

        Set<Integer> userIdsInteger = ParameterUtils.convertToSetInteger(userIds);
        Integer pageNumberInt = ParameterUtils.convertToInteger(pageNumber).orElse(0);
        Integer pageSizeInt = ParameterUtils.convertToInteger(pageSize).orElse(50);
        PropertySubscriptionCollectionDto propertySubscriptionCollectionDto = propertySubscriptionService.getSubscriptions(userIdsInteger, pageNumberInt, pageSizeInt);
        return ResponseEntity.status(HttpStatus.OK)
                .body(propertySubscriptionCollectionDto);
    }
}
