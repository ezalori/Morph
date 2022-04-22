package org.ezalori.morph.web.controller;

import java.util.Map;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthController {
  @PostMapping("/login")
  public Map<String, Object> login(@AuthenticationPrincipal User user) {
    return Map.of("user", user);
  }

  @PostMapping("/logout")
  public Map<String, Object> logout() {
    return Map.of("status", 200);
  }
}
