package com.example.jwtapp.services;

import com.example.jwtapp.models.AppUser;
import com.example.jwtapp.models.Message;
import com.example.jwtapp.models.Role;
import com.example.jwtapp.repos.MessageRepo;
import com.example.jwtapp.repos.RoleRepo;
import com.example.jwtapp.repos.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService implements UserDetailsService {

    private final UserRepo userRepo;
    private final RoleRepo roleRepo;
    private final MessageRepo messageRepo;
    private final PasswordEncoder encoder;

    public String proceedIncomingMessage(Message message) {
        String text = message.getText();
        String[] splitText = text.split(" ");
        if (splitText[0].equals("history") && splitText.length == 2) {
            try {
                int amountOfMessages = Integer.parseInt(splitText[1]);

                if (messageRepo.count() < amountOfMessages) {
                    return "The number of records in the database (" + messageRepo.count() + ") " +
                            "is less than the requested one (" + amountOfMessages + ").";
                }
                Pageable pages = PageRequest.of(0, amountOfMessages, Sort.Direction.DESC, "id");
                List<Message> listFromDb = messageRepo.findAll(pages).getContent();
                StringBuilder builder = new StringBuilder();
                for (int i = 1; i < amountOfMessages + 1; i++) {
                    builder
                            .append("Message ")
                            .append(i).append(": ")
                            .append(listFromDb.get(listFromDb.size() - i).getText())
                            .append(";\n");
                }
                builder
                        .deleteCharAt(builder.length() - 1)
                        .deleteCharAt(builder.length() - 1)
                        .append(".");
                return builder.toString();
            } catch (Exception e) {
                saveMessage(message);
                return "Message successfully saved in the database";
            }
        }
        saveMessage(message);
        return "Message successfully saved in the database";
    }

    public void saveMessage(Message message) {
        AppUser user = usernameValidation(message.getName());
        messageRepo.save(message);
        user.getMessages().add(message);

    }

    public AppUser usernameValidation(String username) {
        Optional<AppUser> user = userRepo.findByUsername(username);
        if (user.isEmpty())
            throw new UsernameNotFoundException("User with such username wasn't found in the database!");
        return user.get();
    }

    public AppUser saveUser(AppUser user) {
        user.setPassword(encoder.encode(user.getPassword()));
        return userRepo.save(user);
    }

    public Role saveRole(Role role) {
        return roleRepo.save(role);
    }

    public void addRoleToUser(String username, String roleName) {
        usernameValidation(username)
                .getRoles()
                .add(roleNameValidation(roleName));
    }

    public Role roleNameValidation(String RoleName) {
        Optional<Role> role = roleRepo.findByName(RoleName);
        if (role.isEmpty())
            throw new UsernameNotFoundException("User with such username wasn't found in the database!");
        return role.get();
    }

    public List<AppUser> getUsers() {
        return userRepo.findAll();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser user = usernameValidation(username);
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.getRoles().forEach(role -> authorities.add(new SimpleGrantedAuthority(role.getName())));
        return new User(user.getUsername(), user.getPassword(), authorities);
    }


    public List<Message> getMessages() {
        return (List<Message>) messageRepo.findAll();
    }
}
