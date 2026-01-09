package com.shoppingcart.security.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.ResponseCookie;

@Data
@AllArgsConstructor
public class AuthenticationResult {
    private UserInfoResponse response;
    private ResponseCookie jwtCookie;
}
