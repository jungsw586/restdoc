package com.example.restdoc.infra.data.interactor.repository;

import com.example.restdoc.domain.interactor.repository.UserRepository;
import com.example.restdoc.infra.data.mybatis.repository.UserMbRepository;
import com.example.restdoc.infra.data.mybatis.vo.UserVo;
import java.util.Optional;
import java.util.stream.Stream;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class UserRepositoryImpl implements UserRepository {

  private final UserMbRepository userMbRepository;

  @Override
  public Stream<UserVo> findAll() {
    return Stream.of(userMbRepository.findAll());
  }

  @Override
  public Optional<UserVo> findOneBy(String id) {
    return userMbRepository.findOneBy(id);
  }

  @Override
  public UserVo save(UserVo user) {
    userMbRepository.findOneBy(user.getId())
        .ifPresentOrElse(
            userMbRepository::update,
            ()->userMbRepository.insert(user)
        );

    return user;
  }

  @Override
  public void deleteOneBy(String id) {
    userMbRepository.deleteOneBy(id);
  }
}
