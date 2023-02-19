package com.isilacar.springsecurityjwtexample.security;

import com.isilacar.springsecurityjwtexample.entity.User;
import com.isilacar.springsecurityjwtexample.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/*
Her http request ve response ları isteğine cevap vermek için kullanılıyor
 */


public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private UserService userService;

    @Autowired
    private  JwtTokenProvider jwtTokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String header = request.getHeader("Authorization");

        if(header==null || !header.startsWith("Bearer ")){
            filterChain.doFilter(request,response);
            return;

        }
        String jwtToken = header.substring(7);

        String subject = jwtTokenProvider.getSubject(jwtToken);

        //mevcut bir oturum yoksa
        if(subject!=null && SecurityContextHolder.getContext().getAuthentication()==null){
            User user = userService.loadUserByUsername(subject);

            if(!jwtTokenProvider.isTokenExpired(jwtToken) && user.getUsername().equals(subject)){
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
      // eğer user içindeki var olan bilgiler herhangi bir değişikliğe uğradıysa,bu bilgileri burada setDetails diyerek güncelliyoruz
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        filterChain.doFilter(request,response);


    }
}
