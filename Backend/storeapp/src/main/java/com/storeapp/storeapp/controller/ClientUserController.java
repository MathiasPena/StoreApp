package com.storeapp.storeapp.controller;

import java.security.Principal;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.storeapp.storeapp.dto.UserUpdateDTO;
import com.storeapp.storeapp.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/client/users")
@PreAuthorize("hasRole('CLIENT')")
public class ClientUserController {

    private final UserService userService;

    public ClientUserController(UserService userService) {
        this.userService = userService;
    }

    @PatchMapping("/me")
    public ResponseEntity<Void> updateProfile(@Valid @RequestBody UserUpdateDTO userUpdateDTO, Principal principal) {
        // Se obtiene el usuario autenticado a partir del principal (username)
        userService.updateUserByUsername(principal.getName(), userUpdateDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/me")
    public ResponseEntity<Void> deleteProfile(Principal principal) {
        userService.deleteUserByUsername(principal.getName());
        return ResponseEntity.noContent().build();
    }
}
