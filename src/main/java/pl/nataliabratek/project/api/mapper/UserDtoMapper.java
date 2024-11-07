package pl.nataliabratek.project.api.mapper;

import org.springframework.stereotype.Service;
import pl.nataliabratek.project.api.model.response.UserDto;
import pl.nataliabratek.project.data.users.UserEntity;

@Service
public class UserDtoMapper {
    public UserDto mapToUserDto(UserEntity userEntity){
        UserDto userDto = new UserDto(userEntity.getName(), userEntity.getLastName(), userEntity.getId(), userEntity.getEmail());
        return userDto;
    }
}
