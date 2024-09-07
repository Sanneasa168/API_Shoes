package com.example.shoes_ecommerce.feature.auth;

import com.example.shoes_ecommerce.feature.auth.dto.*;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/refresh-token")
    AuthResponse refreshToken( @Valid @RequestBody RefreshTokenRequest refreshTokenRequest) throws MessagingException {
        return  authService.refreshToken(refreshTokenRequest);
    }

    @PostMapping("/login")
    AuthResponse login(@Valid  @RequestBody  LoginRequest loginRequest) {
        return  authService.login(loginRequest);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/register")
    RegisterResponse register(@Valid @RequestBody RegisterRequest registerRequest) {
        return  authService.register(registerRequest);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PostMapping("/send-verification")
    void sendVerification(@Valid @RequestBody SendVerificationRequest sendVerificationRequest) throws MessagingException {
        authService.sendVerification(sendVerificationRequest.email());
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PostMapping("/resend-verification")
    void resendVerification(@Valid @RequestBody SendVerificationRequest sendVerificationRequest) throws MessagingException {
        authService.reSendVerification(sendVerificationRequest.email());
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PostMapping("/verify")
    void verify(@Valid @RequestBody VerificationRequest verificationRequest) {
        authService.verify(verificationRequest);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PutMapping("/{uuid}")
    ChangePasswordResponse updatePassword(@Valid @RequestBody ChangePasswordRequest changePasswordRequest, @PathVariable("uuid") String uuid) throws MessagingException {
        return  authService.changePassword(uuid, changePasswordRequest);
    }

}
