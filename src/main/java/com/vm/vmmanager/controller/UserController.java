package com.vm.vmmanager.controller;


import com.vm.vmmanager.dto.LoginRequest;
import com.vm.vmmanager.dto.UserDto;
import com.vm.vmmanager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/vmmanager")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/user/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody final LoginRequest loginRequest) {
        return userService.authenticateUser(loginRequest.getUsername(), loginRequest.getPassword());
    }

    @PostMapping(value = "/user/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody final UserDto userDto) {
        return userService.registerUser(userDto);
    }

    @DeleteMapping(value="/user/{userName}")
    public ResponseEntity<?> deleteUser(@NotBlank @PathVariable(name = "userName", required = true) final String userName) {
        return userService.deleteUser(userName);
    }

}
