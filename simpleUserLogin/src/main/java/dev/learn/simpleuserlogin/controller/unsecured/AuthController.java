package dev.learn.simpleuserlogin.controller.unsecured;

import dev.learn.simpleuserlogin.dto.Message;
import dev.learn.simpleuserlogin.modal.User;
import dev.learn.simpleuserlogin.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/auth/")
public class AuthController {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthController(UserRepository userRepository,
                          PasswordEncoder passwordEncoder,
                          AuthenticationManager authenticationManager
    ){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("signup")
    public ResponseEntity<User> signup(@RequestBody User user){
        if(userRepository.findByUsername(user.getUsername()) == null){
            User u = User.builder()
                    .username(user.getUsername())
                    .password(passwordEncoder.encode(user.getPassword()))
                    .build();
            return ResponseEntity.ok(userRepository.save(u));
        }
        return ResponseEntity.status(HttpStatusCode.valueOf(403)).build();
    }

    @PostMapping("login")
    public ResponseEntity<Message> login(@RequestBody User user){
        boolean isLogin = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword())
        ).isAuthenticated();
        return isLogin ?
                ResponseEntity.ok(
                        Message.builder()
                        .status(1)
                        .message("user successfully logged in")
                        .build()) :
                ResponseEntity.status(401).build();
    }
}
