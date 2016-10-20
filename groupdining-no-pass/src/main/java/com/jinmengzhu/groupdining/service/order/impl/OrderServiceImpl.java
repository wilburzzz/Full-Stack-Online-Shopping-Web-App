package com.jinmengzhu.groupdining.service.order.impl;

import java.sql.Timestamp;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jinmengzhu.groupdining.dao.cart.CartDao;
import com.jinmengzhu.groupdining.dao.order.OrderDao;
import com.jinmengzhu.groupdining.domain.BasicResponse;
import com.jinmengzhu.groupdining.domain.cart.Cart;
import com.jinmengzhu.groupdining.domain.order.Order;
import com.jinmengzhu.groupdining.service.order.OrderService;

@Transactional
@Service
public class OrderServiceImpl implements OrderService{
	
	@Autowired
	private OrderDao orderDao;
	@Autowired
	private CartDao cartDao;

	@SuppressWarnings({ "rawtypes", "unused" })
	@Override
	public BasicResponse add(HttpServletRequest request) {
		BasicResponse b = new BasicResponse();
		String price = (String)request.getParameter("price");
		String userId = (String)request.getParameter("userId");
		String description = (String)request.getParameter("description");
		
		Order o = new Order();
		o.setPrice(price == null ? 0 :Float.parseFloat(price));
		o.setDescription("订单");
		o.setCreateTime(new Timestamp(new Date().getTime()));
		o.setLastUpdateTime(new Timestamp(new Date().getTime()));
		
		orderDao.save(o);
		//清空购物车
		//User user = (User)request.getAttribute(SessionConstant.KEY_CURRENT_USER);
		if(userId == null ){
			return b;
		}
		Cart cart = this.cartDao.getEntityByProperty("userId", userId);
		this.cartDao.delete(cart.getId());
		return b;
	}

}
