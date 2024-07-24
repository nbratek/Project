package pl.nataliabratek.project.api.controller;

import lombok.AllArgsConstructor;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.nataliabratek.project.api.model.CreateUserDto;
import pl.nataliabratek.project.api.model.UpdateUserDto;
import pl.nataliabratek.project.api.model.UserDto;
import pl.nataliabratek.project.data.users.UserEntity;
import pl.nataliabratek.project.data.users.UserRepository;
import pl.nataliabratek.project.domain.service.TokenService;
import pl.nataliabratek.project.domain.service.UserService;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@RestController
public class UserController {

    private TokenService tokenService;
    private UserService userService;


    @GetMapping("/api/v1/users")
    public ResponseEntity<List<UserDto>> getUsers(
            @RequestParam(value="filter-by-name", required = false) String filter,
            @RequestHeader(value="Authorization") String token) throws Exception {
        System.out.println(token);

        if (!tokenService.checkToken(token)){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        //System.out.println(tokenService.getUserId(token));
        List<UserDto> userDtos = userService.getUsers();
        return ResponseEntity.status(HttpStatus.OK)
                .body(userDtos);
    }
    @GetMapping("/api/v1/users/{id}")
    public ResponseEntity<UserDto> getUserById(
            @RequestParam(value="filter-by-name", required = false) String filter,
            @RequestHeader(value="X-Auth-Login") String login,
            @PathVariable(value = "id") Integer id) {
        System.out.println(id);
        //UserDto userDto = new UserDto("Jan", "Nowak", 1);
        //UserDto userDto2 = new UserDto("Adam", "Nowak2", 2);
        //List<UserDto> userDtos = List.of(userDto2, userDto);
        Optional<UserDto> userDtoOptional = userService.getUserById(id);
        if (userDtoOptional.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(userDtoOptional.get());
    }
    //rest api, request body

    @PostMapping ("/api/v1/users")
    public ResponseEntity<UserDto> createUser(@RequestBody CreateUserDto body) {
        UserDto userDto = userService.createUser(body.getName(), body.getLastName(), body.getPassword(), body.getEmail());
        return ResponseEntity.status(HttpStatus.OK)
                .body(userDto);
    }
    @DeleteMapping("/api/v1/users/{id}")
    public ResponseEntity<Void> deleteUser(
        @PathVariable(value = "id") Integer id){
        //System.out.println("usuwanie uzytkownika");
        userService.deleteUser(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
    @PutMapping("/api/v1/users/{id}")
    public ResponseEntity<UserDto> updateUser(
            @PathVariable(value = "id") Integer id,
            @RequestBody UpdateUserDto body){
        //System.out.println("update uzytkownika");
        UserDto userDto = userService.updateUser(id, body);
        return ResponseEntity.status(HttpStatus.OK).body(userDto);
    }
}
