package pl.nataliabratek.project.api.mapper;

import org.springframework.stereotype.Service;
import pl.nataliabratek.project.api.model.UserDto;
import pl.nataliabratek.project.data.users.UserEntity;

@Service
public class UserMapper {
    public UserDto mapToUserDto(UserEntity userEntity){
        UserDto userDto = new UserDto(userEntity.getName(), userEntity.getLastName(), userEntity.getId());
        return userDto;
    }
}
