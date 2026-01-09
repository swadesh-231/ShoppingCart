package com.shoppingcart.service.impl;

import com.shoppingcart.entity.Role;
import com.shoppingcart.entity.User;
import com.shoppingcart.entity.enums.UserRoles;
import com.shoppingcart.exception.DuplicateResourceException;
import com.shoppingcart.exception.ResourceNotFoundException;
import com.shoppingcart.repository.RoleRepository;
import com.shoppingcart.repository.UserRepository;
import com.shoppingcart.security.jwt.JwtUtils;
import com.shoppingcart.security.request.LoginRequest;
import com.shoppingcart.security.request.SignupRequest;
import com.shoppingcart.security.response.AuthenticationResult;
import com.shoppingcart.security.response.SignupResponse;
import com.shoppingcart.security.response.UserInfoResponse;
import com.shoppingcart.security.service.CustomUserDetailsImpl;
import com.shoppingcart.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder encoder;

    @Override
    public AuthenticationResult login(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        CustomUserDetailsImpl userDetails = (CustomUserDetailsImpl) authentication.getPrincipal();

        ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(userDetails);

        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        UserInfoResponse response = UserInfoResponse.builder()
                .id(userDetails.getId())
                .username(userDetails.getUsername())
                .email(userDetails.getEmail())
                .roles(roles)
                .jwtToken(jwtCookie.getValue())
                .build();

        return new AuthenticationResult(response, jwtCookie);
    }

    @Override
    public SignupResponse register(SignupRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            throw new DuplicateResourceException("Username is already taken!");
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            throw new DuplicateResourceException("Email is already in use!");
        }

        User user = User.builder()
                .username(signUpRequest.getUsername())
                .email(signUpRequest.getEmail())
                .password(encoder.encode(signUpRequest.getPassword()))
                .build();

        Set<String> strRoles = signUpRequest.getRole();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null || strRoles.isEmpty()) {
            Role userRole = roleRepository.findByRoleName(UserRoles.ROLE_USER)
                    .orElseThrow(() -> new ResourceNotFoundException("Role", "name", "ROLE_USER"));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role.toLowerCase()) {
                    case "admin":
                        Role adminRole = roleRepository.findByRoleName(UserRoles.ROLE_ADMIN)
                                .orElseThrow(() -> new ResourceNotFoundException("Role", "name", "ROLE_ADMIN"));
                        roles.add(adminRole);
                        break;
                    case "seller":
                        Role sellerRole = roleRepository.findByRoleName(UserRoles.ROLE_SELLER)
                                .orElseThrow(() -> new ResourceNotFoundException("Role", "name", "ROLE_SELLER"));
                        roles.add(sellerRole);
                        break;
                    default:
                        Role userRole = roleRepository.findByRoleName(UserRoles.ROLE_USER)
                                .orElseThrow(() -> new ResourceNotFoundException("Role", "name", "ROLE_USER"));
                        roles.add(userRole);
                }
            });
        }

        user.setRoles(roles);
        userRepository.save(user);

        return SignupResponse.success(signUpRequest.getUsername(), signUpRequest.getEmail());
    }

    @Override
    public UserInfoResponse getCurrentUserDetails(Authentication authentication) {
        CustomUserDetailsImpl userDetails = (CustomUserDetailsImpl) authentication.getPrincipal();

        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        return UserInfoResponse.builder()
                .id(userDetails.getId())
                .username(userDetails.getUsername())
                .email(userDetails.getEmail())
                .roles(roles)
                .build();
    }

    @Override
    public ResponseCookie logoutUser() {
        return jwtUtils.getCleanJwtCookie();
    }
}
