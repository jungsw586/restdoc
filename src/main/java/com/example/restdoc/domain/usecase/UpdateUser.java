package com.example.restdoc.domain.usecase;

import com.example.restdoc.domain.entity.User;
import com.example.restdoc.domain.service.model.command.UpdateUserCommand;

public interface UpdateUser {
  User execute(UpdateUserCommand command);
}
