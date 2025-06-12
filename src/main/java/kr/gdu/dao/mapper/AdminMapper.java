package kr.gdu.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import kr.gdu.logic.User;
@Mapper
public interface AdminMapper {
	
	@Select(
		"select * "
		+ "from useraccount "
		+ "order by userid")
    List<User> select();
	
	
}
