package com.pro.tameit.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "forgetpassword")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ForgetPassword {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long fpid;

    @Column(nullable = false)
    private String otp;

    @Column(nullable = false)
    private Date expirationTime;

    @OneToOne
    private User user;
}
