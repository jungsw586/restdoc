package com.example.restdoc.domain.usecase.impl;

import com.example.restdoc.domain.entity.User;
import com.example.restdoc.domain.interactor.repository.UserRepository;
import com.example.restdoc.domain.usecase.GetUserById;
import com.example.restdoc.infra.data.mybatis.vo.UserVo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class GetUserByIdImpl implements GetUserById {

  private final UserRepository userRepository;

  @Override
  public User execute(String id) {
    UserVo userVo = userRepository.findOneBy(id).orElseThrow();
    return new User(
        userVo.getId(),
        userVo.getName()
    );
  }
}
