package com.jinmengzhu.groupdining.controller;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.jboss.resteasy.annotations.Form;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jinmengzhu.groupdining.constant.SessionConstant;
import com.jinmengzhu.groupdining.domain.BasicResponse;
import com.jinmengzhu.groupdining.domain.PagerModel;
import com.jinmengzhu.groupdining.domain.admin.Admin;
import com.jinmengzhu.groupdining.domain.menu.Dish;
import com.jinmengzhu.groupdining.domain.menu.Menu;
import com.jinmengzhu.groupdining.domain.user.User;
import com.jinmengzhu.groupdining.service.admin.AdminService;
import com.jinmengzhu.groupdining.service.cart.CartService;
import com.jinmengzhu.groupdining.service.menu.DishService;
import com.jinmengzhu.groupdining.service.menu.MenuService;
import com.jinmengzhu.groupdining.service.order.OrderService;
import com.jinmengzhu.groupdining.service.user.UserService;

@Controller
@RequestMapping("/")
public class IndexController {
	
	@Autowired
	private OrderService orderService;
	@Autowired
	private MenuService menuService;
	@Autowired
	private DishService dishService;
	@Autowired
	private CartService cartService;
	@Autowired
	private AdminService adminService;
	@Autowired
	private UserService userService;

	/**
	 * 功能描述：输入项目访问
	 * 使用方法：
	 * 使用约束：
	 * 逻辑：
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping
	public String index(Model model,HttpServletRequest request,HttpSession session){
		Admin admin = (Admin)request.getSession().getAttribute(SessionConstant.KEY_CURRENT_ADMIN);
		User user = (User)request.getSession().getAttribute(SessionConstant.KEY_CURRENT_USER);
		//String id = (String)session.getAttribute(SessionConstant.KEY_CURRENT_ADMINID);
		/*if(admin == null || StringUtils.isEmpty(admin.getId())){
			return "/sign-in";
		}*/
		if(admin != null && admin.getId() != null){
			//model.addAttribute(SessionConstant.KEY_CURRENT_ADMIN, admin);
			return "/admins";
		}else if(user != null && user.getId() != null){
			return "/users";
		}else
			return "/sign-in";
	}
	
	@RequestMapping(value="/index")
	public String indexView(Model model,HttpServletRequest request) throws Exception{
		String adminId = (String)request.getSession().getAttribute(SessionConstant.KEY_CURRENT_ADMINID);
		String userId = (String)request.getSession().getAttribute(SessionConstant.KEY_CURRENT_USERID);
		if(adminId == null || "".equals(adminId)){
			return "users";
		}else if(userId == null || "".equals(userId)){
			return "admins";
		}else
			return "/sign-in";
	}
	
	/**
	 * 功能描述：前往登录页
	 * 使用方法：
	 * 使用约束：
	 * 逻辑：
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/toRegist")
	public String doRegist(Model model,HttpServletRequest request){
		return "/userRegist";
	}
	
	/**
	 * 功能描述：前往用户注册页
	 * 使用方法：
	 * 使用约束：
	 * 逻辑：
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/toCustomerRegist")
	public String toCustomerRegist(Model model,HttpServletRequest request){
		String userType = (String)request.getParameter("userType");
		if(userType != null  && userType.equals("USER")){
			return "/sign-up-user";
		}else 
		return "/sign-up-admin";
	}
	/**
	 * 功能描述：前往系统管理员注册页 停用
	 * 使用方法：
	 * 使用约束：
	 * 逻辑：
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/toRestarantRegist")
	public String toRestarantRegist(Model model,HttpServletRequest request){
		return "/restaurantRegist";
	}
	
	/**
	 * 功能描述：注册用户
	 * 使用方法：
	 * 使用约束：
	 * 逻辑：
	 * @param pageSize
	 * @param pageIndex
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/registCustomer")
	@ResponseBody
	public BasicResponse registCustomer(HttpServletRequest request) {
		BasicResponse basicResp = new BasicResponse();
		try {
			basicResp = userService.createUser(request);
		} catch (Exception e) {
			e.printStackTrace();
			basicResp.setMessage("修改失败!");
			basicResp.setSuccess(false);
		}
		return basicResp;
	}
	/**
	  * 功能描述：创建管理员
	  * 使用约束：
	  * @param request
	  * @return
	  */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/registRestaurant")
	@ResponseBody
	public BasicResponse registRestaurant(HttpServletRequest request) {
		BasicResponse basicResp = new BasicResponse();
		try {
			basicResp = adminService.createAdmin(request);
		} catch (Exception e) {
			e.printStackTrace();
			basicResp.setMessage("创建失败!");
			basicResp.setSuccess(false);
		}
		return basicResp;
	}
	
	/**
	 * 功能描述：加载菜的列表
	 * 使用方法：
	 * 使用约束：
	 * 逻辑：
	 * @param mustParaDto
	 * @param dish
	 * @param pageSize
	 * @param pageIndex
	 * @return
	 */
	@POST
	@Path("/getDishPage")
	@Produces(MediaType.APPLICATION_JSON)
	public PagerModel<Dish> getDishPage(
			@FormParam(value = "mustParaDto" ) String mustParaDto,
			@Form Dish dish,
			@FormParam(value = "pageSize") int pageSize,
			@FormParam(value = "pageIndex") int pageIndex
			){
		return null;
	}
	
	
	
	
	/**
	 * 功能描述：创建菜品
	 * 使用方法：
	 * 使用约束：
	 * 逻辑：
	 * @param dish
	 * @return
	 */
	@POST
	@Path("/createDish")
	@ResponseBody
	public BasicResponse<Dish> createDish(@Form Dish dish){
		return null;
	}
	/**
	 * 功能描述：创建菜单
	 * 使用方法：
	 * 使用约束：
	 * 逻辑：
	 * @param menu
	 * @return
	 */
	@POST
	@Path("/createMenu")
	@ResponseBody
	public BasicResponse<Dish> createMenu(@Form Menu menu){
		return null;
	}
	/**
	 * 功能描述：删除菜单
	 * 使用方法：
	 * 使用约束：
	 * 逻辑：
	 * @param dish
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@POST
	@Path("/createDish")
	@ResponseBody
	public BasicResponse deleteMenu(@Form Menu menu){
		return null;
	}
	
	/**
	 * 功能描述：到菜品列表页
	 * 使用方法：
	 * 使用约束：
	 * 逻辑：
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/toDish")
	public String toDish(Model model,HttpServletRequest request){
		return "/dishList";
	}
}
