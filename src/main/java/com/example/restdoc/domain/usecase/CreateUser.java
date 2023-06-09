package com.example.restdoc.domain.usecase;

import com.example.restdoc.domain.entity.User;
import com.example.restdoc.domain.service.model.command.CreateUserCommand;

public interface CreateUser {

  User execute(CreateUserCommand command);
}
