package com.isilacar.springsecurityjwtexample.service;

import com.isilacar.springsecurityjwtexample.dto.UserDto;
import com.isilacar.springsecurityjwtexample.dto.UserResponse;
import com.isilacar.springsecurityjwtexample.entity.User;
import com.isilacar.springsecurityjwtexample.enums.Role;
import com.isilacar.springsecurityjwtexample.repository.UserRepository;
import com.isilacar.springsecurityjwtexample.request.UserRequest;
import com.isilacar.springsecurityjwtexample.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AuthenticationService  {

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;

    private final AuthenticationManager authenticationManager;

    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    public User save(UserDto userDto) {
        /*
           User user=new User();
        user.setName(userRequest.getUsername());
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        userService.saveToDb(user);
         */

        return userRepository.save(User.builder()
                .username(userDto.getUsername())
                .nameSurname(userDto.getNameSurname())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .role(Role.USER)
                .build());

      //  return  UserResponse.builder().token(jwtTokenProvider.generateToken(user)).build();
    }

    public UserResponse auth(UserRequest userRequest) {

        Authentication authenticate = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(userRequest.getUsername(), userRequest.getPassword()));
       // User user = (User) userService.loadUserByUsername(userRequest.getUsername());
        SecurityContextHolder.getContext().setAuthentication(authenticate);
        String token = jwtTokenProvider.generateToken(authenticate);
        return UserResponse.builder().token(token).build();
    }

}
