package com.project.tarea1Evador.logic.entity.rol;

import com.project.tarea1Evador.logic.entity.user.User;
import com.project.tarea1Evador.logic.entity.user.UserRepository;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@DependsOn("roleSeeder")
public class UserSeeder implements ApplicationListener<ContextRefreshedEvent> {
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;


    public UserSeeder(
            RoleRepository roleRepository,
            UserRepository  userRepository,
            PasswordEncoder passwordEncoder
    ) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        this.createNormalUser();
    }

    private void createNormalUser() {
        User normalUser = new User();
        normalUser.setName("Juan");
        normalUser.setLastname("Manuel");
        normalUser.setEmail("manuelJuan@gmail.com");
        normalUser.setPassword("atest123");

        Optional<Role> optionalRole = roleRepository.findByName(RoleEnum.USER);
        Optional<User> optionalUser = userRepository.findByEmail(normalUser.getEmail());

        if (optionalRole.isEmpty() || optionalUser.isPresent()) {
            return;
        }

        var user = new User();
        user.setName(normalUser.getName());
        user.setLastname(normalUser.getLastname());
        user.setEmail(normalUser.getEmail());
        user.setPassword(passwordEncoder.encode(normalUser.getPassword()));
        user.setRole(optionalRole.get());

        userRepository.save(user);
    }
}
