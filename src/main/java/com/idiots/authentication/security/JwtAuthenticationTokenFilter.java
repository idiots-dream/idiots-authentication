package com.idiots.authentication.security;

import cn.hutool.core.lang.Pair;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.idiots.authentication.lang.Constant;
import com.idiots.authentication.util.TokenProviderUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Optional;

/**
 * JWT登录授权过滤器
 * @author devil-idiots
 * Date 2022-12-2
 */
@Slf4j
@Order(0)
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    public JwtAuthenticationTokenFilter(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (Constant.WHITES
                .stream()
                .map(p -> new AntPathRequestMatcher(p.getValue(), p.getKey().toString()))
                .anyMatch(matcher -> matcher.matches(request))
        ) {
            filterChain.doFilter(request, response);
            return;
        }

        Optional<UserDetails> maybeUserDetail = compoundAuth(request);
        if (maybeUserDetail.isPresent()) {
            UserDetails userDetail = maybeUserDetail.get();
            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                    userDetail.getUsername(), userDetail.getPassword(), userDetail.getAuthorities()
            );
            auth.setDetails(userDetail);
            SecurityContextHolder.getContext().setAuthentication(auth);
            log.info("user 【{}】 logged into the system at the {}.", userDetail.getUsername(), LocalDateTime.now());
        }
        filterChain.doFilter(request, response);
    }

    private Optional<UserDetails> compoundAuth(HttpServletRequest request) {
        Pair<String, String> token = TokenProviderUtil.getAuthToken(request);
        log.info("token:{}", token);
        if (StringUtils.equals(token.getKey(), TokenProviderUtil.JWT_AUTH_PREFIX)) {
            TokenProviderUtil.validate(token.getValue());
                return Optional.of(userDetailsService.loadUserByUsername(TokenProviderUtil.getAccount(token.getValue())));
        }
        if (StringUtils.equals(token.getKey(), TokenProviderUtil.BASIC_AUTH_PREFIX)) {
            Pair<String, String> userAndPassword = TokenProviderUtil.decodeBasicAuthToken(token.getValue());
            UserDetails userDetail = userDetailsService.loadUserByUsername(userAndPassword.getKey());
            if (!passwordEncoder.matches(userAndPassword.getValue(), userDetail.getPassword())) {
                throw new IllegalStateException("wrong user name or password");
            }
            return Optional.of(userDetail);
        }
        return Optional.empty();
    }
}
