package com.jinmengzhu.groupdining.controller.cart;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jinmengzhu.groupdining.constant.SessionConstant;
import com.jinmengzhu.groupdining.domain.BasicResponse;
import com.jinmengzhu.groupdining.domain.user.User;
import com.jinmengzhu.groupdining.service.cart.CartService;

@Controller
@RequestMapping("/cart")
public class CartController {

	@Autowired
	private CartService cartService;
	
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/addMenuToCart")
	@ResponseBody
	public BasicResponse addDishToCart(Model model, HttpServletRequest request)
			throws Exception {
		BasicResponse b = cartService.addMenuToCart(request);
		return b;
	}
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/delMenu")
	@ResponseBody
	public BasicResponse delMenu(Model model, HttpServletRequest request)
			throws Exception {
		BasicResponse b = cartService.delMenu(request);
		return b;
	}
	
	@RequestMapping(value = "/toCartList")
	public String toCartList(Model model, HttpServletRequest request)
			throws Exception {
		User user = (User)request.getSession().getAttribute(SessionConstant.KEY_CURRENT_USER);
		if(user != null){
			model.addAttribute("userId", user.getId());
		}
		return "/cart";
	}
}
