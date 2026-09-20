package org.example.campusmarket.mapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Insert;
import org.example.campusmarket.entity.User;
@Mapper

public interface UserMapper {
    @Select("SELECT * FROM user WHERE username = #{username}")
    User findByUsername(String username);
    @Select("SELECT * FROM user WHERE id = #{id}")
    User findById(Long id);
    @Insert("INSERT INTO user(username, password, nickname, phone) " +
            "VALUES(#{username}, #{password}, #{nickname}, #{phone})")
    int insert(User user);
}
