package com.pro.tameit.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue
    private Long id;
    @Column(unique = true)
    private String userName;
    private String password;
    @Transient
    private String confirmPassword;
    private String email;
    private String verificationToken;
    @ColumnDefault("false")
    private boolean isEmailVerified;
}