package com.shoeshelf.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
@CrossOrigin("http://localhost:5173")
public class UserController {

    private final AuthenticationProvider authenticationProvider;

    /* User bu alanda kontrol ediliyor  */
    @PostMapping("/check-user")
    public ResponseEntity<Boolean> checkUser(@RequestParam String user, @RequestParam String password) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(user, password);

        Authentication authenticate = authenticationProvider.authenticate(usernamePasswordAuthenticationToken);
        boolean authenticated = authenticate.isAuthenticated();

        return ResponseEntity.ok(authenticated);
    }
}
