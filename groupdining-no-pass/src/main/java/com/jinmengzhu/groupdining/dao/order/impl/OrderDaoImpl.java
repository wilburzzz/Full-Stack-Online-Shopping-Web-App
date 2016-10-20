package com.jinmengzhu.groupdining.dao.order.impl;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jinmengzhu.groupdining.dao.impl.BasicDaoImpl;
import com.jinmengzhu.groupdining.dao.order.OrderDao;
import com.jinmengzhu.groupdining.domain.order.Order;

@Repository
public class OrderDaoImpl extends BasicDaoImpl<Order> implements OrderDao{

	@Autowired
	public OrderDaoImpl(SessionFactory sessionFactory) {
		setSessionFactory(sessionFactory);
	}
}
