package com.storeapp.storeapp.service;

import com.storeapp.storeapp.dto.UserCreationDTO;
import com.storeapp.storeapp.dto.UserDTO;
import com.storeapp.storeapp.dto.UserRegistrationDTO;
import com.storeapp.storeapp.dto.UserUpdateDTO;
import com.storeapp.storeapp.exception.EmailAlreadyExistsException;
import com.storeapp.storeapp.exception.ResourceNotFoundException;
import com.storeapp.storeapp.exception.UsernameAlreadyExistsException;
import com.storeapp.storeapp.model.User;
import com.storeapp.storeapp.repository.UserRepository;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<UserDTO> findAllUsers() {
        return userRepository.findAll().stream()
                .map(this::convertToDTO)
                .toList();
    }

    public User findUserById(Long id) {
        return userRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Usuario con ID: " + id + " no encontrado"));
    }

    public void createUser(UserCreationDTO userCreationDTO) {
        if (userRepository.existsByUsername(userCreationDTO.getUsername())) {
        throw new UsernameAlreadyExistsException("El nombre de usuario ya está en uso.");
    }

    if (userRepository.existsByEmail(userCreationDTO.getEmail())) {
        throw new EmailAlreadyExistsException("El email ya está en uso.");
    }

        User user = new User();
        user.setUsername(userCreationDTO.getUsername());
        user.setEmail(userCreationDTO.getEmail());
        user.setPassword(passwordEncoder.encode(userCreationDTO.getPassword()));
        user.setRole(userCreationDTO.getRole());
        user.setActive(true);
        userRepository.save(user);
    }

    public void registerClient(UserRegistrationDTO userRegistrationDTO) {
        if (userRepository.existsByUsername(userRegistrationDTO.getUsername())) {
            throw new UsernameAlreadyExistsException("El nombre de usuario ya está en uso.");
        }
    
        if (userRepository.existsByEmail(userRegistrationDTO.getEmail())) {
            throw new EmailAlreadyExistsException("El email ya está en uso.");
        }

        User user = new User();
        user.setUsername(userRegistrationDTO.getUsername());
        user.setEmail(userRegistrationDTO.getEmail());
        user.setPassword(passwordEncoder.encode(userRegistrationDTO.getPassword()));
        user.setRole("CLIENT");
        user.setActive(true);

        userRepository.save(user);
    }

    public User updateUser(Long id, UserUpdateDTO userUpdateDTO) {
        User existingUser = findUserById(id);

        // Solo actualiza los campos que recibes
    if (userUpdateDTO.getUsername() != null) {
        existingUser.setUsername(userUpdateDTO.getUsername());
    }
    if (userUpdateDTO.getEmail() != null) {
        existingUser.setEmail(userUpdateDTO.getEmail());
    }
    if (userUpdateDTO.getRole() != null) {
        existingUser.setRole(userUpdateDTO.getRole());
    }
    if (userUpdateDTO.getPassword() != null) {
        existingUser.setPassword(passwordEncoder.encode(userUpdateDTO.getPassword()));
    }
        return userRepository.save(existingUser);
    }

    public void deleteUser(Long id) {
        User user = findUserById(id);

        if (!user.isActive()) {
            throw new IllegalStateException("El usuario ya está inactivo.");
        }

        user.setActive(false);
        userRepository.save(user);
    }

    private UserDTO convertToDTO(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        dto.setRole(user.getRole());
        return dto;
    }
}
