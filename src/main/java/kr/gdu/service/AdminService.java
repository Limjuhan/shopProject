package kr.gdu.service;

import java.util.List;

import org.springframework.stereotype.Service;

import kr.gdu.dao.AdminDao;
import kr.gdu.logic.User;

@Service
public class AdminService {
	
	private final AdminDao adminDao;
	
	public AdminService(AdminDao adminDao) {
		this.adminDao = adminDao;
	}
	
	public List<User> getUserList() {
		return adminDao.getUserList();
	}

}
