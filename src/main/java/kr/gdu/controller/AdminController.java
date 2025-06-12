package kr.gdu.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;
import kr.gdu.logic.User;
import kr.gdu.service.AdminService;

@Controller
@RequestMapping("admin")
public class AdminController {
	
	private final AdminService adminService;
	
	public AdminController(AdminService adminService) {
		this.adminService = adminService;
	}
	
	@GetMapping("/list")
	public String getUserList(Model model,HttpSession session) {
		List<User> users = adminService.getUserList();
		model.addAttribute("list",users);
		
		return "/admin/list";
	}
}
