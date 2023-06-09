package com.example.restdoc.domain.service;

import com.example.restdoc.domain.entity.User;
import com.example.restdoc.domain.service.model.command.CreateUserCommand;
import com.example.restdoc.domain.service.model.command.DeleteOneUserCommand;
import com.example.restdoc.domain.service.model.command.UpdateUserCommand;
import java.util.List;

public interface UserService {
  List<User> getAll();

  User getOneBy(String id);

  User create(CreateUserCommand command);

  User update(UpdateUserCommand command);

  void deleteOneBy(DeleteOneUserCommand command);
}
