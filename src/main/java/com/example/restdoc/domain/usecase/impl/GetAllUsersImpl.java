package com.example.restdoc.domain.usecase.impl;

import com.example.restdoc.domain.entity.User;
import com.example.restdoc.domain.interactor.repository.UserRepository;
import com.example.restdoc.domain.usecase.GetAllUsers;
import java.util.stream.Stream;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class GetAllUsersImpl implements GetAllUsers {

  private final UserRepository userRepository;

  @Override
  public Stream<User> execute() {
    return userRepository.findAll().map(
        vo -> new User(vo.getId(), vo.getName())
    );
  }
}
