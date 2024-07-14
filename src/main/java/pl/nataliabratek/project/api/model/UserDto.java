package pl.nataliabratek.project.api.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserDto {
    private String name;
    private String lastName;
    private Integer id;

}
