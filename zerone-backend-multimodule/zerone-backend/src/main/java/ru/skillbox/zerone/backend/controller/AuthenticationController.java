package ru.skillbox.zerone.backend.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.skillbox.zerone.backend.controller.swaggerdoc.SwaggerAuthenticationController;
import ru.skillbox.zerone.backend.model.dto.request.AuthRequestDTO;
import ru.skillbox.zerone.backend.model.dto.response.CommonResponseDTO;
import ru.skillbox.zerone.backend.model.dto.response.MessageResponseDTO;
import ru.skillbox.zerone.backend.model.dto.response.UserDTO;
import ru.skillbox.zerone.backend.service.LoginService;

@Validated
@RestController
@RequestMapping(value = "/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController implements SwaggerAuthenticationController {
  private final LoginService loginService;

  @PostMapping("/login")
  public CommonResponseDTO<UserDTO> login(@Valid @RequestBody AuthRequestDTO requestDto) {
    return loginService.login(requestDto);
  }

  @GetMapping("/logout")
  public CommonResponseDTO<MessageResponseDTO> logout(
      @RequestHeader(name = "Authorization", required = false) String token) {
    return loginService.logout(token);
  }
}
