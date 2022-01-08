package com.users.service.controller;

import com.users.service.auth.models.AuthUser;
import com.users.service.dto.CreateUserDto;
import com.users.service.entity.User;
import com.users.service.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/")
public class UsersController {

    @Autowired
    UserService userService;



    @PostMapping()
    public ResponseEntity<User> register(@Valid @RequestBody CreateUserDto createUserDto, @AuthenticationPrincipal AuthUser athUser) throws IOException, ExecutionException, InterruptedException {
        User user = this.userService.registerUser(createUserDto, athUser.getUid(), athUser.getEmail());
        return ResponseEntity.ok(user);
    }






}
