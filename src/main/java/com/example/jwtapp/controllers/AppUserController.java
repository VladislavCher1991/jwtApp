package com.example.jwtapp.controllers;

import com.example.jwtapp.models.AppUser;
import com.example.jwtapp.models.Message;
import com.example.jwtapp.models.Role;
import com.example.jwtapp.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequiredArgsConstructor
@RequestMapping("/")
public class AppUserController {

    private final UserService service;

    //Здесь происходит создание объекта Message в соответствии с телом запроса.
    //Вся логика находится в методе proceedIncomingMessage класса UserService.
    @PostMapping("/messages/save")
    public ResponseEntity<String> proceedIncomingMessage(@RequestBody Message message) {
        String response = service.proceedIncomingMessage(message);
        if (response.startsWith("Message ")) return new ResponseEntity<>(response, OK);
        else return new ResponseEntity<>(response, BAD_REQUEST);
    }


    //Далее идут методы, необходимые для заполнения БД и тестирования.
    //-------------------------------------------------------------------

    @GetMapping("/admin")
    public String adminPage() {
        return "for admins only!";
    }

    @GetMapping("/users")
    public ResponseEntity<List<AppUser>> getUsers() {
        return ResponseEntity.ok().body(service.getUsers());
    }

    @PostMapping("/users/save")
    public ResponseEntity<AppUser> saveUser(@RequestBody AppUser user) {
        URI uri = URI
                .create(ServletUriComponentsBuilder
                        .fromCurrentContextPath()
                        .path("/users/save")
                        .toUriString());
        return ResponseEntity.created(uri).body(service.saveUser(user));
    }

    @GetMapping("/roles/save")
    public ResponseEntity<Role> saveRole(@RequestBody Role role) {
        URI uri = URI
                .create(ServletUriComponentsBuilder
                        .fromCurrentContextPath()
                        .path("/roles/save")
                        .toUriString());
        return ResponseEntity.created(uri).body(service.saveRole(role));
    }

    @GetMapping("/users/editroles")
    public ResponseEntity<?> addRoleToUser(
            @RequestParam String username,
            @RequestParam String roleName) {
        service.addRoleToUser(username, roleName);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/messages")
    public List<Message> getMessages() {
        return service.getMessages();
    }
}
