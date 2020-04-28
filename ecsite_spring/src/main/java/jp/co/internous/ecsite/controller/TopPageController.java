package jp.co.internous.ecsite.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/top")
public class TopPageController {
	
	@RequestMapping("/")
	public String index() {
		
		return "toppage";
	}

}
