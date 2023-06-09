package com.example.restdoc.domain.usecase.impl;

import com.example.restdoc.domain.interactor.repository.UserRepository;
import com.example.restdoc.domain.service.model.command.DeleteOneUserCommand;
import com.example.restdoc.domain.usecase.DeleteUserById;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DeleteUserByIdImpl implements DeleteUserById {

  private final UserRepository userRepository;

  @Override
  public void execute(DeleteOneUserCommand command) {
    userRepository.deleteOneBy(command.getId());
  }
}
