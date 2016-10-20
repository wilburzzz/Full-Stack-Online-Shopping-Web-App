package com.jinmengzhu.groupdining.controller.admin;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jinmengzhu.groupdining.constant.SessionConstant;
import com.jinmengzhu.groupdining.domain.BasicResponse;
import com.jinmengzhu.groupdining.domain.admin.Admin;
import com.jinmengzhu.groupdining.domain.user.User;
import com.jinmengzhu.groupdining.service.admin.AdminService;
import com.jinmengzhu.groupdining.service.user.UserService;

@Controller
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	private AdminService adminService;
	@Autowired
	private UserService userService;
	
	/**
	  * 功能描述：用户登陆
	  * 使用约束：
	  * @param users
	  * @return
	  */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "doLogin") 
    public String doLogin(Model model,String userName,String password,String userType,HttpServletRequest request) { 
		BasicResponse b = new BasicResponse();
		if(userType.equals("USER")){
			b = userService.doLogin(userName,password);
		}else{
			b = adminService.doLogin(userName,password);
		}
		if(b.isSuccess()){
			if(userType.equals("USER")){
				User user = (User) b.getObject();
				//request.getSession().setAttribute(SessionConstant.KEY_CURRENT_USERID,user.getId());
				request.getSession().setAttribute(SessionConstant.KEY_CURRENT_USER,user);
				return "/users";
			}else{
				Admin admin = (Admin) b.getObject();
				request.getSession().setAttribute(SessionConstant.KEY_CURRENT_ADMIN,admin);
				//request.getSession().setAttribute(SessionConstant.KEY_CURRENT_ADMINID,admin.getId());
				return "/admins";
			}
		}
		model.addAttribute("message", b.getMessage());
		return "/sign-in";
    }
	
	/**
	  * 功能描述：退出
	  * 使用约束：
	  * @param model
	  * @param request
	  * @return
	  * @throws Exception
	  */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/logOut")
	@ResponseBody
	public BasicResponse logOut(Model model, HttpServletRequest request)
			throws Exception {
		BasicResponse b = new BasicResponse();
		request.getSession().invalidate();
		return b;
	}
}
