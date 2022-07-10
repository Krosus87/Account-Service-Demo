package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    public UserRegistrationResponse registerUser(Person person) {
        if (person.getName() == null || person.getUsername() == null || person.getPassword() == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        if (userRepository.findUserByUsernameIgnoreCase(person.getUsername()).isPresent())
            throw new ResponseStatusException(HttpStatus.CONFLICT, "User exist!");
        if (showAllUsers().size() == 0) {
            person.setRole("ADMINISTRATOR");
            person.setAccountNonLocked(true);
        }
        person.setPassword(getEncoder().encode(person.getPassword()));
        userRepository.save(person);

        return new UserRegistrationResponse(person);
    }

    public UserDeletionResponse deleteUser(String username) {
        Optional<Person> person = userRepository.findUserByUsernameIgnoreCase(username);
        if (person.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        userRepository.delete(person.get());

        return new UserDeletionResponse(person.get());
    }

    public List<UserListView> showAllUsers() {
        List<Person> userList = userRepository.findAll();
        List<UserListView> userListView = new ArrayList<>();

        for (Person person : userList) {
            userListView.add(new UserListView(person));
        }

        return userListView;
    }

    public UserRegistrationResponse grantRole(Map<String, String> newRole) {
        Optional<Person> person = userRepository.findUserByUsernameIgnoreCase(newRole.get("username"));
        if (person.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        if (newRole.get("role").equals(person.get().getRole()))
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        if ("ADMINISTRATOR".equals(newRole.get("role")))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        if (!("MERCHANT".equals(newRole.get("role")) ^ "SUPPORT".equals(newRole.get("role"))))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        Person updatedUser = person.get();
        updatedUser.setRole(newRole.get("role"));
        userRepository.save(updatedUser);

        return new UserRegistrationResponse(updatedUser);
    }

    public Map<String, String> unlockUser(Map<String, String> unlockRequest) {
        Optional<Person> person = userRepository.findUserByUsernameIgnoreCase(unlockRequest.get("username"));
        if (person.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        if ("ADMINISTRATOR".equals(person.get().getRole()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        if ("UNLOCK".equals(unlockRequest.get("operation")))
            person.get().setAccountNonLocked(true);
        if ("LOCK".equals(unlockRequest.get("operation")))
            person.get().setAccountNonLocked(false);
        Person updatedUser = person.get();
        userRepository.save(updatedUser);

        return Map.of("status", String.format("User %s %sed!", unlockRequest.get("username"), unlockRequest.get("operation").toLowerCase()));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Person> person = userRepository.findUserByUsernameIgnoreCase(username);

        if (person.isPresent()) {
            return new UserDetailsImpl(person.get());
        } else {
            throw new UsernameNotFoundException(String.format("Username[%s] not found", username));
        }
    }

    @Bean
    public PasswordEncoder getEncoder() {
        return new BCryptPasswordEncoder();
    }
}
