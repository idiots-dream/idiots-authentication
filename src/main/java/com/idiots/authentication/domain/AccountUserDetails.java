package com.idiots.authentication.domain;

import com.idiots.authentication.entity.SysAccount;
import com.idiots.authentication.entity.SysResource;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * SpringSecurity需要的用户详情
 *
 * @author devil-idiots
 * Date 2022-12-2
 */
@Data
public class AccountUserDetails implements UserDetails {
    private SysAccount sysAdmin;
    private List<SysResource> resources;

    public AccountUserDetails(SysAccount sysAdmin, List<SysResource> resources) {
        this.sysAdmin = sysAdmin;
        this.resources = resources;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        //返回当前用户的角色
        return resources.stream()
                .map(role -> new SimpleGrantedAuthority(role.getId() + ":" + role.getName()))
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return sysAdmin.getPassword();
    }

    @Override
    public String getUsername() {
        return sysAdmin.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return sysAdmin.getEnabled().equals(1);
    }
}
