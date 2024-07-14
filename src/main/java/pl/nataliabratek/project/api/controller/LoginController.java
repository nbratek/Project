package pl.nataliabratek.project.api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.nataliabratek.project.domain.usecase.AuthorizeUserUseCase;

@RestController
@RequestMapping("/api/v1")
public class LoginController {

    private AuthorizeUserUseCase authorizeUserUseCase;

    public LoginController(AuthorizeUserUseCase authorizeUserUseCase) {
        this.authorizeUserUseCase = authorizeUserUseCase;
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(
        @RequestHeader("X-Auth-Login") String login,
        @RequestHeader("X-Auth-Password") String password
        ){
        String token = authorizeUserUseCase.authorize(login, password);
        if (token == null) {
            // zwraca komunikat o niepoprawnych danych
            return ResponseEntity.status(401).body("Niepoprawne dane");
        }
        else{
            //zwracamy token
            return ResponseEntity.status(HttpStatus.OK)
                    .body(token);
        }
    }

}
