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
import com.jinmengzhu.groupdining.domain.menu.Menu;
import com.jinmengzhu.groupdining.domain.user.User;
import com.jinmengzhu.groupdining.service.menu.MenuService;

@Controller
@RequestMapping("/menu")
public class MenuController {
	
	@Autowired
	private MenuService menuService;

	/**
	 * 功能描述：加载菜单列表
	 * 使用方法：
	 * 使用约束：
	 * 逻辑：
	 * @param mustParaDto
	 * @param menu
	 * @param pageSize
	 * @param pageIndex
	 * @return
	 */
	@RequestMapping(value = "/getPage")
	@ResponseBody
	public PagerModel<Menu> getMenuPage(Model model, HttpServletRequest request){
		return menuService.getPage(request);
	}
	

	@RequestMapping(value = "/toMenuView")
	public String addView(Model model, HttpServletRequest request)
			throws Exception {
		return "/admins-menu";
	}
	@RequestMapping(value = "/toMenuEdit")
	public String toMenuEdit(Model model, HttpServletRequest request)
			throws Exception {
		return "/menuEdit";
	}
	/**
	 * 功能描述：修改
	 * 使用方法：
	 * 使用约束：
	 * 逻辑：
	 * @param model
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/toMenuEdit2")
	public String toMenuEdit2(Model model, HttpServletRequest request)
			throws Exception {
		String menuId = (String)request.getParameter("menuId");
		model.addAttribute("menuId", menuId);
		return "/menuEdit2";
	}
	@RequestMapping(value = "/toMenuDetail")
	public String toMenuDetail(Model model, HttpServletRequest request)
			throws Exception {
		String menuId = (String)request.getParameter("menuId");
		model.addAttribute("menuId", menuId);
		return "/menuDetail";
	}
	
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/add")
	@ResponseBody
	public BasicResponse add(Model model, HttpServletRequest request)
			throws Exception {
		BasicResponse b = menuService.add(request);
		return b;
	}
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/addDishToMenu")
	@ResponseBody
	public BasicResponse addDishToMenu(Model model, HttpServletRequest request)
			throws Exception {
		BasicResponse b = menuService.addDishToMenu(request);
		return b;
	}
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/modify")
	@ResponseBody
	public BasicResponse modify(Model model, HttpServletRequest request)
			throws Exception {
		BasicResponse b = menuService.modify(request);
		return b;
	}
	@RequestMapping(value = "/get")
	@ResponseBody
	public Map<String,Object> get(Model model, HttpServletRequest request){
		return menuService.get(request);
	}
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/del")
	@ResponseBody
	public BasicResponse del(Model model, HttpServletRequest request)
			throws Exception {
		BasicResponse b = menuService.del(request);
		return b;
	}
	
	@RequestMapping(value = "/getListByCart")
	@ResponseBody
	public List<Menu> getListByCartId(Model model, HttpServletRequest request){
		return menuService.getListByCart(request);
	}
}
