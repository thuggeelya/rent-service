package com.thuggeelya.rent.controller;

import com.thuggeelya.rent.model.AppUser;
import com.thuggeelya.rent.model.Role;
import com.thuggeelya.rent.model.enums.ERole;
import com.thuggeelya.rent.payload.request.LoginRequest;
import com.thuggeelya.rent.payload.request.SignupRequest;
import com.thuggeelya.rent.payload.response.JwtResponse;
import com.thuggeelya.rent.payload.response.MessageResponse;
import com.thuggeelya.rent.repository.RoleRepository;
import com.thuggeelya.rent.repository.UserRepository;
import com.thuggeelya.rent.security.JwtUtils;
import com.thuggeelya.rent.security.service.UserDetailsImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/auth")
public class AuthController {

    final AuthenticationManager authenticationManager;
    final UserRepository userRepository;
    final RoleRepository roleRepository;
    final PasswordEncoder encoder;
    final JwtUtils jwtUtils;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager, UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder encoder, JwtUtils jwtUtils) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.encoder = encoder;
        this.jwtUtils = jwtUtils;
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        return ResponseEntity
                .ok(new JwtResponse(jwt, userDetails.getId(), userDetails.getUsername(), userDetails.getEmail(), roles));
    }

    @SuppressWarnings("unused")
    private boolean isLogged() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return null != authentication && !("anonymousUser").equals(authentication.getName());
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
        }

        AppUser user = createUserAccount(signUpRequest);
        userRepository.save(user);
        return ResponseEntity.ok(new MessageResponse("AppUser registered successfully!"));
    }

    private AppUser createUserAccount(SignupRequest signUpRequest) {
        AppUser user = new AppUser(signUpRequest.getUsername(), signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()));
        Set<String> strRoles = signUpRequest.getRole();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            addRole(roles, ERole.ROLE_USER);
        } else {
            parseRoles(strRoles, roles);
        }

        user.setRoles(roles);
        user.setRegistrationDate(LocalDate.now(ZoneId.systemDefault()));
        return user;
    }

    private void parseRoles(Set<String> strRoles, Set<Role> roles) {
        strRoles.forEach(role -> {
            switch (role) {
                case "admin" -> addRole(roles, ERole.ROLE_ADMIN);
                case "mod" -> addRole(roles, ERole.ROLE_MODERATOR);
                default -> addRole(roles, ERole.ROLE_USER);
            }
        });
    }

    private void addRole(Set<Role> roles, ERole role) {
        Role adminRole = roleRepository.findByName(role).orElseThrow(() -> new RuntimeException("Error: Role is not found."));
        roles.add(adminRole);
    }
}