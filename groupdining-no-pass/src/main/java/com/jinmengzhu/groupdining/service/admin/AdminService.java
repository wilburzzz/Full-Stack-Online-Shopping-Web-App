package com.jinmengzhu.groupdining.service.admin;

import javax.servlet.http.HttpServletRequest;

import com.jinmengzhu.groupdining.domain.BasicResponse;

public interface AdminService {

	@SuppressWarnings("rawtypes")
	public BasicResponse doLogin(String userName, String password);

	public BasicResponse createAdmin(HttpServletRequest request);

}
