package com.example.restdoc.infra.data.mybatis.repository;

import com.example.restdoc.infra.data.mybatis.vo.UserVo;
import java.util.Optional;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface UserMbRepository {
  @Select(
      "SELECT * FROM user"
  )
  UserVo[] findAll();

  @Select(
      "SELECT * FROM user u WHERE u.id = #{id}"
  )
  Optional<UserVo> findOneBy(
      @Param("id") String id
  );

  @Insert(
      "INSERT INTO user ( id, name ) VALUES ( #{user.id}, #{user.name} )"
  )
  void insert(
      @Param("user") UserVo userVo
  );

  @Update(
      "UPDATE user u SET name = #{user.name} WHERE u.id = #{user.id}"
  )
  void update(
      @Param("user") UserVo userVo
  );

  @Delete(
      "DELETE FROM user u WHERE u.id = #{id}"
  )
  void deleteOneBy(
      @Param("id") String id
  );
}
