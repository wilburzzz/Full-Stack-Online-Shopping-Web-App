package com.jinmengzhu.groupdining.service.cart;

import javax.servlet.http.HttpServletRequest;

import com.jinmengzhu.groupdining.domain.BasicResponse;

/**
 * 职责：购物车
 * 使用方法：
 */
public interface CartService {

	BasicResponse addMenuToCart(HttpServletRequest request);

	BasicResponse delMenu(HttpServletRequest request);

}
