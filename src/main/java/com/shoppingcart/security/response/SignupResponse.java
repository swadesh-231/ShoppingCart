package com.shoppingcart.security.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SignupResponse {
    private String message;
    private boolean success;
    private String username;
    private String email;

    public static SignupResponse success(String username, String email) {
        return SignupResponse.builder()
                .message("User registered successfully!")
                .success(true)
                .username(username)
                .email(email)
                .build();
    }
}
