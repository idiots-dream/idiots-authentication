package com.idiots.authentication.security;

import com.idiots.authentication.entity.SysAccount;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * 动态权限数据源，用于获取动态权限规则
 * @author devil-idiots
 * Date 2022-12-2
 */
@Component
public class CustomAuthenticationManager implements AuthenticationManager {

    private final UserDetailsService userDetailService;
    private final PasswordEncoder passwordEncoder;

    public CustomAuthenticationManager(UserDetailsService userDetailService, PasswordEncoder passwordEncoder) {
        this.userDetailService = userDetailService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        UserDetails ud = userDetailService.loadUserByUsername(authentication.getName());
        assert ud instanceof SysAccount;
        if (passwordEncoder.matches((String) authentication.getCredentials(), ud.getPassword())) {
            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(ud.getUsername(), ud.getPassword());
            auth.setDetails(ud);
            return auth;
        }
        return null;
    }
}
