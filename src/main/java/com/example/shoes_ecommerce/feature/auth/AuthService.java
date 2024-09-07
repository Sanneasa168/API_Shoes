package com.example.shoes_ecommerce.feature.auth;

import com.example.shoes_ecommerce.feature.auth.dto.*;
import jakarta.mail.MessagingException;

public interface AuthService {

    AuthResponse refreshToken(RefreshTokenRequest refreshTokenRequest);

    AuthResponse login(LoginRequest loginRequest);

    RegisterResponse register(RegisterRequest registerRequest);

    void sendVerification(String email) throws MessagingException;

    ChangePasswordResponse changePassword(String uuid,ChangePasswordRequest changePasswordRequest);

    void verify(VerificationRequest verificationRequest);

    void reSendVerification(String email) throws MessagingException;



}
