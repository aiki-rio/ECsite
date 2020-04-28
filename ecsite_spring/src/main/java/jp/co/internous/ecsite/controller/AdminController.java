package jp.co.internous.ecsite.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import jp.co.internous.ecsite.model.domain.Goods;
import jp.co.internous.ecsite.model.domain.User;
import jp.co.internous.ecsite.model.form.GoodsForm;
import jp.co.internous.ecsite.model.form.LoginForm;
import jp.co.internous.ecsite.model.mapper.GoodsMapper;
import jp.co.internous.ecsite.model.mapper.UserMapper;

@Controller
@RequestMapping("/ecsite/admin")
public class AdminController {
	
	@Autowired
	UserMapper userMapper;
	
	@Autowired
	GoodsMapper goodsMapper;
	
	@RequestMapping("/")
	public String index() {
		return "adminindex";
	}
	
	@RequestMapping("/welcome")
	public String welcome(LoginForm loginForm, Model m) {
		List<User> users = userMapper.findByUserNameAndPassword(loginForm.getUserName(), loginForm.getPassword());
		
		if (users != null && users.size() > 0) {
			/* ログイン成功 */
			boolean isAdmin = users.get(0).getIsAdmin() != 0;
			/* 管理者の場合 */
			if (isAdmin) {
				List<Goods> goods = goodsMapper.findAll();
				m.addAttribute("userName", users.get(0).getUserName());
				m.addAttribute("password", users.get(0).getPassword());
				m.addAttribute("goods", goods);
				
				return "welcome";
			} else {
				/*　管理者でなかった場合　*/
				return "redirect:/ecsite/";
			}
		}
		
		/* ログイン失敗 */
		return "redirect:/ecsite/";
	}

	@RequestMapping("/goodsMst")
	public String goodsMst(LoginForm loginForm, Model m) {
		m.addAttribute("userName", loginForm.getUserName());
		m.addAttribute("password", loginForm.getPassword());
		
		return "goodsmst";
	}
	
	@RequestMapping("/addGoods")
	public String addGoods(GoodsForm goodsForm, LoginForm loginForm, Model m) {
		m.addAttribute("userName", loginForm.getUserName());
		m.addAttribute("password", loginForm.getPassword());
		
		Goods goods = new Goods();
		goods.setGoodsName(goodsForm.getGoodsName());
		goods.setPrice(goodsForm.getPrice());
		goodsMapper.insert(goods);
		
		return "forward:/ecsite/admin/welcome";
	}
	
	@ResponseBody
	@PostMapping("/api/deleteGoods")
	public String deleteApi(@RequestBody GoodsForm goodsForm, Model m) {
		try {
			goodsMapper.deleteById(goodsForm.getId());
		} catch (IllegalArgumentException e) {
			return "-1";
		}
		return "1";
	}

}
