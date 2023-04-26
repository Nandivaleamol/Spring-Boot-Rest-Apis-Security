package com.nandivaleamol;

import com.nandivaleamol.config.AppConstant;
import com.nandivaleamol.entities.Role;
import com.nandivaleamol.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@SpringBootApplication
public class SpringBootRestApisApplication implements CommandLineRunner {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    public static void main(String[] args) {
        SpringApplication.run(SpringBootRestApisApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        try {
            Role role1 = new Role();
            role1.setId(AppConstant.ADMIN_USER);
            role1.setName("ROLE_ADMIN");

            Role role2 = new Role();
            role2.setId(AppConstant.NORMAL_USER);
            role2.setName("ROLE_NORMAL");

            List<Role> roles = List.of(role1, role2);
            List<Role> result = this.roleRepository.saveAll(roles);
            result.forEach(role -> System.out.println(role.getName()));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
