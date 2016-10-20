package com.jinmengzhu.groupdining.dao.menu;

import java.util.List;

import com.jinmengzhu.groupdining.dao.BasicDao;
import com.jinmengzhu.groupdining.domain.PagerModel;
import com.jinmengzhu.groupdining.domain.menu.Dish;
import com.jinmengzhu.groupdining.domain.menu.Menu;

public interface DishDao extends BasicDao<Dish>{

	@SuppressWarnings("rawtypes")
	PagerModel<Dish> getPage(Dish m, PagerModel pageInfo);

	List<Dish> getDishListByMenuIds(String dishIds);

}
