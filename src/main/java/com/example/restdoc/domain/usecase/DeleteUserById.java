package com.example.restdoc.domain.usecase;

import com.example.restdoc.domain.service.model.command.DeleteOneUserCommand;

public interface DeleteUserById {
  void execute(DeleteOneUserCommand command);
}
