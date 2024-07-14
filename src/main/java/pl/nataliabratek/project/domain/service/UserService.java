package pl.nataliabratek.project.domain.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.nataliabratek.project.api.mapper.UserMapper;
import pl.nataliabratek.project.api.model.UserDto;
import pl.nataliabratek.project.data.users.UserEntity;
import pl.nataliabratek.project.data.users.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserService {
    private UserRepository userRepository;
    private UserMapper userMapper;
    public UserDto createUser(String name, String lastName){
        UserEntity userEntity = new UserEntity(null, name, lastName);
        userRepository.save(userEntity);
        UserDto userDto = userMapper.mapToUserDto(userEntity);
        return userDto;
    }

    public List<UserDto> getUsers(){
        List<UserEntity> userEntities= userRepository.findAll();
        List<UserDto> userDtos = userEntities.stream()
                .map(userEntity -> userMapper.mapToUserDto(userEntity))
                .collect(Collectors.toList());
        return userDtos;
    }
}
