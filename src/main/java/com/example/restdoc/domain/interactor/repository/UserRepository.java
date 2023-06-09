package com.example.restdoc.domain.interactor.repository;

import com.example.restdoc.domain.entity.User;
import com.example.restdoc.infra.data.mybatis.vo.UserVo;
import java.util.Optional;
import java.util.stream.Stream;

public interface UserRepository {

  Stream<UserVo> findAll();

  Optional<UserVo> findOneBy(String id);

  UserVo save(UserVo user);

  void deleteOneBy(String id);
}
