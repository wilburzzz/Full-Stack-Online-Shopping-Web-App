package com.jinmengzhu.groupdining.controller.menu;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jinmengzhu.groupdining.constant.SessionConstant;
import com.jinmengzhu.groupdining.domain.BasicResponse;
import com.jinmengzhu.groupdining.domain.PagerModel;
import com.jinmengzhu.groupdining.domain.menu.Dish;
import com.jinmengzhu.groupdining.domain.user.User;
import com.jinmengzhu.groupdining.service.menu.DishService;


@Controller
@RequestMapping("/dish")
public class DishController {

	@Autowired
	private DishService dishService;
	
	@RequestMapping(value = "/getPage")
	@ResponseBody
	public PagerModel<Dish> getDishPage(Model model, HttpServletRequest request){
		return dishService.getPage(request);
	}
	@RequestMapping(value = "/getListByCartId")
	@ResponseBody
	public List<Dish> getListByCartId(Model model, HttpServletRequest request){
		User user = (User)request.getAttribute(SessionConstant.KEY_CURRENT_USER);
		return dishService.getListByCartId(request);
	}
	
	@RequestMapping(value = "/addView")
	public String addView(Model model, HttpServletRequest request)
			throws Exception {
		return "/user";
	}
	@RequestMapping(value = "/toDishEdit")
	public String toDishEdit(Model model, HttpServletRequest request)
			throws Exception {
		String id = (String)request.getParameter("id");
		model.addAttribute("dishId", id);
		return "/dishEdit";
	}
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/add")
	@ResponseBody
	public BasicResponse add(Model model, HttpServletRequest request)
			throws Exception {
		BasicResponse b = dishService.add(request);
		return b;
	}
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/modify")
	@ResponseBody
	public BasicResponse modify(Model model, HttpServletRequest request)
			throws Exception {
		BasicResponse b = dishService.modify(request);
		return b;
	}
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/del")
	@ResponseBody
	public BasicResponse del(Model model, HttpServletRequest request)
			throws Exception {
		BasicResponse b = dishService.del(request);
		return b;
	}
	@RequestMapping(value = "/get")
	@ResponseBody
	public Dish get(Model model, HttpServletRequest request){
		return dishService.get(request);
	}
}
