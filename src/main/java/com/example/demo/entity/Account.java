package com.example.demo.entity;

import com.example.demo.enumClass.AccountStatusEnum;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity(name = "accounts")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String username;
    private  String passwordHash;
    @ManyToMany(cascade = {CascadeType.MERGE,CascadeType.DETACH,CascadeType.REMOVE},fetch = FetchType.EAGER)
    @JoinTable(
            name = "account_role",
            joinColumns =@JoinColumn(name = "accountId"),
            inverseJoinColumns = @JoinColumn(name = "roleId")
    )
    @JsonManagedReference
    private Set<Role> roles;
    private AccountStatusEnum status;
    @CreatedDate
    private Date createdAt;
    @LastModifiedDate
    private Date updatedAt;
    private String verifyCode;


}
