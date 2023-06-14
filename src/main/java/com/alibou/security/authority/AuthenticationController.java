package com.alibou.security.authority;

import com.alibou.security.otp.Otp;
import com.alibou.security.otp.OtpRepository;
import com.alibou.security.otp.OtpRequest;
import com.alibou.security.otp.OtpService;
import com.alibou.security.service.EmailSenderService;
import com.alibou.security.user.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/v1/auth")

public class AuthenticationController {

    private final AuthenticationService service;

    private final UserRepository repository;

    private final OtpRepository otpRepository;

    private final OtpService otpService;

    private final EmailSenderService emailService;

    public AuthenticationController(AuthenticationService service, UserRepository repository, OtpRepository otpRepository, OtpService otpService, EmailSenderService emailService) {
        super();
        this.service = service;
        this.repository = repository;
        this.otpRepository = otpRepository;
        this.otpService = otpService;
        this.emailService = emailService;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody RegisterRequest request
    ) {
        var checkUser = repository.findByEmail(request.getEmail())
                .orElse(null);
        if (checkUser != null) {
            String responseText = "Email already exists";
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new AuthenticationResponse(responseText));
        } else {
            // Send email notification
            String subject = "Account Creation";
            String body = "Your account has been created successfully. Enjoy our website";
            emailService.sendSimpleEmail(request.getEmail(), body, subject);
            return ResponseEntity.ok(service.register(request));
        }
    }

    @PostMapping("/refresh-token")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
       service.refreshToken(request, response);
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request
    ) {
        return ResponseEntity.ok(service.authenticate(request));
    }

    @PostMapping("/reset")
    public ResponseEntity<String> reset(
            @RequestBody AuthenticationRequest request
    ) {
        var user = repository.findByEmail(request.getEmail()).orElse(null);
        if (user != null) {
            // Generate OTP
            String otp = otpService.generateOTP(user.getEmail(), user);

            // Set expiration to 15 minutes from now
            LocalDateTime expiration = LocalDateTime.now().plusMinutes(15);

            // Save OTP in repository
            Otp otpEntity = new Otp(otp, expiration, user);
            otpRepository.save(otpEntity);

            // Send email notification
            String subject = "Password Reset OTP";
            String body = "Your OTP for password reset is: " + otp;
            emailService.sendSimpleEmail(user.getEmail(), body, subject);

            // Return success message
            String message = "OTP generated and email sent";
            return ResponseEntity.ok(message);
        } else {
            // User not found
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<String> verifyOtp(
            @RequestBody OtpRequest request
    ) {
        String otp = request.getOtp();
        String email = request.getEmail();

        if (otpService.verifyOTP(email, otp)) {
            return ResponseEntity.ok("OTP verified successfully");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid OTP");
        }
    }

    @PostMapping("/password-reset")
    public ResponseEntity<String> verifyOtp(
            @RequestBody RegisterRequest request
    ) {
        var response = service.reset(request);

        // Send email notification
        String subject = "Password Reset Notification";
        String body = "Password reset was successful";
        emailService.sendSimpleEmail(request.getEmail(), body, subject);

        return ResponseEntity.ok(response);
    }
}

