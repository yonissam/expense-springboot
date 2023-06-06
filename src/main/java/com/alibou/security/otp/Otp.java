package com.alibou.security.otp;

import com.alibou.security.token.TokenType;
import com.alibou.security.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "otp")
@Entity
public class Otp {

    @Id
    @GeneratedValue
    public Integer id;

    @Column(unique = true)
    public String otp;

    public LocalDateTime expirationTime;

    @ManyToOne
    @JoinColumn(name = "user_id")
    public User user;

    public Otp(String otp, LocalDateTime expirationTime,  User user) {
        this.otp = otp;
        this.expirationTime = expirationTime;
        this.user = user;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public LocalDateTime getExpiration() {
        return expirationTime;
    }

    public void setExpiration(LocalDateTime expiration) {
        this.expirationTime = expiration;
    }

}
