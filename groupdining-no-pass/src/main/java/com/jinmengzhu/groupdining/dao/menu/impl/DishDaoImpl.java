package com.jinmengzhu.groupdining.dao.menu.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.jinmengzhu.groupdining.dao.impl.BasicDaoImpl;
import com.jinmengzhu.groupdining.dao.menu.DishDao;
import com.jinmengzhu.groupdining.domain.PagerModel;
import com.jinmengzhu.groupdining.domain.menu.Dish;
import com.jinmengzhu.groupdining.domain.menu.Menu;
import com.jinmengzhu.groupdining.util.StringUtil;

@Repository
public class DishDaoImpl extends BasicDaoImpl<Dish> implements DishDao{

	@Autowired
	public DishDaoImpl(SessionFactory sessionFactory) {
		setSessionFactory(sessionFactory);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public PagerModel<Dish> getPage(Dish m, PagerModel pageInfo) {
		
		StringBuffer hql = new StringBuffer();
		hql.append("select a from " + Dish.class.getName() + " a where 1=1");
		Map<String, Object> params = new HashMap<String, Object>();
		
		String name = m.getName();
		if (!StringUtils.isEmpty(name)) {
			hql.append(" and name like :name");
			params.put("name", "%"+name.trim()+"%");
		}
		
		// 得到排序方式
		String sortType = pageInfo.getSort();
		String datasetSort = pageInfo.getOrder();
		if (StringUtil.isNull(sortType)) {
			sortType = "a.createTime";
		}
		if (StringUtil.isNull(datasetSort)) {
			datasetSort = "desc";
		}
		
		hql.append(" order by " + sortType + " " + datasetSort);
		PagerModel<Dish> retnPage = this.queryObjectPageByHql(hql.toString(), pageInfo, params);
		return retnPage;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Dish> getDishListByMenuIds(String dishIds) {
		if(StringUtils.isEmpty(dishIds)){
			return new ArrayList<Dish>();
		}
		dishIds = dishIds.replace(",", "','");
		dishIds = "'"+dishIds+"'";
		String hql = "from "+Dish.class.getName() +" where id in("+dishIds+")";
		List<Dish> dishes = this.queryObjectsByHql(hql);
		return dishes;
	}
}
