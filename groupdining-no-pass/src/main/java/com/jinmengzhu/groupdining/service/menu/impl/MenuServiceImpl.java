package com.jinmengzhu.groupdining.service.menu.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.jinmengzhu.groupdining.constant.SessionConstant;
import com.jinmengzhu.groupdining.dao.cart.CartDao;
import com.jinmengzhu.groupdining.dao.menu.DishDao;
import com.jinmengzhu.groupdining.dao.menu.MenuDao;
import com.jinmengzhu.groupdining.domain.BasicResponse;
import com.jinmengzhu.groupdining.domain.PagerModel;
import com.jinmengzhu.groupdining.domain.cart.Cart;
import com.jinmengzhu.groupdining.domain.menu.Dish;
import com.jinmengzhu.groupdining.domain.menu.Menu;
import com.jinmengzhu.groupdining.domain.user.User;
import com.jinmengzhu.groupdining.service.menu.MenuService;
import com.jinmengzhu.groupdining.util.CurrencyUtil;
import com.jinmengzhu.groupdining.util.PageUtil;

@Transactional
@Service
public class MenuServiceImpl implements MenuService{
	
	@Autowired
	private MenuDao menuDao;
	@Autowired
	private DishDao dishDao;
	@Autowired
	private CartDao cartDao;

	@SuppressWarnings("rawtypes")
	@Override
	public PagerModel<Menu> getPage(HttpServletRequest request) {
		
		PagerModel pageInfo = PageUtil.getPage(request);
		Menu m = new Menu();
		
		return menuDao.getPage(m,pageInfo);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public BasicResponse add(HttpServletRequest request) {
		BasicResponse b = new BasicResponse();
		String name = (String)request.getParameter("name");
		String style = (String)request.getParameter("style");
		String pictrue = (String)request.getParameter("pictrue");
		String price = (String)request.getParameter("price");
		String description = (String)request.getParameter("description");
		String dishIds = (String)request.getParameter("dishIds");
		String discount = (String)request.getParameter("discount");
		
		Menu d = new Menu();
		d.setName(name);
		d.setPrice(price == null ? 0 :Float.parseFloat(price));
		d.setDiscount(discount == null ? 0 :CurrencyUtil.formatDouble(discount));
		d.setPictrue(pictrue);
		d.setStyle(style);
		d.setDishIds(dishIds);
		d.setDescription(description);
		d.setCreateTime(new Timestamp(new Date().getTime()));
		d.setLastUpdateTime(new Timestamp(new Date().getTime()));
		List<Menu> menus = menuDao.getEntityListByProperty("name", name);
		if (menus != null && menus.size() > 1) {
			b.setMessage("亲，数据库已有两个该用户咯，请联系管理员处理。");
			b.setSuccess(false);
			return b;
		}
		
		menuDao.save(d);
		return b;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public BasicResponse modify(HttpServletRequest request) {
		BasicResponse b = new BasicResponse();
		Menu d = new Menu();
		menuDao.saveOrUpdate(d);
		return b;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public BasicResponse del(HttpServletRequest request) {
		BasicResponse b = new BasicResponse();
		String id = (String)request.getParameter("id");
		menuDao.delete(id);
		return b;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public BasicResponse addDishToMenu(HttpServletRequest request) {
		BasicResponse b = new BasicResponse();
		String dishId = (String)request.getParameter("dishId");
		String menuId = (String)request.getParameter("menuId");
		Menu menu = this.menuDao.getEntityByProperty("id", menuId);
		String dishIds = menu.getDishIds();
		if(StringUtils.isEmpty(dishIds)){
			menu.setDishIds(dishId);
		}else{
			String[] arr = dishIds.split(",");
			for (String s : arr) {
				if(s.equals(dishId)){
					b.setMessage("The Dish has been added!");
					b.setSuccess(false);
					return b;
				}
			}
			menu.setDishIds(menu.getDishIds()+","+dishId);
		}
		menuDao.saveOrUpdate(menu);
		return b;
	}

	@Override
	public Map<String,Object> get(HttpServletRequest request) {
		Map<String,Object> map = new HashMap<String, Object>();
		String menuId = (String)request.getParameter("menuId");
		Menu menu = this.menuDao.getEntityByProperty("id", menuId);
		map.put("menuData", menu);
		List<Dish> dishes = new ArrayList<Dish>();
		dishes = this.dishDao.getDishListByMenuIds(menu.getDishIds());
		map.put("dishData", dishes);
		return map;
	}

	@Override
	public List<Menu> getListByCart(HttpServletRequest request) {

		//User user = (User)request.getAttribute(SessionConstant.KEY_CURRENT_USER);
		String userId = (String)request.getParameter("userId");
		if(userId == null ){
			return new ArrayList<Menu>();
		}
		Cart cart = this.cartDao.getEntityByProperty("userId", userId);
		if(cart == null){
			return new ArrayList<Menu>();
		}
		String meunIds = cart.getMenuId();
		if(StringUtils.isEmpty(meunIds)){
			return new ArrayList<Menu>();
		}
		List<Menu> menus = this.menuDao.getMenuListByMenuIds(cart.getMenuId());
		if(menus == null || menus.size() == 0){
			return new ArrayList<Menu>();
		}
		return menus;
	}

}
