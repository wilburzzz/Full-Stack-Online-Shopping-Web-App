package com.jinmengzhu.groupdining.service.user.impl;

import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.jinmengzhu.groupdining.dao.user.UserDao;
import com.jinmengzhu.groupdining.domain.BasicResponse;
import com.jinmengzhu.groupdining.domain.user.User;
import com.jinmengzhu.groupdining.exception.ParamNullException;
import com.jinmengzhu.groupdining.service.user.UserService;
import com.jinmengzhu.groupdining.util.SecurityUtil;

@Transactional
@Service
public class UserServiceImpl implements UserService{
	@Autowired
	private UserDao userDao;

	@SuppressWarnings("rawtypes")
	@Override
	public BasicResponse createUser(HttpServletRequest request) {
		BasicResponse b = new BasicResponse();
		String name = (String)request.getParameter("name");
		String password = (String)request.getParameter("password");
		String email = (String)request.getParameter("email");
		User u = new User();
		u.setName(name);
		u.setPassword(password);
		u.setEmail(email);
		u.setCreateTime(new Timestamp(new Date().getTime()));
		List<User> users = userDao.getEntityListByProperty("name", name);
		if (users != null && users.size() > 1) {
			b.setMessage("亲，数据库已有两个该用户咯，请联系管理员处理。");
			b.setSuccess(false);
			return b;
		}
		try {
			// 使用md5加盐值加密
			u.setPassword(SecurityUtil.md5(u.getName(), u.getPassword()));
		} catch (NoSuchAlgorithmException e) {
			throw new ParamNullException("密码加密失败:" + e.getMessage());
		}
		userDao.save(u);
		return b;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public BasicResponse doLogin(String userName, String password) {
		BasicResponse basicResponse = new BasicResponse();
		// 判空
		if (StringUtils.isEmpty(password) || StringUtils.isEmpty(userName)) {
			throw new ParamNullException("userName or password");
		}
		userName = userName.trim();
		List<User> users = userDao.getEntityListByProperty("name", userName);
		if (users == null || users.size() == 0) {
			basicResponse.setMessage("用户不存在!");
			basicResponse.setSuccess(false);
			return basicResponse;
		}
		if (users != null && users.size() > 1) {
			basicResponse.setMessage("亲，数据库已有两个该用户咯，请联系管理员处理。");
			basicResponse.setSuccess(false);
			return basicResponse;
		}
		User user = users.get(0);
		try {
			if (!SecurityUtil.md5(userName, password).equals(user.getPassword())) {
				basicResponse.setMessage("用户名或者密码不正确");
				basicResponse.setSuccess(false);
				return basicResponse;
			}
		} catch (NoSuchAlgorithmException e) {
			throw new ParamNullException("密码加密失败:" + e.getMessage());
		}
		//user.setPassword(null);
		basicResponse.setObject(user);
		basicResponse.setSuccess(true);
		return basicResponse;
	}

}
