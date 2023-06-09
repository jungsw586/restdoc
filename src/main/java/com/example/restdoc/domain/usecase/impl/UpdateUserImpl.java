package com.example.restdoc.domain.usecase.impl;

import com.example.restdoc.domain.entity.User;
import com.example.restdoc.domain.interactor.repository.UserRepository;
import com.example.restdoc.domain.service.model.command.UpdateUserCommand;
import com.example.restdoc.domain.usecase.UpdateUser;
import com.example.restdoc.infra.data.mybatis.vo.UserVo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UpdateUserImpl implements UpdateUser {

  private final UserRepository userRepository;

  @Override
  public User execute(UpdateUserCommand command) {
    UserVo found = userRepository.findOneBy(command.getId()).orElseThrow();
    found.setName(command.getName());
    UserVo saved = userRepository.save(found);
    return new User(
        saved.getId(),
        saved.getName()
    );
  }
}
