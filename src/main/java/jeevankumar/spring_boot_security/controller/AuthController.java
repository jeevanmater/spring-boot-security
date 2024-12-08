package jeevankumar.spring_boot_security.controller;

import jeevankumar.spring_boot_security.config.JwtTokenProvider;
import jeevankumar.spring_boot_security.dto.JwtAuthenticationResponse;
import jeevankumar.spring_boot_security.dto.LoginRequest;
import jeevankumar.spring_boot_security.dto.SignupRequest;
import jeevankumar.spring_boot_security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
    }
    @PostMapping("/login")
    public ResponseEntity<JwtAuthenticationResponse> authenticateUser(@RequestBody LoginRequest loginRequest){
        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getUsername()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authenticate);
       String jwt = jwtTokenProvider.generateToken(authenticate);
       return ResponseEntity.ok(new JwtAuthenticationResponse(jwt));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignupRequest signupRequest){
        try {
            userService.registerUser(signupRequest);
            return ResponseEntity.ok("User registered successfully...!");
        }catch (RuntimeException exception){
            return ResponseEntity.badRequest().body(exception.getMessage());
        }
    }
}
