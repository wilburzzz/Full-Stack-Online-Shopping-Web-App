package com.jinmengzhu.groupdining.dao.menu;

import java.util.List;

import com.jinmengzhu.groupdining.dao.BasicDao;
import com.jinmengzhu.groupdining.domain.PagerModel;
import com.jinmengzhu.groupdining.domain.menu.Menu;

public interface MenuDao extends BasicDao<Menu>{

	PagerModel<Menu> getPage(Menu m, PagerModel pageInfo);

	List<Menu> getMenuListByMenuIds(String menuId);

}
