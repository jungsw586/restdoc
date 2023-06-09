package com.example.restdoc.domain.usecase;

import com.example.restdoc.domain.entity.User;

public interface GetUserById {
  User execute(String id);
}
