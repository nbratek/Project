package pl.nataliabratek.project.api.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;
import pl.nataliabratek.project.api.model.request.AddPropertyComment;
import pl.nataliabratek.project.api.model.response.PropertyCollectionDto;
import pl.nataliabratek.project.api.model.response.PropertyCommentCollectionDto;
import pl.nataliabratek.project.api.model.response.PropertyCommentDto;
import pl.nataliabratek.project.domain.service.PropertyCommentService;
import pl.nataliabratek.project.domain.service.TokenService;

import java.util.Objects;
import java.util.Set;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/properties")
public class PropertyCommentController {

    private TokenService tokenService;
    private PropertyCommentService propertyCommentService;

    @PostMapping("/comment")
    public ResponseEntity<PropertyCommentDto> addComment(
            @RequestHeader(name="Authorization") String token,
            @RequestBody AddPropertyComment body){

        Integer userId = tokenService.getUserId(token);
        Objects.requireNonNull(userId);

        Integer propertyId = body.getPropertyId();
        String message = body.getMessage();
        PropertyCommentDto propertyCommentDto = propertyCommentService.addComment(userId, propertyId, message);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(propertyCommentDto);
    }

    @GetMapping("/comment")
    public ResponseEntity<PropertyCommentCollectionDto> getComments(
            @RequestParam(value = "filterByPropertyId", required = false) @Nullable Set<String> propertyIds,
            @RequestParam(value = "pageNumber", required = false) @Nullable String pageNumber,
            @RequestParam(value = "pageSize", required = false) @Nullable String pageSize) {

        PropertyCollectionDto propertyCollectionDto = propertyCommentService.getComments(propertyIds, pageNumber, pageSize);
    }
}
