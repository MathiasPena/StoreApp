package com.storeapp.storeapp.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.storeapp.storeapp.config.JwtTokenProvider;
import com.storeapp.storeapp.dto.JwtResponse;
import com.storeapp.storeapp.dto.LoginRequest;
import com.storeapp.storeapp.dto.UserRegistrationDTO;
import com.storeapp.storeapp.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserDetailsService userDetailsService;
    private final UserService userService;

    public AuthController(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider,
            UserDetailsService userDetailsService, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userDetailsService = userDetailsService;
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getUsername());
        // Obtener el rol del usuario
        String role = userDetails.getAuthorities().stream()
                .findFirst() // Como hay un solo rol, tomamos el primero
                .map(GrantedAuthority::getAuthority) // Extraemos el nombre del rol (e.g., "ROLE_ADMIN")
                .orElseThrow(() -> new RuntimeException("El usuario no tiene un rol asignado"));

        // Remover el prefijo "ROLE_" para simplificar el almacenamiento del rol en el
        // token
        role = role.replace("ROLE_", "");

        String token = jwtTokenProvider.createToken(userDetails.getUsername(), role);

        return ResponseEntity.ok(new JwtResponse(token));
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@Valid @RequestBody UserRegistrationDTO userRegistrationDTO) {
        userService.registerClient(userRegistrationDTO);
        return ResponseEntity.ok("Usuario registrado con éxito.");
    }
}
