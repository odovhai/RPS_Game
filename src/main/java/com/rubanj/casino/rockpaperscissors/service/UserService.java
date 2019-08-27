package com.rubanj.casino.rockpaperscissors.service;

import com.rubanj.casino.rockpaperscissors.domain.dto.UserCredentials;
import com.rubanj.casino.rockpaperscissors.domain.model.User;

import java.util.List;

public interface UserService {

    List<User> findAll();

    User create(User user);

    User changePassword(UserCredentials credentials, String newPassword);

    User findByName(String userName);

    void validateUserAccess(UserCredentials userCredentials);

    void delete(Long userId);


}
