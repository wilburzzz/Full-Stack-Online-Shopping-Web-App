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
import com.jinmengzhu.groupdining.service.menu.DishService;
import com.jinmengzhu.groupdining.util.PageUtil;

@Transactional
@Service
public class DishServiceImpl implements DishService{
	
	@Autowired
	private DishDao dishDao;
	@Autowired
	private MenuDao menuDao;
	@Autowired
	private CartDao cartDao;

	@SuppressWarnings("rawtypes")
	@Override
	public PagerModel<Dish> getPage(HttpServletRequest request) {
		PagerModel pageInfo = PageUtil.getPage(request);
		Dish d = new Dish();
		
		return dishDao.getPage(d,pageInfo);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public BasicResponse add(HttpServletRequest request) {
		BasicResponse b = new BasicResponse();
		String name = (String)request.getParameter("name");
		String price = (String)request.getParameter("price");
		String pictrue = (String)request.getParameter("pictrue");
		String description = (String)request.getParameter("description");
		
		Dish d = new Dish();
		d.setName(name);
		d.setPrice(price == null ? 0 :Float.parseFloat(price));
		d.setPictrue(pictrue);
		d.setDescription(description);
		d.setCreateTime(new Timestamp(new Date().getTime()));
		d.setLastUpdateTime(new Timestamp(new Date().getTime()));
		List<Dish> admins = dishDao.getEntityListByProperty("name", name);
		if (admins != null && admins.size() > 1) {
			b.setMessage("亲，数据库已有两个该用户咯，请联系管理员处理。");
			b.setSuccess(false);
			return b;
		}
		
		dishDao.save(d);
		return b;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public BasicResponse del(HttpServletRequest request) {
		BasicResponse b = new BasicResponse();
		String id = (String)request.getParameter("id");
		dishDao.delete(id);
		return b;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public BasicResponse modify(HttpServletRequest request) {
		BasicResponse b = new BasicResponse();
		String id = (String)request.getParameter("id");
		String name = (String)request.getParameter("name");
		String price = (String)request.getParameter("price");
		String pictrue = (String)request.getParameter("pictrue");
		String description = (String)request.getParameter("description");
		
		Dish d = this.dishDao.getEntityByProperty("id", id);
		d.setId(id);
		d.setName(name);
		d.setPrice(price == null ? 0 :Float.parseFloat(price));
		d.setPictrue(pictrue);
		d.setDescription(description);
		d.setLastUpdateTime(new Timestamp(new Date().getTime()));
		dishDao.saveOrUpdate(d);
		return b;
	}

	@Override
	public List<Dish> getListByCartId(HttpServletRequest request) {
		User user = (User)request.getAttribute(SessionConstant.KEY_CURRENT_USER);
		if(user == null ){
			return new ArrayList<Dish>();
		}
		Cart cart = this.cartDao.getEntityByProperty("userId", user.getId());
		String meunIds = cart.getMenuId();
		if(StringUtils.isEmpty(meunIds)){
			return new ArrayList<Dish>();
		}
		List<Menu> menus = this.menuDao.getMenuListByMenuIds(cart.getMenuId());
		if(menus == null || menus.size() == 0){
			return new ArrayList<Dish>();
		}
		Map<String,Dish> map = new HashMap<String, Dish>();
		for (Menu menu : menus) {
			List<Dish>  dishes = this.dishDao.getDishListByMenuIds(menu.getId());
			for (Dish dish : dishes) {
				if(map.containsKey(dish.getId())){
					Dish d = map.get(dish.getId());
					d.setAmount( d.getAmount() + 1);
				}else map.put(dish.getId(), dish);
			}
		}
		List<Dish> destDish = new ArrayList<Dish>();
		for (String key: map.keySet()) {
			destDish.add(map.get(key));
		}
		return destDish;
	}

	@Override
	public Dish get(HttpServletRequest request) {
		String id = (String)request.getParameter("id");
		Dish dish  = this.dishDao.getEntityByProperty("id", id);
		return dish;
	}

}
