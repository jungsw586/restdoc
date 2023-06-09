package com.example.restdoc.domain.usecase.impl;

import com.example.restdoc.domain.entity.User;
import com.example.restdoc.domain.interactor.repository.UserRepository;
import com.example.restdoc.domain.service.model.command.CreateUserCommand;
import com.example.restdoc.domain.usecase.CreateUser;
import com.example.restdoc.infra.data.mybatis.vo.UserVo;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CreateUserImpl implements CreateUser {

  private final UserRepository userRepository;

  @Override
  public User execute(CreateUserCommand command) {
    String id = UUID.randomUUID().toString();
    UserVo user = userRepository.save(
        new UserVo(
            id,
            command.getName()
        )
    );
    return new User(
        user.getId(),
        user.getName()
    );
  }
}
