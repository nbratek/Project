package pl.nataliabratek.project.domain.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pl.nataliabratek.project.api.mapper.UserMapper;
import pl.nataliabratek.project.api.model.UpdateUserDto;
import pl.nataliabratek.project.api.model.UserDto;
import pl.nataliabratek.project.data.users.UserEntity;
import pl.nataliabratek.project.data.users.UserRepository;
import pl.nataliabratek.project.domain.exception.NotFoundException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Getter
public class UserService {
    private UserRepository userRepository;
    private UserMapper userMapper;
    private BCryptPasswordEncoder passwordEncoder;
    private EmailService emailService;

    public UserDto createUser(String name, String lastName, String password, String email) {
        String token = UUID.randomUUID().toString();
        String hashedPassword = hashPassword(password);
        UserEntity userEntity = new UserEntity(null, name, lastName, hashedPassword, email, token);
        userRepository.save(userEntity);
        emailService.sendConfirmationEmail(email, token);
        UserDto userDto = userMapper.mapToUserDto(userEntity);
        return userDto;
    }

    private String hashPassword(String password) {
        return passwordEncoder.encode(password);
    }

    public List<UserDto> getUsers() {
        List<UserEntity> userEntities = userRepository.findAll();
        List<UserDto> userDtos = userEntities.stream()
                .map(userEntity -> userMapper.mapToUserDto(userEntity))
                .collect(Collectors.toList());
        return userDtos;
    }

    public void deleteUser(Integer id) {
        userRepository.deleteById(id);
    }

    //@Nullable
    public Optional<UserDto> getUserById(Integer id) {
//        Optional<UserEntity> userEntityOptional = userRepository.findById(id);
//
//        if (userEntityOptional.isPresent()){
//            UserEntity userEntity = userEntityOptional.get();
//            UserDto userDto = userMapper.mapToUserDto(userEntity);
//            return Optional.of(userDto);
//        }
//        return Optional.empty();

        return userRepository.findById(id)
                .map(userMapper::mapToUserDto);
    }

    public UserDto updateUser(Integer id, UpdateUserDto updateUserDto) {
        UserEntity userEntity =  userRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        userEntity.setName(updateUserDto.getName());
        userEntity.setLastName(updateUserDto.getLastName());
        UserEntity updatedUser = userRepository.save(userEntity);
        return userMapper.mapToUserDto(updatedUser);

    }

    public void confirmUserByToken(String confirmationToken) {
        UserEntity userEntity = userRepository.findByUnconfirmedToken(confirmationToken)
                .orElseThrow(NotFoundException::new);
        userEntity.setUnconfirmedToken(null);
        userRepository.save(userEntity);
    }
}
