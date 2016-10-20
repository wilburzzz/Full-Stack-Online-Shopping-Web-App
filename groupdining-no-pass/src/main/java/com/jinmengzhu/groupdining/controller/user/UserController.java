package com.jinmengzhu.groupdining.controller.user;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jinmengzhu.groupdining.domain.BasicResponse;

@Controller
@RequestMapping("/user")
public class UserController {

	
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
