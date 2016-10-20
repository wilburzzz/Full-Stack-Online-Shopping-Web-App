package com.jinmengzhu.groupdining.service.user;

import javax.servlet.http.HttpServletRequest;

import com.jinmengzhu.groupdining.domain.BasicResponse;

public interface UserService {

	@SuppressWarnings("rawtypes")
	public BasicResponse createUser(HttpServletRequest request);

	@SuppressWarnings("rawtypes")
	public BasicResponse doLogin(String userName, String password);

}
