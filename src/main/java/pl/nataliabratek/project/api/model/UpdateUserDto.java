package pl.nataliabratek.project.api.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UpdateUserDto {
    private String name;
    private String lastName;

}