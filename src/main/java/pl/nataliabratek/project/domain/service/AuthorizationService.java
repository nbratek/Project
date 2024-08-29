package pl.nataliabratek.project.domain.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.lang.Nullable;
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
    private TokenService tokenService;



    @Nullable
    public String authorize(String login, String password){
        return  checkLoginAndPassword(login, password)
         .map(tokenService::createToken)
                .orElse(null);

        //        if (checkLoginAndPassword(login, password)) {
//            return tokenService.createToken(0);
//            // id, zastapic depractedy method spring security
//        }
//        else {
//            return null;
//        }

    }
    //jak zwrocic informacje ze uzytkownik nie jest potwierdzony

    private Optional<Integer> checkLoginAndPassword(String login, String password){
        return userRepository.findByEmail(login)
                .filter(userEntity -> userEntity.getUnconfirmedToken() == null && passwordEncoder.matches(password, userEntity.getPasswordHash()))
                .map(UserEntity::getId);
//        if(userEntityOptional.isPresent() ){
//            UserEntity user = userEntityOptional.get();
//            return user.getUnconfirmedToken() == null && passwordEncoder.matches(password, user.getPasswordHash());
//        }
//        else{
//            return false;
//        }
    }
}
//wyslac maila - konto 