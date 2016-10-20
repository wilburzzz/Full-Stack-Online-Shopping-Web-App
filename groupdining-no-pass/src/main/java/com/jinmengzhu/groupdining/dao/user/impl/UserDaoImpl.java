package com.jinmengzhu.groupdining.dao.user.impl;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jinmengzhu.groupdining.dao.impl.BasicDaoImpl;
import com.jinmengzhu.groupdining.dao.user.UserDao;
import com.jinmengzhu.groupdining.domain.user.User;

@Repository
public class UserDaoImpl extends BasicDaoImpl<User> implements UserDao{

	
	@Autowired
	public UserDaoImpl(SessionFactory sessionFactory) {
		setSessionFactory(sessionFactory);
	}
}
