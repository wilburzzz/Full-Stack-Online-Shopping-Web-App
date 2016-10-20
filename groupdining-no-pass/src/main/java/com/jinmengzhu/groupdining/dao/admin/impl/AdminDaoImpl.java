package com.jinmengzhu.groupdining.dao.admin.impl;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jinmengzhu.groupdining.dao.admin.AdminDao;
import com.jinmengzhu.groupdining.dao.impl.BasicDaoImpl;
import com.jinmengzhu.groupdining.domain.admin.Admin;

@Repository
public class AdminDaoImpl extends BasicDaoImpl<Admin> implements AdminDao{

	@Autowired
	public AdminDaoImpl(SessionFactory sessionFactory) {
		setSessionFactory(sessionFactory);
	}
}
