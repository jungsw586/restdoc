package com.example.restdoc.domain.service.impl;

import com.example.restdoc.domain.entity.User;
import com.example.restdoc.domain.service.UserService;
import com.example.restdoc.domain.service.model.command.CreateUserCommand;
import com.example.restdoc.domain.service.model.command.DeleteOneUserCommand;
import com.example.restdoc.domain.service.model.command.UpdateUserCommand;
import com.example.restdoc.domain.usecase.CreateUser;
import com.example.restdoc.domain.usecase.DeleteUserById;
import com.example.restdoc.domain.usecase.GetAllUsers;
import com.example.restdoc.domain.usecase.GetUserById;
import com.example.restdoc.domain.usecase.UpdateUser;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

  private final GetAllUsers getAllUsers;
  private final GetUserById getUserById;
  private final CreateUser createUser;
  private final UpdateUser updateUser;
  private final DeleteUserById deleteUserById;

  @Override
  public List<User> getAll() {
    return getAllUsers.execute().toList();
  }

  @Override
  public User getOneBy(String id) {
    return getUserById.execute(id);
  }

  @Override
  public User create(CreateUserCommand command) {
    return createUser.execute(command);
  }

  @Override
  public User update(UpdateUserCommand command) {
    return updateUser.execute(command);
  }

  @Override
  public void deleteOneBy(DeleteOneUserCommand command) {
    deleteUserById.execute(command);
  }
}
