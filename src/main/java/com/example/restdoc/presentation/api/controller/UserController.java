package com.example.restdoc.presentation.api.controller;

import com.example.restdoc.domain.entity.User;
import com.example.restdoc.domain.service.UserService;
import com.example.restdoc.domain.service.model.command.CreateUserCommand;
import com.example.restdoc.domain.service.model.command.DeleteOneUserCommand;
import com.example.restdoc.domain.service.model.command.UpdateUserCommand;
import com.example.restdoc.presentation.api.controller.request.CreateUserRequest;
import com.example.restdoc.presentation.api.controller.request.UpdateUserRequest;
import com.example.restdoc.presentation.api.controller.response.UserResponse;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {

  private final UserService userService;

  @GetMapping()
  public ResponseEntity<List<UserResponse>> getAll() {
    List<UserResponse> response = userService.getAll()
        .stream().map(
            entity -> new UserResponse(
                entity.getId(),
                entity.getName()
            )
        )
        .toList();

    return new ResponseEntity(
        response,
        HttpStatus.OK
    );
  }

  @GetMapping("/{id}")
  public ResponseEntity<UserResponse> getOneBy(
      @PathVariable String id
  ) {
    User user = userService.getOneBy(id);

    UserResponse response = new UserResponse(
        user.getId(),
        user.getName()
    );

    return new ResponseEntity(
        response,
        HttpStatus.OK
    );
  }

  @PostMapping()
  public ResponseEntity<UserResponse> createUser(
      @RequestBody CreateUserRequest request
  ) {
    User user = userService.create(
        new CreateUserCommand(request.getName())
    );

    UserResponse response = new UserResponse(
        user.getId(),
        user.getName()
    );

    return new ResponseEntity(
        response,
        HttpStatus.OK
    );
  }

  @PutMapping()
  public ResponseEntity<UserResponse> udpateUser(
      @RequestBody UpdateUserRequest request
  ) {
    User user = userService.update(
        new UpdateUserCommand(
            request.getId(),
            request.getName()
        )
    );

    UserResponse response = new UserResponse(
        user.getId(),
        user.getName()
    );

    return new ResponseEntity(
        response,
        HttpStatus.OK
    );
  }

  @DeleteMapping("/{id}")
  public ResponseEntity deleteOneBy(
      @PathVariable String id
  ) {
    userService.deleteOneBy(
        new DeleteOneUserCommand(id)
    );

    return new ResponseEntity(HttpStatus.OK);
  }
}
