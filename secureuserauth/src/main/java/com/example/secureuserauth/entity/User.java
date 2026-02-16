package com.example.secureuserauth.entity;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.secureuserauth.enums.Role;
import com.example.secureuserauth.exception.UserException;

import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NoArgsConstructor
// @AllArgsConstructor
@Setter(AccessLevel.PRIVATE)
// @Getter(AccessLevel.PRIVATE)
@Getter
@Entity
@Table(name = "users")
public class User implements UserDetails{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min = 2, message = "Name must be at least 2 characters long")
    @NotBlank(message = "Name must not be blank")
    private String name;

    @Size(min = 8, message = "Password must be at least 8 characters long")
    @NotBlank(message = "Password must not be blank")
    private String password;

    @NotBlank(message = "Email must not be blank")
    @Column(unique = true)
    @Email
    private String email;

    @OneToMany(
        mappedBy = "author",
        cascade = CascadeType.ALL,
        orphanRemoval = false
    )
    private List<Post> posts = new ArrayList<>();

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "perfil_cargos", joinColumns = @JoinColumn(name = "perfil_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private List<Role> roles = new ArrayList<>();

    private OffsetDateTime createdAt;

    public static User newUser(String name, String password, String email, List<Role> roles) {
        User user = new User();
        user.setName(name.trim());
        user.setPassword(password);
        user.setEmail(email.trim().toLowerCase());
        user.addRoles(roles);
        user.setCreatedAt(OffsetDateTime.now());

        return user;
    }

    public void addRole(Role role) {
        if (this.getRoles().contains(role)) {
            log.error("User {} already contains {} role.", this.getName(), role);
            throw new UserException("User already contains role");
        }
        
        this.getRoles().add(role);
    }

    public void addRoles(List<Role> roles) {
        List<Role> rolesToAdd = roles.stream().filter(r -> !this.getRoles().contains(r)).toList();
        List<Role> alreadyContains = roles.stream().filter(this.getRoles()::contains).toList();

        log.info("rolestoAdd: {} | alreadyContains: {} | roles: {}", rolesToAdd, alreadyContains, roles);

        if (roles.isEmpty()) {
            log.error("Role list is empty.");
            return;
        }
        
        if (rolesToAdd.isEmpty()) {
            log.error("User {} already contains {} roles.", this.getName(), alreadyContains.toString());
            throw new UserException("User already contains roles");
        }

        if (!alreadyContains.isEmpty()) {
            log.warn("User {} already contains {} role(s).", this.getName(), alreadyContains.toString());
        }

        this.getRoles().addAll(rolesToAdd);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.getRoles().stream()
            .map(role -> new SimpleGrantedAuthority("ROLE_" + role.name()))
            .toList();
    }

    @Override
    public String getUsername() {
        return this.getEmail();
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
        return true;
    }
}
