package com.jinmengzhu.groupdining.service.order;

import javax.servlet.http.HttpServletRequest;

import com.jinmengzhu.groupdining.domain.BasicResponse;

public interface OrderService {

	BasicResponse add(HttpServletRequest request);

}
