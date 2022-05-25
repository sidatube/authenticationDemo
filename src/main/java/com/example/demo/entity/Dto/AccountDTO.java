package com.example.demo.entity.Dto;

import com.example.demo.entity.Account;
import com.example.demo.entity.Role;
import com.example.demo.enumClass.AccountStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class AccountDTO {
    private long id;
    private String username;
    private String role;
    private AccountStatusEnum status;
    private Date createdAt;
    private Date updatedAt;

    public AccountDTO(Account account) {
        this.id = account.getId();
        this.username = account.getUsername();
        this.role = account.getRoles().toString().substring(1, account.getRoles().toString().length() - 1);
        this.status = account.getStatus();
        this.createdAt = account.getCreatedAt();
        this.updatedAt = account.getUpdatedAt();
    }
}
