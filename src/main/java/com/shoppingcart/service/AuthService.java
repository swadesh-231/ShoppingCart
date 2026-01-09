package com.shoppingcart.service;

import com.shoppingcart.security.request.LoginRequest;
import com.shoppingcart.security.request.SignupRequest;
import com.shoppingcart.security.response.AuthenticationResult;
import com.shoppingcart.security.response.SignupResponse;
import com.shoppingcart.security.response.UserInfoResponse;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;

public interface AuthService {
    AuthenticationResult login(LoginRequest loginRequest);
    SignupResponse register(SignupRequest signUpRequest);
    UserInfoResponse getCurrentUserDetails(Authentication authentication);
    ResponseCookie logoutUser();
}
