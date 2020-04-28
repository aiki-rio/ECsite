package jp.co.internous.ecsite.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;

import jp.co.internous.ecsite.model.LoginSession;
import jp.co.internous.ecsite.model.domain.Goods;
import jp.co.internous.ecsite.model.domain.Purchase;
import jp.co.internous.ecsite.model.domain.User;
import jp.co.internous.ecsite.model.domain.dto.HistoryDto;
import jp.co.internous.ecsite.model.domain.dto.LoginDto;
import jp.co.internous.ecsite.model.form.CartForm;
import jp.co.internous.ecsite.model.form.HistoryForm;
import jp.co.internous.ecsite.model.form.LoginForm;
import jp.co.internous.ecsite.model.mapper.GoodsMapper;
import jp.co.internous.ecsite.model.mapper.PurchaseMapper;
import jp.co.internous.ecsite.model.mapper.UserMapper;

@Controller
@RequestMapping("/ecsite")
public class IndexController {
	
	@Autowired
	UserMapper userMapper;
	
	@Autowired
	GoodsMapper goodsMapper;
	
	@Autowired
	PurchaseMapper purchaseMapper;

	@Autowired
	protected LoginSession session;
	
	private Gson gson = new Gson();
	
	@RequestMapping("/")
	public String index(Model m) {
		List<Goods> goods = goodsMapper.findAll();
		m.addAttribute("goods", goods);
		
		return "index";
	}
	
	@ResponseBody
	@PostMapping("/api/login")
	public String loginApi(@RequestBody LoginForm form) {
		List<User> users = userMapper.findByUserNameAndPassword(form.getUserName(), form.getPassword());
		LoginDto dto = new LoginDto(0, null, null, "ゲスト");
		if (users.size() > 0) {
			dto = new LoginDto(users.get(0));
		}
		return gson.toJson(dto);
	}
	
	@ResponseBody
	@PostMapping("/api/purchase")
	public String purchaseApi(@RequestBody CartForm f) {
		Purchase p = new Purchase();

		f.getCartList().forEach((c) -> {
			long total = c.getPrice() * c.getCount();
			p.setUserId(f.getUserId());
			p.setGoodsId(c.getId());
			p.setGoodsName(c.getGoodsName());
			p.setItemCount(c.getCount());
			p.setTotal(total);
			//purchaseMapper.javaで定義したinsertメソッドを p を引数として実行
			purchaseMapper.insert(p);
		});
		
		return String.valueOf(f.getCartList().size());
	}
	
	@ResponseBody
	@PostMapping("api/history")
	public String historyApi(@RequestBody HistoryForm form) {
		String userId = form.getUserId();
		long historySystem = form.getHistorySystem();
		List<HistoryDto> history;
		//historySystem=1 全ての購入履歴
		//historySystem=2 直近の購入履歴
		if (historySystem == 1) {
			history = purchaseMapper.findHistory(userId);
		} else {
			history = purchaseMapper.findHistoryLatest(userId);
		}
		
		return gson.toJson(history);
	}

}
