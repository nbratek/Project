package pl.nataliabratek.project.domain.service;

import org.springframework.stereotype.Service;
import pl.nataliabratek.project.api.controller.LoginController;
@Service
public class AuthorizationService {
    public boolean checkLoginAndPassword(String login, String password){
        if(login.equals("admin") && password.equals("admin")){
            return true;
        }
        else{
            return false;
        }
    }
}
