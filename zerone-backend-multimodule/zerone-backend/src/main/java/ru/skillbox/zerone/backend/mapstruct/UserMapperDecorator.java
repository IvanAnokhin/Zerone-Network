package ru.skillbox.zerone.backend.mapstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.skillbox.zerone.backend.model.dto.request.RegisterRequestDTO;
import ru.skillbox.zerone.backend.model.dto.response.UserDTO;
import ru.skillbox.zerone.backend.model.entity.Role;
import ru.skillbox.zerone.backend.model.entity.User;
import ru.skillbox.zerone.backend.repository.FriendshipRepository;
import ru.skillbox.zerone.backend.repository.WebSocketConnectionRepository;
import ru.skillbox.zerone.backend.service.RoleService;
import ru.skillbox.zerone.backend.util.CurrentUserUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static ru.skillbox.zerone.backend.model.enumerated.FriendshipStatus.BLOCKED;

public abstract class UserMapperDecorator implements UserMapper {
  @Autowired
  private UserMapper userMapper;
  @Autowired
  private PasswordEncoder passwordEncoder;
  @Autowired
  private RoleService roleService;
  @Autowired
  private WebSocketConnectionRepository webSocketConnectionRepository;
  @Autowired
  private FriendshipRepository friendshipRepository;

  @Override
  public User registerRequestDTOToUser(RegisterRequestDTO registerRequestDTO, String confirmationCode, String photo) {
    var user = userMapper.registerRequestDTOToUser(registerRequestDTO, confirmationCode, photo);
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    List<Role> roles = new ArrayList<>();
    roles.add(roleService.getBasicUserRole());
    user.setRoles(roles);
    return user;
  }

  @Override
  public UserDTO userToUserDTO(User user) {
    var userDTO = userMapper.userToUserDTO(user);
    var curUser = CurrentUserUtils.getCurrentUser();
    if (Boolean.TRUE.equals(user.getIsBlocked())) {
      userDTO.setFirstName("Пользователь");
      userDTO.setLastName(" заблокирован администрацией");
    }
    if (Boolean.TRUE.equals(user.getIsDeleted())) {
      userDTO.setFirstName("Пользователь");
      userDTO.setLastName(" удален");
    }

    userDTO.setBlockedByMe(
        friendshipRepository.existsBySrcPersonAndDstPersonAndStatus(curUser, user, BLOCKED));

    if (webSocketConnectionRepository.existsByUserId(user.getId().toString())) {
      userDTO.setLastOnlineTime(LocalDateTime.now());
    }
    return userDTO;
  }
}
