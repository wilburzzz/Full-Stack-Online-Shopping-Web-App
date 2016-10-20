package com.jinmengzhu.groupdining.service.admin.impl;

import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.jinmengzhu.groupdining.dao.admin.AdminDao;
import com.jinmengzhu.groupdining.domain.BasicResponse;
import com.jinmengzhu.groupdining.domain.admin.Admin;
import com.jinmengzhu.groupdining.exception.ParamNullException;
import com.jinmengzhu.groupdining.service.admin.AdminService;
import com.jinmengzhu.groupdining.util.SecurityUtil;

@Transactional
@Service
public class AdminServiceImpl implements AdminService{
	@Autowired
	private AdminDao adminDao;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public BasicResponse doLogin(String userName, String password) {
		BasicResponse basicResponse = new BasicResponse();
		// 判空
		if (StringUtils.isEmpty(password) || StringUtils.isEmpty(userName)) {
			throw new ParamNullException("userName or password");
		}
		userName = userName.trim();
		List<Admin> admins = adminDao.getEntityListByProperty("name", userName);
		if (admins == null || admins.size() == 0) {
			basicResponse.setMessage("用户不存在!");
			basicResponse.setSuccess(false);
			return basicResponse;
		}
		if (admins != null && admins.size() > 1) {
			basicResponse.setMessage("亲，数据库已有两个该用户咯，请联系管理员处理。");
			basicResponse.setSuccess(false);
			return basicResponse;
		}
		Admin admin = admins.get(0);
		try {
			if (!SecurityUtil.md5(userName, password).equals(admin.getPassword())) {
				basicResponse.setMessage("用户名或者密码不正确");
				basicResponse.setSuccess(false);
				return basicResponse;
			}
		} catch (NoSuchAlgorithmException e) {
			throw new ParamNullException("密码加密失败:" + e.getMessage());
		}
		//admin.setPassword(null);
		basicResponse.setObject(admin);
		basicResponse.setSuccess(true);
		return basicResponse;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public BasicResponse createAdmin(HttpServletRequest request) {
		BasicResponse b = new BasicResponse();
		String name = (String)request.getParameter("name");
		String password = (String)request.getParameter("password");
		String email = (String)request.getParameter("email");
		String style = (String)request.getParameter("style");
		Admin a = new Admin();
		a.setName(name);
		a.setPassword(password);
		a.setEmail(email);
		a.setStyle(style);
		a.setCreateTime(new Timestamp(new Date().getTime()));
		a.setLastUpdateTime(new Timestamp(new Date().getTime()));
		List<Admin> admins = adminDao.getEntityListByProperty("name", name);
		if (admins != null && admins.size() > 1) {
			b.setMessage("亲，数据库已有两个该用户咯，请联系管理员处理。");
			b.setSuccess(false);
			return b;
		}
		try {
			// 使用md5加盐值加密
			a.setPassword(SecurityUtil.md5(a.getName(), a.getPassword()));
		} catch (NoSuchAlgorithmException e) {
			throw new ParamNullException("密码加密失败:" + e.getMessage());
		}
		adminDao.save(a);
		return b;
	}
	
	

}
