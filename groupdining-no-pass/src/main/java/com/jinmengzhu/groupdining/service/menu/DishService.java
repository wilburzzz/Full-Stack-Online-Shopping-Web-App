package com.jinmengzhu.groupdining.service.menu;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.jinmengzhu.groupdining.domain.BasicResponse;
import com.jinmengzhu.groupdining.domain.PagerModel;
import com.jinmengzhu.groupdining.domain.menu.Dish;

public interface DishService {

	PagerModel<Dish> getPage(HttpServletRequest request);

	BasicResponse add(HttpServletRequest request);

	BasicResponse del(HttpServletRequest request);

	BasicResponse modify(HttpServletRequest request);

	List<Dish> getListByCartId(HttpServletRequest request);

	Dish get(HttpServletRequest request);

}
