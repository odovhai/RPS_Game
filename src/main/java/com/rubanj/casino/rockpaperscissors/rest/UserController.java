package com.rubanj.casino.rockpaperscissors.rest;

import com.rubanj.casino.rockpaperscissors.converter.EntityDtoConverter;
import com.rubanj.casino.rockpaperscissors.domain.dto.UserCredentials;
import com.rubanj.casino.rockpaperscissors.domain.dto.UserDto;
import com.rubanj.casino.rockpaperscissors.domain.model.User;
import com.rubanj.casino.rockpaperscissors.service.UserService;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(value = "User API")
@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {

    private final UserService service;
    private final EntityDtoConverter<User, UserDto> converter;

    @GetMapping
    public List<UserDto> getAll() {
        List<User> result = service.findAll();
        return converter.toDtoList(result);
    }

    @PostMapping
    public UserDto create(@RequestBody @Valid UserDto userDto) {
        User result = service.create(converter.toEntity(userDto));
        return converter.toDto(result);
    }

    @PutMapping
    public UserDto changeUserPassword(@RequestBody @Valid UserCredentials credentials, @RequestParam String newPassword) {
        return converter.toDto(service.changePassword(credentials, newPassword));
    }

    @DeleteMapping(path = "/{userId}")
    public void deleteUser(@PathVariable Long userId) {
        service.delete(userId);
    }
}
