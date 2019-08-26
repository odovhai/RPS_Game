package com.rubanj.casino.rockpaperscissors.service.impl;

import com.rubanj.casino.rockpaperscissors.domain.dto.UserCredentials;
import com.rubanj.casino.rockpaperscissors.domain.model.User;
import com.rubanj.casino.rockpaperscissors.repository.UserRepository;
import com.rubanj.casino.rockpaperscissors.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User create(User user) {
        if (userRepository.findByName(user.getName()).isPresent()) {
            throw new IllegalStateException(String.format("User [%s] already exists", user.getName()));
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public User findByName(String userName) {
        return userRepository.findByName(userName)
                .orElseThrow(() -> new NoSuchElementException(String.format("User [%s] does not exits", userName)));
    }

    @Override
    public void validateUserAccess(UserCredentials userCredentials) {
        String errorMsg = String.format("User [%s] does not exist or entered password is invalid.", userCredentials.getUserName());
        User user = userRepository.findByName(userCredentials.getUserName()).orElseThrow(() -> new BadCredentialsException(errorMsg));
        if (!(passwordEncoder.matches(userCredentials.getPassword(), user.getPassword()))) {
            throw new BadCredentialsException(errorMsg);
        }
    }
}
