package pl.nataliabratek.project.api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.nataliabratek.project.domain.service.AuthorizationService;

@RestController
@RequestMapping("/api/v1")
public class LoginController {

    private AuthorizationService authorizationService;

    public LoginController(AuthorizationService authorizeUserUseCase) {
        this.authorizationService = authorizeUserUseCase;
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(
        @RequestHeader("X-Auth-Login") String login,
        @RequestHeader("X-Auth-Password") String password
        ){
        String token = authorizationService.authorize(login, password);
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
// wyjatki
}

