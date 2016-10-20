package com.jinmengzhu.groupdining.dao.cart.impl;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jinmengzhu.groupdining.dao.cart.CartDao;
import com.jinmengzhu.groupdining.dao.impl.BasicDaoImpl;
import com.jinmengzhu.groupdining.domain.cart.Cart;

@Repository
public class CartDaoImpl extends BasicDaoImpl<Cart> implements CartDao{
	
	@Autowired
	public CartDaoImpl(SessionFactory sessionFactory) {
		setSessionFactory(sessionFactory);
	}
}
