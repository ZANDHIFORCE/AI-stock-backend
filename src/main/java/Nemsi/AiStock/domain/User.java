package Nemsi.AiStock.domain;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class User implements UserDetails {
    private String userid;
    private String password;
    private String email;
    private String nickname;
    private String role;
    private String createdd;
    private String updatedd;
    private String birthdd;
    private String gender;
    private String name;

    // Getters and Setters
    public String getUserid() { return userid; }
    public void setUserid(String userid) { this.userid = userid; }

    @Override
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getNickname() { return nickname; }
    public void setNickname(String nickname) { this.nickname = nickname; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public String getCreatedd() { return createdd; }
    public void setCreatedd(String createdd) { this.createdd = createdd; }

    public String getUpdatedd() { return updatedd; }
    public void setUpdatedd(String updatedd) { this.updatedd = updatedd; }

    public String getBirthdd() { return birthdd; }
    public void setBirthdd(String birthdd) { this.birthdd = birthdd; }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    // UserDetails implementations
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + role));
    }

    @Override
    public String getUsername() {
        return userid;
    }

    @Override
    public boolean isAccountNonExpired() { return true; }

    @Override
    public boolean isAccountNonLocked() { return true; }

    @Override
    public boolean isCredentialsNonExpired() { return true; }

    @Override
    public boolean isEnabled() { return true; }
}
