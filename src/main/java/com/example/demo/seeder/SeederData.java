package com.example.demo.seeder;

import com.example.demo.entity.Account;
import com.example.demo.entity.Role;
import com.example.demo.enumClass.AccountStatusEnum;
import com.example.demo.repository.AccountRepository;
import com.example.demo.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class SeederData implements CommandLineRunner {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder encoder;
    @Override
    public void run(String... args) throws Exception {
        if (roleRepository.findAll().isEmpty()){
            Role admin = Role.builder().name("admin").build();
            Role user = Role.builder().name("user").build();
            roleRepository.saveAll(Arrays.asList(admin,user));
        }
        if (accountRepository.findAll().isEmpty()) {
            Role admin = Role.builder().name("admin").build();
            Role user = Role.builder().name("user").build();
            Optional<Role> optionalAdmin = roleRepository.findByName("admin");
            Optional<Role> optionalUser = roleRepository.findByName("user");
            if (optionalAdmin.isPresent()) {
                admin = optionalAdmin.get();
            }
            if (optionalUser.isPresent()) {
                user = optionalUser.get();
            }
            List<Account> accounts = new ArrayList<>();
            for (int i = 0; i < 5; i++) {
                Account account = Account.builder()
                        .username("username"+i)
                        .passwordHash(encoder.encode("123456"))
                        .status(AccountStatusEnum.ACTIVE)
                        .roles(new HashSet<>(Arrays.asList(admin,user)))
                        .build();
                accounts.add(account);
            }
            accountRepository.saveAll(accounts);
        }
    }
}
