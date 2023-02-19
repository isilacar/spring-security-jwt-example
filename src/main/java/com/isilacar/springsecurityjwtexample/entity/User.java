package com.isilacar.springsecurityjwtexample.entity;


import com.isilacar.springsecurityjwtexample.enums.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nameSurname;

    private String username;
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;


    //nereelere yatkileri var, onları tanımlıyor
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    //kullanıcının hesabının süresi dolmuş mu
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    //bu kullanıcının hesabı kilitli mi değil mi
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    //kullanıcı aktif mi pasif mi
    @Override
    public boolean isEnabled() {
        return true;
    }
}
