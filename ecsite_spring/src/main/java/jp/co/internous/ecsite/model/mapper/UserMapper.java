package jp.co.internous.ecsite.model.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;

import jp.co.internous.ecsite.model.domain.User;

@Mapper
public interface UserMapper {
	
	List<User> findByUserNameAndPassword(
			@Param("userName") String userName,
			@Param("password") String password);

	@Insert("INSERT INTO user (user_name, password, full_name) " +
					"VALUES (#{userName}, #{password}, #{fullName})")
	@Options(useGeneratedKeys=true, keyProperty="id")
	void insert(User user);

}
