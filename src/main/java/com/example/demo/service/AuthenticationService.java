package com.example.demo.service;

import com.example.demo.entity.Account;
import com.example.demo.entity.Dto.AccountDTO;
import com.example.demo.entity.Dto.RegisterDTO;
import com.example.demo.entity.Role;
import com.example.demo.enumClass.AccountStatusEnum;
import com.example.demo.repository.AccountRepository;
import com.example.demo.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AuthenticationService implements UserDetailsService {
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private RoleRepository roleRepository;
    private static final String USER_ROLE = "user";

    // authorize là quyền, authentication là xác thực.
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Account> optional = accountRepository.findByUsername(username);
        if (!optional.isPresent()) {
            throw new UsernameNotFoundException("Username not exists!!");
        }
        Account account = optional.get();
        ArrayList<SimpleGrantedAuthority> authorities = new ArrayList<>();
        for (Role role : account.getRoles()
        ) {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        }
        return new User(account.getUsername(), account.getPasswordHash(), authorities);
    }

    public AccountDTO saveAccount(RegisterDTO registerDTO) {
        //create new user role if not exist
        Optional<Role> userRoleOptional = roleRepository.findByName(USER_ROLE);
        Role userRole = userRoleOptional.orElse(null);
        if (userRole == null) {
            //create new role
            userRole = roleRepository.save(new Role(0,USER_ROLE,null));
        }
        //check if username has exist
        Optional<Account> byUsername = accountRepository.findByUsername(registerDTO.getUsername());
        if (byUsername.isPresent()) {
            return null;
        }
        Account account = new Account();

        account.setUsername(registerDTO.getUsername());
        account.setPasswordHash(passwordEncoder.encode(registerDTO.getPassword()));
        account.setCreatedAt(new Date());
        account.setUpdatedAt(new Date());
        account.setStatus(AccountStatusEnum.ACTIVE);
        account.setRoles(new HashSet<>(Collections.singletonList(userRole)));
        Account save = accountRepository.save(account);
        return new AccountDTO(save);
    }

    public Account getAccount(String username) {
        Optional<Account> byUsername = accountRepository.findByUsername(username);
        return byUsername.orElse(null);
    }
}
