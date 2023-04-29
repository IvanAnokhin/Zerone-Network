package ru.skillbox.zerone.backend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.skillbox.zerone.backend.controller.swaggerdoc.SwaggerChangeEmailController;
import ru.skillbox.zerone.backend.model.dto.response.CommonResponseDTO;
import ru.skillbox.zerone.backend.model.dto.response.MessageResponseDTO;
import ru.skillbox.zerone.backend.service.UserService;

@RestController
@RequiredArgsConstructor
public class ChangeEmailController implements SwaggerChangeEmailController {
  private final UserService userService;

  @GetMapping(value = "/changeemail/complete")
  public CommonResponseDTO<MessageResponseDTO> changeEmailConfirm(@RequestParam String userId, @RequestParam String token) {
    return userService.changeEmailConfirm(userId, token);
  }
}
