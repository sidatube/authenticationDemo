package com.example.demo.service;

import com.example.demo.entity.Account;
import com.example.demo.entity.Role;
import com.example.demo.enumClass.AccountStatusEnum;
import com.example.demo.repository.AccountRepository;
import com.example.demo.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AccountService {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder encoder;

    public Account save(Account account) {
        Optional<Role> optionalRole = roleRepository.findByName("user");
        if (!optionalRole.isPresent()) {
            return null;
        }
        account.setStatus(AccountStatusEnum.ACTIVE);
        account.setRoles(new HashSet<>(Collections.singletonList(optionalRole.get())));
        account.setPasswordHash(encoder.encode(account.getPasswordHash()));
        return accountRepository.save(account);
    }
    public List<Account> findAll(){
        return accountRepository.findAll();
    }
    public Account getInfo(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
             Optional<Account> accountOptional = accountRepository.findByUsername(authentication.getName());
             if (accountOptional.isPresent()){
                 return accountOptional.get();
             }
        }
        return  null;
    }

}
