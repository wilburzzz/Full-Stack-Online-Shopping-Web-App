package com.jinmengzhu.groupdining.service.cart.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.jinmengzhu.groupdining.constant.SessionConstant;
import com.jinmengzhu.groupdining.dao.cart.CartDao;
import com.jinmengzhu.groupdining.dao.menu.MenuDao;
import com.jinmengzhu.groupdining.domain.BasicResponse;
import com.jinmengzhu.groupdining.domain.cart.Cart;
import com.jinmengzhu.groupdining.domain.user.User;
import com.jinmengzhu.groupdining.service.cart.CartService;

/**
 * 职责：
 * 使用方法：
 */
@Transactional
@Service
public class CartServiceImpl implements CartService {
	@Autowired
	private CartDao cartDao;
	@Autowired
	private MenuDao menuDao;

	@SuppressWarnings("rawtypes")
	@Override
	public BasicResponse addMenuToCart(HttpServletRequest request) {
		BasicResponse b = new BasicResponse();
		String menuId = (String)request.getParameter("menuId");
		User user = (User)request.getSession().getAttribute(SessionConstant.KEY_CURRENT_USER);
		
		Cart cart = this.cartDao.getEntityByProperty("userId", user.getId());
		if(cart == null || "".equals(cart.getId())){
	        cart = new Cart();
			cart.setMenuId(menuId);
			cart.setUserId(user.getId());
			cart.setCreateTime(new Timestamp(new Date().getTime()));
			cart.setLastUpdateTime(new Timestamp(new Date().getTime()));
			this.cartDao.save(cart);
			return b;
		}
		
		String menuIds = cart.getMenuId();
		if(StringUtils.isEmpty(menuIds)){
			cart.setMenuId(menuIds);
		}else{
			String[] arr = menuIds.split(",");
			for (String s : arr) {
				if(s.equals(menuId)){
					b.setMessage("The Menu has been added!");
					b.setSuccess(false);
					return b;
				}
			}
			cart.setMenuId(cart.getMenuId()+","+menuId);
		}
		cart.setLastUpdateTime(new Timestamp(new Date().getTime()));
		cartDao.saveOrUpdate(cart);
		return b;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public BasicResponse delMenu(HttpServletRequest request) {
		BasicResponse b = new BasicResponse();
		String menuId = (String)request.getParameter("menuId");
		User user = (User)request.getSession().getAttribute(SessionConstant.KEY_CURRENT_USER);
		//this.menuDao.getMenuListByMenuIds();
		Cart cart = this.cartDao.getEntityByProperties(new String[]{"userId"}, new Object[]{user.getId()});
		if(cart != null){
			String menuIds = cart.getMenuId();
			String[] arr = menuIds.split(",");
			String menuIdStr = "";
			for (String s : arr) {
				if(!s.equals(menuId)){
					menuIdStr += s + ","; 
				}
			}
			if(!StringUtils.isEmpty(menuIdStr)){
				menuIdStr = menuIdStr.substring(0, menuIdStr.length()-1);
			}
			cart.setMenuId(menuIdStr);
			this.cartDao.saveOrUpdate(cart);
		}
		return b;
	}

}
