package pl.nataliabratek.project.domain.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pl.nataliabratek.project.api.controller.LoginController;
import pl.nataliabratek.project.data.users.UserEntity;
import pl.nataliabratek.project.data.users.UserRepository;

import java.util.Optional;

@Service
@AllArgsConstructor

public class AuthorizationService {
    private UserRepository userRepository;
    private BCryptPasswordEncoder passwordEncoder;


    public boolean checkLoginAndPassword(String login, String password){
        Optional<UserEntity> userEntityOptional = userRepository.findByEmail(login);
        if(userEntityOptional.isPresent() ){
            UserEntity user = userEntityOptional.get();
            return user.getUnconfirmedToken() == null && passwordEncoder.matches(password, user.getPasswordHash());
        }
        else{
            return false;
        }
    }
    //jak zwrocic informacje ze uzytkownik nie jest potwierdzony
}
