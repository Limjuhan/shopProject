package kr.gdu.dao;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import kr.gdu.dao.mapper.AdminMapper;
import kr.gdu.logic.User;

@Repository
public class AdminDao {

	@Autowired 
	private SqlSessionTemplate template;
	
	private final Class<AdminMapper> cls = AdminMapper.class;
	
	public List<User> getUserList() {
		return template.getMapper(cls).select();  
	}
}
