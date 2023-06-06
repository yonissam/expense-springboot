package com.alibou.security.otp;

import com.alibou.security.user.User;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
public class OtpService {

    // Map to store generated OTPs and their expiration times
    private final Map<String, Otp> otpMap = new HashMap<>();

    // OTP expiration time in minutes
    private static final int OTP_EXPIRATION_MINUTES = 15;

    public String generateOTP(String email, User user) {
        // Generate a random 6-digit OTP
        Random random = new Random();
        String otp = String.format("%06d", random.nextInt(1000000));

        // Calculate the expiration time for the OTP
        LocalDateTime expirationTime = LocalDateTime.now().plusMinutes(OTP_EXPIRATION_MINUTES);

        // Store the OTP and its expiration time in the map
        otpMap.put(email, new Otp(otp, expirationTime, user));

        return otp;
    }

    public boolean verifyOTP(String email, String otp) {
        // Check if the email exists in the map
        if (otpMap.containsKey(email)) {
            Otp otpDetails = otpMap.get(email);

            // Check if the OTP matches and has not expired
            if (otp.equals(otpDetails.getOtp()) && LocalDateTime.now().isBefore(otpDetails.getExpirationTime())) {
                // OTP is valid
                otpMap.remove(email); // Remove the OTP entry from the map
                return true;
            }
        }

        return false;
    }
}
