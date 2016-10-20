package com.jinmengzhu.groupdining.controller.order;

import javax.servlet.http.HttpServletRequest;
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

import com.jinmengzhu.groupdining.domain.BasicResponse;
import com.jinmengzhu.groupdining.domain.PagerModel;
import com.jinmengzhu.groupdining.domain.order.Order;
import com.jinmengzhu.groupdining.service.order.OrderService;

@Controller
@RequestMapping("/order")
public class OrderController {

	@Autowired
	private OrderService orderService;
	
	/**
	 * 功能描述：创建订单
	 * 使用方法：
	 * 使用约束：
	 * 逻辑：
	 * @param order
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/saveOrder")
	@ResponseBody
	public BasicResponse add(Model model, HttpServletRequest request)
			throws Exception {
		BasicResponse b = orderService.add(request);
		return b;
	}
	
	/**
	 * 功能描述：获取订单列表
	 * 使用方法：
	 * 使用约束：
	 * 逻辑：
	 * @param order
	 * @param pageSize
	 * @param pageIndex
	 * @return
	 */
	@POST
	@Path("/getOrderPage")
	@Produces(MediaType.APPLICATION_JSON)
	public PagerModel<Order> getOrderPage(
			@Form Order order,
			@FormParam(value = "pageSize") int pageSize,
			@FormParam(value = "pageIndex") int pageIndex
			){
		return null;
	}
}
