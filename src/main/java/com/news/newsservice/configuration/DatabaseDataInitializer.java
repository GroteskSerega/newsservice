package com.news.newsservice.configuration;

import com.news.newsservice.entity.Role;
import com.news.newsservice.entity.RoleType;
import com.news.newsservice.entity.User;
import com.news.newsservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class DatabaseDataInitializer {

    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    @Value("${app.security.adminPassword}")
    private String adminPassword;

    @Value("${app.security.adminUsername}")
    private String adminUsername;

    @EventListener(ApplicationReadyEvent.class)
    public void createAdminUserInDb() {
        Optional<User> existedAdmin = userRepository.findByUsername(adminUsername);

        if (existedAdmin.isPresent()) {
            return;
        }

        var role = Role.from(RoleType.ROLE_ADMIN);
        var admin = new User();

        admin.setUsername(adminUsername);
        admin.setEmail(adminUsername + "@" + adminUsername + ".com");
        admin.setPassword(passwordEncoder.encode(adminPassword));
        admin.setRoles(new ArrayList<>(List.of(role)));
        role.setUser(admin);

        userRepository.save(admin);
    }
}
