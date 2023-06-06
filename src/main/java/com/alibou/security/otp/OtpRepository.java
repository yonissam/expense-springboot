package com.alibou.security.otp;

import com.alibou.security.demo.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OtpRepository extends JpaRepository<Otp, Integer> {
}
