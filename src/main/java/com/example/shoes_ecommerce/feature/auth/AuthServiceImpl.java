package com.example.shoes_ecommerce.feature.auth;
import com.example.shoes_ecommerce.domain.Roles;
import com.example.shoes_ecommerce.domain.UserVerification;
import com.example.shoes_ecommerce.domain.Users;
import com.example.shoes_ecommerce.feature.auth.dto.*;
import com.example.shoes_ecommerce.feature.user.RolesRepository;
import com.example.shoes_ecommerce.feature.user.UsersRepository;
import com.example.shoes_ecommerce.mapping.UserMapping;
import com.example.shoes_ecommerce.util.RandomUtil;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.security.oauth2.server.resource.authentication.BearerTokenAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationProvider;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UsersRepository usersRepository;
    private final RolesRepository rolesRepository;

    private final PasswordEncoder passwordEncoder;
    private final UserMapping userMapping;

    private final  UserVerificationRepository userVerificationRepository;
    private final DaoAuthenticationProvider daoAuthenticationProvider;
    private final JwtAuthenticationProvider jwtAuthenticationProvider;


    private final JavaMailSender javaMailSender;
    private final JwtEncoder accessTokenJwtEncoder;
    private final JwtEncoder refreshTokenJwtEncoder;

    @Value("${spring.mail.username}")
    private String adminEmail;

    @Override
    public AuthResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {

        String refreshToken = refreshTokenRequest.refreshToken();

        // Authentication  client  with refresh token
        Authentication auth =  new BearerTokenAuthenticationToken(refreshToken);
        auth = jwtAuthenticationProvider.authenticate(auth);

        Jwt jwt  = (Jwt) auth.getPrincipal();
        Instant now = Instant.now();
        JwtClaimsSet  jwtClaimsSetAccessToke = JwtClaimsSet.builder()
                .id(jwt.getId())
                .subject("Access API")
                .issuer(jwt.getId())
                .issuedAt(now)
                .expiresAt(now.plus(10, ChronoUnit.SECONDS))
                .audience(List.of("Next Js","Flutter"))
                .claim("isAdmin",true)
                .claim("Client", "IOSDT0001" )
                .claim("scope", jwt.getClaimAsString("scope"))
                .build();

        // Generate  Access Token
        String accessToken  = accessTokenJwtEncoder
                .encode(JwtEncoderParameters.from(jwtClaimsSetAccessToke))
                .getTokenValue();

        // Get expiration of refresh token
         Instant expiresAt = jwt.getExpiresAt();
         long remainingDays = Duration.between(now, expiresAt).toDays();
         if(remainingDays < 1){
             JwtClaimsSet  jwtClaimsSetRefreshToken= JwtClaimsSet.builder()
                     .id(auth.getName())
                     .subject("Refresh Token  ")
                     .issuer(auth.getName())
                     .issuedAt(now)
                     .expiresAt(now.plus(6, ChronoUnit.DAYS))
                     .audience(List.of("Next Js","Flutter"))
                     .claim("isAdmin",true)
                     .claim("scope", jwt.getClaimAsString("scope"))
                     .build();

             // Generate Refresh Token
             refreshToken = refreshTokenJwtEncoder
                     .encode(JwtEncoderParameters.from(jwtClaimsSetRefreshToken))
                     .getTokenValue();
         }

        return AuthResponse.builder()
                .tokenType("Bearer")
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    @Override
    public AuthResponse login(LoginRequest loginRequest) {

        // Authenticate client with username (email) and password
        Authentication auth = new UsernamePasswordAuthenticationToken(loginRequest.email(), loginRequest.password());
        auth = daoAuthenticationProvider.authenticate(auth);
        log.info("Auth: {}", auth.getPrincipal());

        // ROLE_USER ROLE_ADMIN
        String scope = auth
                .getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));
        log.info("scope: {}", scope);

        // Define JwtClaimsSet (Payload)
        Instant now = Instant.now();
        JwtClaimsSet jwtAccessClaimsSet = JwtClaimsSet.builder()
                .id(auth.getName())
                .subject("Access APIs")
                .issuer(auth.getName())
                .issuedAt(now)
                .expiresAt(now.plus(6,ChronoUnit.DAYS))
                .audience(List.of("NextJS", "Android", "iOS"))
                .claim("isAdmin", true)
                .claim("scope", scope)
                .build();

        // Refresh Token  ClaimsSet
         JwtClaimsSet jwtRefreshClaimsSet = JwtClaimsSet.builder()
                .id(auth.getName())
                .subject("Refresh Token")
                .issuer(auth.getName())
                .issuedAt(now)
                .expiresAt(now.plus(1, ChronoUnit.DAYS))
                .audience(List.of("NextJS", "Android", "iOS"))
                .claim("scope", scope)
                .build();

        //2. Generate access token
        String accessToken = accessTokenJwtEncoder
                .encode(JwtEncoderParameters.from(jwtAccessClaimsSet))
                .getTokenValue();

        // Generate Refresh Token
        String refreshToken = refreshTokenJwtEncoder
                .encode(JwtEncoderParameters.from(jwtRefreshClaimsSet))
                .getTokenValue();

        return AuthResponse.builder()
                .tokenType("Bearer")
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    @Override
    public RegisterResponse register(RegisterRequest registerRequest) {

        // Validation an email
        if (usersRepository.existsByEmail(registerRequest.email())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Email has been already in used");
        }

        // Validate phone number
        if (usersRepository.existsByPhoneNumber(registerRequest.phoneNumber())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Phone number has been already in used"
            );
        }

        // Validate Confirm Password
        if (!registerRequest.password().equals(registerRequest.confirmPassword())) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Password  does not match"
            );
        }

        Users users = userMapping.fromRegisterRequest(registerRequest);
        users.setUuid(UUID.randomUUID().toString());
        users.setPassword(passwordEncoder.encode(users.getPassword()));
        users.setIsBlocked(false);
        users.setIsDeleted(false);
        users.setIsVerified(false);
        users.setProfile("profile/default-user.png");

        List<Roles> rolesUserList = rolesRepository.findRolesByUser();
        List<Roles> rolesCustomerList = rolesRepository.findRolesByCustomer();

        Roles rolesUser = rolesUserList.get(0);  // Assuming you just want the first result
        Roles rolesCustomer = rolesCustomerList.get(0);

        List<Roles> roles = List.of(rolesUser, rolesCustomer);
        users.setRoles(roles);

        // Save users
        usersRepository.save(users);

        return RegisterResponse
                .builder()
                .message("You have registered successfully, please verify an email")
                .email(users.getEmail())
                .build();
    }

    @Override
    public void sendVerification(String email) throws MessagingException {

        // Validate Email
        Users users = usersRepository
                .findByEmail(email)
                .orElseThrow(
                        ()-> new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Email has not been found"
                        )

                );

        UserVerification userVerification = new UserVerification();
        userVerification.setUsers(users);
        userVerification.setVerifiedCode(RandomUtil.random6Digits());
        userVerification.setExpiryTime(LocalTime.now().plusMinutes (1));
        userVerificationRepository.save(userVerification);

        //Prepare email for sending
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        helper.setTo(email);
        helper.setFrom(adminEmail);
        helper.setSubject("User Verification ");
        helper.setText(userVerification.getVerifiedCode());

        javaMailSender.send(message);
    }

    @Override
    public ChangePasswordResponse changePassword(String uuid, ChangePasswordRequest changePasswordRequest) {
        Users users = usersRepository
                .findByUuid(uuid)
                .orElseThrow(
                        () -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "User Uuid has not been found"
                        )
                );

        // Validate old password
        if (!passwordEncoder.matches(changePasswordRequest.oldPassword(), users.getPassword())) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Old password does not match"
            );
        }

        // Validate New Password and Confirm Password
        if (!changePasswordRequest.newPassword().equals(changePasswordRequest.confirmPassword())) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "New password and confirm password do not match"
            );
        }

        // Encode and update the new password
        users.setPassword(passwordEncoder.encode(changePasswordRequest.newPassword()));
        usersRepository.save(users);

        // Step 5: Create a new JWT for the user (optional: you can skip this if not needed)
        Instant now = Instant.now();
        JwtClaimsSet jwtClaimsSet = JwtClaimsSet.builder()
                .id(users.getUuid())  // Use UUID or other user identifier
                .subject("Password Changed")
                .issuer("YourAppName")  // Or use user's identifier like email
                .issuedAt(now)
                .expiresAt(now.plus(6, ChronoUnit.DAYS))  // Optional token expiration
                .audience(List.of("NextJS", "Android", "iOS"))  // Adjust according to your app's clients
                .claim("message", "Password changed successfully")
                .build();

        String newAccessToken = accessTokenJwtEncoder
                .encode(JwtEncoderParameters.from(jwtClaimsSet))
                .getTokenValue();

        return ChangePasswordResponse.builder()
                .message("Your password has been changed successfully")
                .email(users.getEmail())
                .accessToken(newAccessToken)
                .build();
    }


    @Override
    public void verify(VerificationRequest verificationRequest) {

        // Validate Emails
        Users users = usersRepository
                .findByEmail(verificationRequest.email())
                .orElseThrow(
                        ()-> new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Email has not been  found"
                        )

                );

        // Validate verified code
        UserVerification userVerification = userVerificationRepository
                .findByUsersAndVerifiedCode(users,verificationRequest.verifiedCode())
                .orElseThrow(
                        ()-> new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "User verification has not been found"
                        )
                );

         // Is verified code expired
        if(LocalTime.now().isAfter(userVerification.getExpiryTime())){
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    "Verification code has expired"
            );
        }
        users.setIsVerified(true);
        usersRepository.save(users);

        userVerificationRepository.delete(userVerification);
    }

    @Override
    public void reSendVerification(String email) throws MessagingException {

        // Validate Email
        Users users = usersRepository
                .findByEmail(email)
                .orElseThrow(
                        ()-> new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Email has not been  found"
                        )

                );

        UserVerification userVerification = userVerificationRepository
                .findByUsers(users)
                        .orElseThrow(
                                ()-> new ResponseStatusException(
                                        HttpStatus.NOT_FOUND,
                                        "User has not been found"
                                )
                        );

        userVerification.setVerifiedCode(RandomUtil.random6Digits());
        userVerification.setExpiryTime(LocalTime.now().plusMinutes (1));
        userVerificationRepository.save(userVerification);

        //Prepare email for sending
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        helper.setTo(email);
        helper.setFrom(adminEmail);
        helper.setSubject("User Verification ");
        helper.setText(userVerification.getVerifiedCode());

        javaMailSender.send(message);

    }


}
