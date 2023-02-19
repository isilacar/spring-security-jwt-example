package com.isilacar.springsecurityjwtexample.controller;

import com.isilacar.springsecurityjwtexample.dto.UserDto;
import com.isilacar.springsecurityjwtexample.dto.UserResponse;
import com.isilacar.springsecurityjwtexample.entity.User;
import com.isilacar.springsecurityjwtexample.request.UserRequest;
import com.isilacar.springsecurityjwtexample.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
@RequiredArgsConstructor
public class AuthenticationController {


    private final AuthenticationService authenticationService;

    @PostMapping("/save")
    public ResponseEntity<User> save(@RequestBody UserDto userDto){
        return new ResponseEntity<>(authenticationService.save(userDto), HttpStatus.CREATED);
    }

    @PostMapping("/auth")
    public ResponseEntity<UserResponse> auth(@RequestBody UserRequest userRequest){
        return new ResponseEntity<>(authenticationService.auth(userRequest),HttpStatus.OK);
    }



}
