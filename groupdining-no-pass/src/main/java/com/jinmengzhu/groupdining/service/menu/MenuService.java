package com.jinmengzhu.groupdining.service.menu;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.jinmengzhu.groupdining.domain.BasicResponse;
import com.jinmengzhu.groupdining.domain.PagerModel;
import com.jinmengzhu.groupdining.domain.menu.Menu;

public interface MenuService {

	PagerModel<Menu> getPage(HttpServletRequest request);

	BasicResponse add(HttpServletRequest request);

	BasicResponse modify(HttpServletRequest request);

	BasicResponse del(HttpServletRequest request);

	BasicResponse addDishToMenu(HttpServletRequest request);

	Map<String, Object> get(HttpServletRequest request);

	List<Menu> getListByCart(HttpServletRequest request);

}
