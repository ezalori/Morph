package org.ezalori.morph.web.controller;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import lombok.RequiredArgsConstructor;
import org.ezalori.morph.web.AppException;
import org.ezalori.morph.web.form.LoginForm;
import org.ezalori.morph.web.model.User;
import org.ezalori.morph.web.repository.UserRepository;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
  private final UserRepository userRepo;

  @PostMapping("/login")
  public Map<String, Object> login(@Valid LoginForm loginForm, BindingResult bindingResult, HttpSession session) {
    if (bindingResult.hasErrors()) {
      throw new AppException(bindingResult.toString());
    }
    var user = userRepo.findByUsernameAndPassword(loginForm.getUsername(), loginForm.getPassword())
        .orElseThrow(() -> new AppException("Invalid username or password."));

    session.setAttribute("user", user);

    return Map.of("id", user.getId());
  }

  @GetMapping("/test")
  public Map<String, Object> test(@SessionAttribute(value = "user", required = false) User user) {
    var payload = new HashMap<String, Object>();
    payload.put("user", user);
    return payload;
  }
}
