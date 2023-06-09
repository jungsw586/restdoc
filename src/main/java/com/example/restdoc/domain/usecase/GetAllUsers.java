package com.example.restdoc.domain.usecase;

import com.example.restdoc.domain.entity.User;
import java.util.stream.Stream;

public interface GetAllUsers {
  Stream<User> execute();
}
