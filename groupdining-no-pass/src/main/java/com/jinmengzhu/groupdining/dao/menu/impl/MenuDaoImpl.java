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
import com.jinmengzhu.groupdining.dao.menu.MenuDao;
import com.jinmengzhu.groupdining.domain.PagerModel;
import com.jinmengzhu.groupdining.domain.menu.Dish;
import com.jinmengzhu.groupdining.domain.menu.Menu;
import com.jinmengzhu.groupdining.util.StringUtil;

@Repository
public class MenuDaoImpl extends BasicDaoImpl<Menu> implements MenuDao{

	
	@Autowired
	public MenuDaoImpl(SessionFactory sessionFactory) {
		setSessionFactory(sessionFactory);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public PagerModel<Menu> getPage(Menu m, PagerModel pageInfo) {
		
		StringBuffer hql = new StringBuffer();
		hql.append("select a from " + Menu.class.getName() + " a where 1=1");
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
		PagerModel<Menu> retnPage = this.queryObjectPageByHql(hql.toString(), pageInfo, params);
		return retnPage;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Menu> getMenuListByMenuIds(String menuId) {

		if(StringUtils.isEmpty(menuId)){
			return new ArrayList<Menu>();
		}
		menuId = menuId.replace(",", "','");
		menuId = "'"+menuId+"'";
		String hql = "from "+Menu.class.getName() +" where id in("+menuId+")";
		List<Menu> menus = this.queryObjectsByHql(hql);
		return menus;
	
	}
}
