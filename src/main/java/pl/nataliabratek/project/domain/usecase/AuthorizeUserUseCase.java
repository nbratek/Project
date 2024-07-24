package pl.nataliabratek.project.domain.usecase;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import pl.nataliabratek.project.domain.service.AuthorizationService;
import pl.nataliabratek.project.domain.service.TokenService;

@AllArgsConstructor
@Component

public class AuthorizeUserUseCase {
    private AuthorizationService authorizationService;
    private TokenService tokenService;
@Nullable
    public String authorize(String login, String password){
        if (authorizationService.checkLoginAndPassword(login, password)) {
            return tokenService.createToken(0);
            // id, zastapic depractedy method spring security
        }
        else {
            return null;
        }

    }
}
