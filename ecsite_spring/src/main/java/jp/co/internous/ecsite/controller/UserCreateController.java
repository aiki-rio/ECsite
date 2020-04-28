package jp.co.internous.ecsite.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.internous.ecsite.model.domain.User;
import jp.co.internous.ecsite.model.form.UserCreateForm;
import jp.co.internous.ecsite.model.mapper.UserMapper;

@Controller
@RequestMapping("/usercreate")
public class UserCreateController {
	
	@Autowired
	UserMapper userMapper;
	
	@RequestMapping("/")
	public String index() {
		
		return "usercreate";
	}
	
	@RequestMapping("/userConfirm")
	public String userConfirm(UserCreateForm createForm, Model m) {
		m.addAttribute("userName", createForm.getUserName());
		m.addAttribute("password", createForm.getPassword());
		m.addAttribute("fullName", createForm.getFullName());
		
		return "userconfirm";
	}
	
	@RequestMapping("/confirmComplete")
	public String confirmComplete(UserCreateForm createForm, Model m) {
		User user = new User();
		user.setUserName(createForm.getUserName());
		user.setPassword(createForm.getPassword());
		user.setFullName(createForm.getFullName());
		userMapper.insert(user);
		
		return "confirmcomplete";
	}
}

