package com.jinmengzhu.groupdining.dao.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.google.gson.Gson;
import com.jinmengzhu.groupdining.dao.BasicDao;
import com.jinmengzhu.groupdining.domain.PagerModel;

/**
 * 数据库公用的基本类，包含常用方法
 */
@Repository
@Transactional
public class BasicDaoImpl<T> extends HibernateDaoSupport implements BasicDao<T> {

	private static Logger log = LoggerFactory.getLogger(BasicDaoImpl.class);

	protected Gson gson = new Gson();

	protected Class<?> entityClass;

	@Autowired
	public BasicDaoImpl(SessionFactory sessionFactory) {
		setSessionFactory(sessionFactory);
	}

	@SuppressWarnings("rawtypes")
	public BasicDaoImpl() {
		Type type = this.getClass().getGenericSuperclass();
		if (!type.equals(HibernateDaoSupport.class)) {
			ParameterizedType pt = (ParameterizedType) type;
			this.entityClass = (Class) pt.getActualTypeArguments()[0];
		}
	}

	/**
	 * 增加一个对象
	 */
	public Serializable save(T o) {
		return getHibernateTemplate().save(o);
	}

	public void saveOrUpdate(T o) {
		getHibernateTemplate().saveOrUpdate(o);
	}

	public T merge(T o) {
		T t = getHibernateTemplate().merge(o);
		return t;
	}

	/*
	 * 根据id删除一个对象 int
	 * 
	 * @see com.wahwg.dao.BasicDao#delete(int)
	 */
	public void delete(int id) {
		Object o = getHibernateTemplate().get(entityClass, id);
		if (o != null) {
			getHibernateTemplate().delete(o);
		}
	}

	/*
	 * 根据id删除一个对象 String
	 * 
	 * @see com.wahwg.dao.BasicDao#delete(String)
	 */
	public void delete(String id) {
		Object o = getHibernateTemplate().get(entityClass, id);
		if (o != null) {
			getHibernateTemplate().delete(o);
		}
	}

	/*
	 * 根据id删除一个对象 Long
	 * 
	 * @see com.wahwg.dao.BasicDao#delete(Long)
	 */
	public void delete(Long id) {
		Object o = getHibernateTemplate().get(entityClass, id);
		if (o != null) {
			getHibernateTemplate().delete(o);
		}
	}

	/*
	 * 删除一个对象
	 * 
	 * @see com.wahwg.dao.BasicDao#delete(int)
	 */
	public void delete(T t) {
		getHibernateTemplate().delete(t);
	}

	/**
	 * 更新
	 */
	public void update(T o) {
		getHibernateTemplate().update(o);
	}

	protected void addOrder(Criteria criteria, String[] orderByProperties, boolean[] isAscs) {

		if (criteria == null || orderByProperties == null || isAscs == null || orderByProperties.length == 0
				|| isAscs.length == 0) {
			return;
		}

		if (orderByProperties.length != isAscs.length) {
			return;
		}

		for (int i = 0; i < isAscs.length; i++) {
			boolean isAsc = isAscs[i];
			if (isAsc) {
				criteria.addOrder(Property.forName(orderByProperties[i]).asc());
				continue;
			}
			criteria.addOrder(Property.forName(orderByProperties[i]).desc());
		}
	}

	@SuppressWarnings("rawtypes")
	private void addOrder(Criteria criteria, PagerModel pagerModel) {
		String[] orderByProperties = pagerModel.getOrderByProperties();
		boolean[] isAscs = pagerModel.getIsAscs();
		addOrder(criteria, orderByProperties, isAscs);
	}

	protected void addEq(Criteria criteria, String[] properties, Object[] values) {

		if (criteria == null || properties == null || values == null || properties.length == 0 || values.length == 0
				|| properties[0] == null || values[0] == null) {
			return;
		}

		if (properties.length != values.length) {
			return;
		}

		List<String> aliasList = new ArrayList<String>();
		for (int i = 0; i < values.length; i++) {
			String alias = "";
			String associationPath = "";
			if (properties[i] != null) {
				String[] temp = properties[i].split("\\.");
				for (int j = 0; j < temp.length; j++) {
					if (j == 0) {
						associationPath = temp[j];
						alias = temp[j];
					} else {
						associationPath = alias + "." + temp[j];
						alias = alias + "_" + temp[j];
					}
					if (j != temp.length - 1) {
						if (!aliasList.contains(alias)) {
							criteria.createAlias(associationPath, alias);
							aliasList.add(alias);
						}
					}
				}
			}

			if (StringUtils.isEmpty(associationPath)) {
				criteria.add(Restrictions.eq(properties[i], values[i]));
			} else {
				criteria.add(Restrictions.eq(associationPath, values[i]));
			}
		}
	}

	protected void addNull(Criteria criteria, String[] properties) {

		if (criteria == null || properties == null || properties.length == 0) {
			return;
		}

		for (int i = 0; i < properties.length; i++) {
			criteria.add(Restrictions.isNull(properties[i]));
		}
	}

	protected void addNull(Criteria criteria, String property) {

		if (criteria == null) {
			return;
		}
		criteria.add(Restrictions.isNull(property));
	}

	protected void addLike(Criteria criteria, String[] properties, String[] values) {

		if (criteria == null || properties == null || values == null || properties.length == 0 || values.length == 0
				|| properties[0] == null || values[0] == null) {
			return;
		}

		if (properties.length != values.length) {
			return;
		}

		List<String> aliasList = new ArrayList<String>();
		for (int i = 0; i < values.length; i++) {
			String alias = "";
			String associationPath = "";
			if (properties[i] != null) {
				String[] temp = properties[i].split("\\.");
				for (int j = 0; j < temp.length; j++) {
					if (j == 0) {
						associationPath = temp[j];
						alias = temp[j];
					} else {
						associationPath = alias + "." + temp[j];
						alias = alias + "_" + temp[j];
					}
					if (j != temp.length - 1) {
						if (!aliasList.contains(alias)) {
							criteria.createAlias(associationPath, alias);
							aliasList.add(alias);
						}
					}
				}
			}

			if (StringUtils.isEmpty(associationPath)) {
				criteria.add(Restrictions.like(properties[i], values[i], MatchMode.ANYWHERE));
			} else {
				criteria.add(Restrictions.like(associationPath, values[i], MatchMode.ANYWHERE));
			}
		}
	}

	@SuppressWarnings("unchecked")
	public T getById(Integer id) {
		return (T) getHibernateTemplate().get(entityClass, id);
	}

	@SuppressWarnings("unchecked")
	public T getById(Long id) {
		return (T) getHibernateTemplate().get(entityClass, id);
	}

	@SuppressWarnings("unchecked")
	public T getEntityByProperty(String property, Object value) {
		T t = null;
		try {

			Query query = getSessionFactory().getCurrentSession().createQuery(
					"from " + entityClass.getSimpleName() + " where " + property + " = :" + "temp");
			query.setParameter("temp", value);
			t = (T) query.uniqueResult();
		} catch (Exception e) {
			log.info("========================================");
			log.error("qgetEntityByProperty执行失败！", e.toString() + "----" + property + "--" + t);
			log.info("========================================");
		}
		return t;
	}

	@SuppressWarnings("unchecked")
	public T getEntityByProperties(String[] properties, Object[] values) {

		if (properties == null || values == null) {
			return null;
		}
		if (properties.length != values.length) {
			return null;
		}

		String hql = "from " + entityClass.getSimpleName() + " where 1=1";
		StringBuffer stringBuffer = new StringBuffer();
		for (int i = 0; i < properties.length; i++) {
			stringBuffer.append(" and " + properties[i] + "=:" + "value" + i);
		}

		Query query = getSessionFactory().getCurrentSession().createQuery(hql + stringBuffer.toString());

		for (int i = 0; i < properties.length; i++) {
			query.setParameter("value" + i, values[i]);
		}
		T t = (T) query.uniqueResult();
		return t;
	}

	@SuppressWarnings("unchecked")
	public T getEntityByPropertys(String[] properties, int[] values) {
		if (properties == null || values == null) {
			return null;
		}

		if (properties.length != values.length) {
			return null;
		}

		String hql = "from " + entityClass.getSimpleName() + " where 1=1";
		StringBuffer stringBuffer = new StringBuffer();
		for (int i = 0; i < properties.length; i++) {
			stringBuffer.append(" and " + properties[i] + "=:" + "value" + i);
		}

		Query query = getSessionFactory().getCurrentSession().createQuery(hql + stringBuffer.toString());
		for (int i = 0; i < properties.length; i++) {
			query.setInteger("value" + i, values[i]);
		}
		T t = (T) query.uniqueResult();
		return t;
	}

	@SuppressWarnings("unchecked")
	public T getEntityByPropertyNull(String property) {

		Query query = getSessionFactory().getCurrentSession().createQuery(
				"from " + entityClass.getSimpleName() + " where " + property + " is null");
		T t = (T) query.uniqueResult();
		return t;
	}

	@SuppressWarnings("unchecked")
	public List<T> getEntityListByPropertyNull(String property) {

		Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(entityClass);
		criteria.add(Restrictions.isNull(property));
		List<T> list = (List<T>) criteria.list();
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> getEntityListByPropertyNull(String property, String orderProperty, boolean isAsc) {

		Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(entityClass);
		criteria.add(Restrictions.isNull(property));
		String[] orderProperties = { orderProperty };
		boolean[] isAscs = { isAsc };
		addOrder(criteria, orderProperties, isAscs);

		List<T> list = (List<T>) criteria.list();
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> getEntityListByPropertyNull(String property, String[] orderProperties, boolean[] isAscs) {
		Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(entityClass);
		criteria.add(Restrictions.isNull(property));
		addOrder(criteria, orderProperties, isAscs);

		List<T> list = (List<T>) criteria.list();
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> getEntityByPropertyBetween(String property, Object lo, Object hi) {
		Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(entityClass);
		criteria.add(Restrictions.between(property, lo, hi));
		List<T> list = (List<T>) criteria.list();
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> getEntityByPropertyBetween(String property, Object lo, Object hi, String orderProperty, boolean isAsc) {

		Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(entityClass);
		criteria.add(Restrictions.between(property, lo, hi));

		String[] orderProperties = { orderProperty };
		boolean[] isAscs = { isAsc };
		addOrder(criteria, orderProperties, isAscs);

		List<T> list = (List<T>) criteria.list();
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> getEntityByPropertyBetween(String property, Object lo, Object hi, String[] orderProperties,
			boolean[] isAscs) {

		Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(entityClass);
		criteria.add(Restrictions.between(property, lo, hi));
		addOrder(criteria, orderProperties, isAscs);

		List<T> list = (List<T>) criteria.list();
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> getEntityByPropertyLike(String property, String value, Object lo, Object hi) {
		Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(entityClass);
		String[] properties = { property };
		String[] values = { value };
		addLike(criteria, properties, values);
		List<T> list = (List<T>) criteria.list();
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> getEntityByPropertyLike(String property, String value, Object lo, Object hi, String orderProperty,
			boolean isAsc) {

		Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(entityClass);
		String[] properties = { property };
		String[] values = { value };
		addLike(criteria, properties, values);

		String[] orderProperties = { orderProperty };
		boolean[] isAscs = { isAsc };
		addOrder(criteria, orderProperties, isAscs);

		List<T> list = (List<T>) criteria.list();
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> getEntityByPropertyLike(String property, String value, Object lo, Object hi,
			String[] orderProperties, boolean[] isAscs) {
		Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(entityClass);
		String[] properties = { property };
		String[] values = { value };
		addLike(criteria, properties, values);
		addOrder(criteria, orderProperties, isAscs);

		List<T> list = (List<T>) criteria.list();
		return list;
	}

	@SuppressWarnings("unchecked")
	public T getEntityByProperty(String property, Integer value) {

		Query query = getSessionFactory().getCurrentSession().createQuery(
				"from " + entityClass.getSimpleName() + " where " + property + " = :" + "temp");
		query.setInteger("temp", value);
		T t = (T) query.uniqueResult();
		return t;
	}

	@SuppressWarnings("unchecked")
	public List<T> getEntityListByProperty(String property, Object value) {
		Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(entityClass);
		criteria.add(Restrictions.eq(property, value));
		List<T> list = (List<T>) criteria.list();
		return list;
	}

	@SuppressWarnings("unchecked")
	public List<T> getEntityListByProperty(String property, Object value, String orderProperty, boolean isAsc) {

		Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(entityClass);
		criteria.add(Restrictions.eq(property, value));
		String[] orderProperties = { orderProperty };
		boolean[] isAscs = { isAsc };
		addOrder(criteria, orderProperties, isAscs);

		List<T> list = (List<T>) criteria.list();
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> getEntityListByProperty(String property, Object value, String[] orderProperties, boolean[] isAscs) {
		Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(entityClass);
		criteria.add(Restrictions.eq(property, value));
		addOrder(criteria, orderProperties, isAscs);

		List<T> list = (List<T>) criteria.list();
		return list;
	}

	@SuppressWarnings("unchecked")
	public List<T> getEntityListByProperty(String property, int value, String orderPeoperty, boolean isAsc) {
		String sort = "asc";
		if (!isAsc) {
			sort = "desc";
		}
		Query query = getSessionFactory().getCurrentSession().createQuery(
				"from " + entityClass.getSimpleName() + " where " + property + " = :" + "temp" + " order by "
						+ orderPeoperty + " " + sort);
		query.setInteger("temp", value);
		List<T> list = (List<T>) query.list();
		return list;
	}

	/**
	 * 查找所有
	 */
	@SuppressWarnings("unchecked")
	public List<T> getEntityListAll() {
		Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(entityClass);
		return (List<T>) criteria.list();
	}

	/**
	 * properties比较的属性数组 values属性数组对应的值 orderByProperties排序的属性数组 isAscs是否升序
	 */
	@SuppressWarnings("unchecked")
	public List<T> getEntityListByProperties(String[] properties, Object[] values) {
		Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(entityClass);
		addEq(criteria, properties, values);
		return (List<T>) criteria.list();
	}

	/**
	 * properties比较的属性数组 values属性数组对应的值 orderByProperties排序的属性数组 isAscs是否升序
	 */
	@SuppressWarnings("unchecked")
	public List<T> getEntityListByProperties(String[] properties, Object[] values, String[] orderByProperties,
			boolean[] isAscs) {
		Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(entityClass);
		addEq(criteria, properties, values);
		addOrder(criteria, orderByProperties, isAscs);
		return (List<T>) criteria.list();
	}

	/**
	 * properties比较的属性数组 values属性数组对应的值 orderByProperties排序的属性数组 isAscs是否升序
	 */
	@SuppressWarnings("unchecked")
	public List<T> getEntityListByProperties(String[] properties, Object[] values, String orderProperty, boolean isAsc) {
		Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(entityClass);
		addEq(criteria, properties, values);

		String[] orderProperties = { orderProperty };
		boolean[] isAscs = { isAsc };
		addOrder(criteria, orderProperties, isAscs);
		return (List<T>) criteria.list();
	}

	/**
	 * properties比较的属性数组 values属性数组对应的值 orderByProperties排序的属性数组 isAscs是否升序
	 */
	@SuppressWarnings("unchecked")
	public List<T> getEntityListByPropertiesLike(String[] properties, String[] values) {
		Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(entityClass);
		addLike(criteria, properties, values);
		return (List<T>) criteria.list();
	}

	/**
	 * properties比较的属性数组 values属性数组对应的值 orderByProperties排序的属性数组 isAscs是否升序
	 */
	@SuppressWarnings("unchecked")
	public List<T> getEntityListByPropertiesLike(String[] properties, String[] values, String[] orderByProperties,
			boolean[] isAscs) {
		Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(entityClass);
		addLike(criteria, properties, values);
		addOrder(criteria, orderByProperties, isAscs);
		return (List<T>) criteria.list();
	}

	/**
	 * properties比较的属性数组 values属性数组对应的值 orderByProperties排序的属性数组 isAscs是否升序
	 */
	@SuppressWarnings("unchecked")
	public List<T> getEntityListByPropertiesLike(String[] properties, String[] values, String orderProperty,
			boolean isAsc) {
		Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(entityClass);
		addLike(criteria, properties, values);

		String[] orderProperties = { orderProperty };
		boolean[] isAscs = { isAsc };
		addOrder(criteria, orderProperties, isAscs);
		return (List<T>) criteria.list();
	}

	/**
	 * property属性 lo小值 hi大值 orderByProperties排序的属性数组 isAscs是否升序
	 */
	@SuppressWarnings("unchecked")
	public List<T> getEntityListByPropertyBetween(String property, Object lo, Object hi) {
		Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(entityClass);
		criteria.add(Restrictions.between(property, lo, hi));
		return (List<T>) criteria.list();
	}

	/**
	 * property属性 lo小值 hi大值 orderByProperties排序的属性数组 isAscs是否升序
	 */
	@SuppressWarnings("unchecked")
	public List<T> getEntityListByPropertyBetween(String property, Object lo, Object hi, String orderProperty,
			boolean isAsc) {
		Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(entityClass);
		criteria.add(Restrictions.between(property, lo, hi));

		String[] orderProperties = { orderProperty };
		boolean[] isAscs = { isAsc };
		addOrder(criteria, orderProperties, isAscs);
		return (List<T>) criteria.list();
	}

	/**
	 * property属性 lo小值 hi大值 orderByProperties排序的属性数组 isAscs是否升序
	 */
	@SuppressWarnings("unchecked")
	public List<T> getEntityListByPropertyBetween(String property, Object lo, Object hi, String[] orderByProperties,
			boolean[] isAscs) {
		Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(entityClass);
		criteria.add(Restrictions.between(property, lo, hi));
		addOrder(criteria, orderByProperties, isAscs);
		return (List<T>) criteria.list();
	}

	/**
	 * 分页查询 firstResult第一条数据的下标值 maxResults每页数据的数量 orderByProperties排序的属性数组
	 * isAscs是否升序
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public PagerModel findEntityList(PagerModel pagerModel) {

		Criteria totalCriteria = getSessionFactory().getCurrentSession().createCriteria(entityClass);
		totalCriteria.setProjection(Projections.rowCount());
		Long totalRecords = (Long) totalCriteria.uniqueResult();

		Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(entityClass);
		addOrder(criteria, pagerModel);
		int firstResult = (pagerModel.getPageNumber() - 1) * pagerModel.getPageSize();
		criteria.setFirstResult(firstResult);
		criteria.setMaxResults(pagerModel.getPageSize());
		List<T> datas = (List<T>) criteria.list();

		pagerModel.setRows(datas);
		pagerModel.setTotal(totalRecords);

		return pagerModel;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public PagerModel findEntityListByProperties(PagerModel pagerModel, String[] properties, Object[] values) {

		Criteria totalCriteria = getSessionFactory().getCurrentSession().createCriteria(entityClass);
		addEq(totalCriteria, properties, values);
		totalCriteria.setProjection(Projections.rowCount());
		Long totalRecords = (Long) totalCriteria.uniqueResult();

		Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(entityClass);
		addEq(criteria, properties, values);
		addOrder(criteria, pagerModel);
		int firstResult = (pagerModel.getPageNumber() - 1) * pagerModel.getPageSize();
		criteria.setFirstResult(firstResult);
		criteria.setMaxResults(pagerModel.getPageSize());
		List<T> datas = (List<T>) criteria.list();

		pagerModel.setRows(datas);
		pagerModel.setTotal(totalRecords);

		return pagerModel;
	}

	/**
	 * 分页查询 firstResult第一条数据的下标值 maxResults每页数据的数量 orderByProperties排序的属性数组
	 * isAscs是否升序
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public PagerModel findEntityListByPropertiesLike(PagerModel pagerModel, String[] properties, String[] values) {

		Criteria totalCriteria = getSessionFactory().getCurrentSession().createCriteria(entityClass);
		addLike(totalCriteria, properties, values);
		totalCriteria.setProjection(Projections.rowCount());
		Long totalRecords = (Long) totalCriteria.uniqueResult();

		Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(entityClass);
		addLike(criteria, properties, values);
		addOrder(criteria, pagerModel);
		int firstResult = (pagerModel.getPageNumber() - 1) * pagerModel.getPageSize();
		criteria.setFirstResult(firstResult);
		criteria.setMaxResults(pagerModel.getPageSize());
		List<T> datas = (List<T>) criteria.list();

		pagerModel.setRows(datas);
		pagerModel.setTotal(totalRecords);

		return pagerModel;
	}

	/**
	 * 分页查询 firstResult第一条数据的下标值 maxResults每页数据的数量 orderByProperties排序的属性数组
	 * isAscs是否升序
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public PagerModel findEntityListByPropertiesNull(PagerModel pagerModel, String[] properties) {

		Criteria totalCriteria = getSessionFactory().getCurrentSession().createCriteria(entityClass);
		addNull(totalCriteria, properties);
		totalCriteria.setProjection(Projections.rowCount());
		Long totalRecords = (Long) totalCriteria.uniqueResult();

		Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(entityClass);
		addNull(criteria, properties);
		addOrder(criteria, pagerModel);
		int firstResult = (pagerModel.getPageNumber() - 1) * pagerModel.getPageSize();
		criteria.setFirstResult(firstResult);
		criteria.setMaxResults(pagerModel.getPageSize());
		List<T> datas = (List<T>) criteria.list();

		pagerModel.setRows(datas);
		pagerModel.setTotal(totalRecords);

		return pagerModel;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public PagerModel findEntityListByPropertyBetween(PagerModel pagerModel, String property, Object lo, Object hi) {

		Criteria totalCriteria = getSessionFactory().getCurrentSession().createCriteria(entityClass);
		totalCriteria.add(Restrictions.between(property, lo, hi));
		totalCriteria.setProjection(Projections.rowCount());
		Long totalRecords = (Long) totalCriteria.uniqueResult();

		Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(entityClass);
		criteria.add(Restrictions.between(property, lo, hi));
		addOrder(criteria, pagerModel);
		int firstResult = (pagerModel.getPageNumber() - 1) * pagerModel.getPageSize();
		criteria.setFirstResult(firstResult);
		criteria.setMaxResults(pagerModel.getPageSize());
		List<T> datas = (List<T>) criteria.list();

		pagerModel.setRows(datas);
		pagerModel.setTotal(totalRecords);

		return pagerModel;
	}

	/**
	 * 功能描述：获取session 使用方法： 使用约束： 逻辑：
	 * 
	 * @param hibernateTemplate
	 */
	protected Session getSession(HibernateTemplate hibernateTemplate) {
		Session session = getSessionFactory().getCurrentSession();
		return session;
	}

	/**
	 * 关闭连接
	 * 
	 * @param session
	 */
	public void closeHibernateSessoin(Session session) {
		this.closeSessoin(session, this.getHibernateTemplate());
	}

	/**
	 * 功能描述：释放连接 使用方法： 使用约束： 逻辑：
	 * 
	 * @param session
	 * @param hibernateTemplate
	 */
	protected void closeSessoin(Session session, HibernateTemplate hibernateTemplate) {
		// SessionFactoryUtils.releaseSession(session,
		// hibernateTemplate.getSessionFactory());
		session.close();
	}

	public Session getHibernateSession() {
		return this.getSession(this.getHibernateTemplate());
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Object queryObjectById(Class clz, Long id) throws Exception {
		HibernateTemplate hibernateTemplate = getHibernateTemplate();
		Object object = null;
		try {
			object = hibernateTemplate.get(clz, id);
		} catch (Exception ex) {
			log.info("========================================");
			log.error("queryObjectById执行失败！", ex);
			log.error("queryObjectById:" + id);
			log.info("========================================");
		}
		return object;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public PagerModel queryObjectPageByHql(String hql, PagerModel page, Map<String, Object> params) {
		// 形成页信息
		PagerModel result = new PagerModel();
		long recordCount = 0;
		try {
			if (StringUtils.isEmpty(hql)) {
				throw new Exception("没有查询语句，无法查询");
			}
			if (page == null || page.getPageSize() == 0) {
				page = new PagerModel();
			}
			recordCount = this.queryObjectsHql(hql, params);

		} catch (Exception e) {
			e.printStackTrace();
		}
		result.setTotal(recordCount);
		result.setPageSize(page.getPageSize() == 0 ? 10 : page.getPageSize());
		result.setPageNumber(page.getPageNumber() == 0 ? 1 : page.getPageNumber());
		// 形成返回结果的sql语句
		Session session = this.getHibernateSession();
		Query query = session.createQuery(hql);
		if (params != null && params.size() != 0) {
			Iterator itParam = params.keySet().iterator();
			for (int i = 0; i < params.size(); i++) {
				String param = (String) itParam.next();
				Object paramValue = params.get(param);
				query.setParameter(param, paramValue);
			}
		}
		// 计算页数 2*10 20-10=10
		int toCount = result.getPageNumber() * result.getPageSize();
		if (toCount > 0) {
			int fromCount = toCount - page.getPageSize();
			query.setFirstResult(fromCount);
			query.setMaxResults(page.getPageSize());
		}
		List<T> datas = (List<T>) query.list();
		result.setRows(datas);
		return result;
	}

	@SuppressWarnings("rawtypes")
	public List queryObjectsByHql(String hql) {
		return this.queryObjectsByHql(hql, null);
	}

	@SuppressWarnings("rawtypes")
	public List queryObjectsByHql(String hql, Map map) {

		List list = new ArrayList();
		HibernateTemplate hibernateTemplate = this.getHibernateTemplate();
		Session session = this.getSession(hibernateTemplate);
		try {
			Query query = session.createQuery(hql);
			if (map == null) {
				return query.list();
			}
			Iterator keyiterator = map.keySet().iterator();
			while (keyiterator.hasNext()) {
				String keyString = (String) keyiterator.next();
				query.setParameter(keyString, map.get(keyString));
			}
			list = query.list();
		} catch (Exception ex) {
			log.info("========================================");
			log.error("queryObjectsByHql执行失败！", ex);
			log.error(hql);
			log.info("========================================");
		} finally {
			// this.closeHibernateSessoin(session);
		}
		return list;
	}

	/*
	 * 根据ids 批量删除对象
	 * 
	 * @see com.aipu.yq.api.dao.BasicDao#deleteByIds(java.lang.Class,
	 * java.lang.Long[])
	 */
	@Override
	public void deleteByIds(Class<?> clazz, String longIds) {
		final String hqlStr = " delete " + clazz.getName() + " where id in(" + longIds + ")";
		commitHQL(hqlStr);
	}

	/**
	 * 功能描述：提交hql语句到数据库执行 公共方法 使用方法： 使用约束： 逻辑：
	 * 
	 * @param hql
	 * @throws Exception
	 */
	public void commitHQL(String hql) {

		HibernateTemplate hibernateTemplate = this.getHibernateTemplate();
		Session session = this.getSession(hibernateTemplate);
		Query query = session.createQuery(hql);
		query.executeUpdate();
		/*
		 * try { } catch (Exception e) {
		 * log.info("========================================");
		 * log.error("commitHQL(String hql)执行hql失败！", e); log.error(hql);
		 * log.info("========================================");
		 * 
		 * } finally { // closeHibernateSessoin(session); }
		 */
	}

	/**
	 * 功能描述：提交sql语句到数据库执行 使用方法： 使用约束： 逻辑：
	 * 
	 * @param sql
	 */
	public void commitSQL(String sql) {

		HibernateTemplate hibernateTemplate = this.getHibernateTemplate();
		Session session = this.getSession(hibernateTemplate);
		Query query = session.createSQLQuery(sql);
		query.executeUpdate();
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List queryObjectByIds(Class<?> clazz, String ids) throws Exception {
		final String hqlStr = " from " + clazz.getName() + " where id in(" + ids + ")";
		HibernateTemplate hibernateTemplate = this.getHibernateTemplate();
		Session session = this.getSession(hibernateTemplate);
		List list = new ArrayList();
		try {
			Query query = session.createQuery(hqlStr);
			list = query.list();
		} catch (Exception ex) {
			log.info("========================================");
			log.error("commitHQL(String hql)执行hql失败！", ex);
			log.error(hqlStr);
			log.info("========================================");
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public T getEntityByPropertyOther(String property, Object value, Long id) {
		Query query = getSessionFactory().getCurrentSession().createQuery(
				"from " + entityClass.getSimpleName() + " where " + property + " = :" + "temp" + " and id != :" + "id");
		query.setParameter("temp", value);
		query.setParameter("id", id);
		T t = (T) query.uniqueResult();
		return t;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List getEntitysByPropertyOther(String property, Object value, Long id) {
		Query query = getSessionFactory().getCurrentSession().createQuery(
				"from " + entityClass.getSimpleName() + " where " + property + " = :" + "temp" + " and id != :" + "id");
		query.setParameter("temp", value);
		query.setParameter("id", id);
		List list = query.list();
		return list;
	}

	/**
	 * 通过本地化SQL和查询参数Map查询对象信息
	 *
	 * @param clazz
	 *            对象类信息
	 * @param sql
	 *            sql
	 * @param parMap
	 *            参数map
	 * @param pageSize
	 *            页大小
	 * @param pageIndex
	 *            页编号
	 * @return Page
	 * @throws Exception
	 *             异常
	 */
	@SuppressWarnings({ "rawtypes", "unused" })
	public List queryObjectsSql(Class clazz, String sql, Map<String, Object> parMap, int pageSize, int pageIndex) {

		Session session = this.getHibernateSession();
		List list = null;
		String info = "SQL 查询 Query:" + sql;
		info += " ; 参数 Para :";
		Class temp = List.class;
		try {
			SQLQuery query = session.createSQLQuery(sql);
			if (clazz != null && temp != clazz)
				query.addEntity("pt", clazz);
			if (parMap != null && !parMap.isEmpty()) {
				for (String paramName : parMap.keySet()) {
					info += paramName + "=" + parMap.get(paramName);
					query.setParameter(paramName, parMap.get(paramName));
					info += ",";
				}
			}
			// 判断是否需要分页
			if (pageSize > 0 && pageIndex >= 0) {
				query.setMaxResults(pageSize);
				if (pageIndex == 0) {
					pageIndex = 1;
				}
				query.setFirstResult((pageIndex - 1) * pageSize);
			}
			list = query.list();
		} catch (Exception ex) {
			log.info("========================================");
			log.error("根据sql语句和查询条件查询实例失败", ex);
			log.error(sql);
			log.info("========================================");
		} finally {
			// this.closeHibernateSessoin(session);
		}
		return list;
	}

	@SuppressWarnings({ "rawtypes", "unused" })
	public List queryListObjectsSql(String sql, Map<String, Object> parMap, int pageSize, int pageIndex) {

		Session session = this.getHibernateSession();
		List list = null;
		String info = "SQL 查询 Query:" + sql;
		info += " ; 参数 Para :";
		Class temp = List.class;
		try {
			SQLQuery query = session.createSQLQuery(sql);
			if (parMap != null && !parMap.isEmpty()) {
				for (String paramName : parMap.keySet()) {
					info += paramName + "=" + parMap.get(paramName);
					query.setParameter(paramName, parMap.get(paramName));
					info += ",";
				}
			}
			// 判断是否需要分页
			if (pageSize > 0 && pageIndex >= 0) {
				query.setMaxResults(pageSize);
				if (pageIndex == 0) {
					pageIndex = 1;
				}
				query.setFirstResult((pageIndex - 1) * pageSize);
			}
			list = query.list();
		} catch (Exception ex) {

		} finally {
		}
		return list;
	}

	/**
	 * 功能描述：通过sql查询对象 含分页 使用方法： 使用约束： 逻辑：
	 * 
	 * @param clazz
	 * @param sql
	 * @param parmMap
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public PagerModel queryObjectsSqlAndPage(Class clazz, String sql, Map<String, Object> parmMap, PagerModel page) {

		PagerModel newPage = new PagerModel();
		newPage.setPageNumber(page.getPageNumber() == 0 ? 1 : page.getPageNumber());
		newPage.setPageSize(page.getPageSize());
		List returnList = new ArrayList();
		try {
			returnList = this.queryObjectsSql(clazz, sql, parmMap, page.getPageSize(), page.getPageNumber());
		} catch (Exception e) {
			e.printStackTrace();
		}
		int cout = this.queryObjectsSql(sql, parmMap);
		newPage.setTotal((long) cout);
		newPage.setRows(returnList);
		return newPage;
	}

	/**
	 * 功能描述：通过sql查询对象集合 使用方法： 使用约束： 逻辑：
	 * 
	 * @param sql
	 * @param parMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	private int queryObjectsSql(String sql, Map<String, Object> parMap) {

		List list = null;
		try {
			if (sql == null)
				throw new Exception("通过SQL查询数据失败：SQL语句为空!");
			sql = sql.toLowerCase();
			if (sql.indexOf("from") == -1)
				throw new Exception("通过SQL查询数据失败：SQL语句缺失from关键字!");

			Session session = this.getHibernateSession();
			final String sqlStr = "select count(1)  " + sql.substring(sql.indexOf("from"));
			SQLQuery query = session.createSQLQuery(sqlStr);
			if (parMap != null && !parMap.isEmpty()) {
				for (String paramName : parMap.keySet()) {
					query.setParameter(paramName, parMap.get(paramName));
				}
			}
			list = query.list();
		} catch (Exception ex) {
			log.info("========================================");
			log.error("根据sql语句和查询条件查询实例失败", ex);
			log.error(sql);
			log.info("========================================");
		} finally {
			// this.closeHibernateSessoin(session);

		}
		if (list != null && list.size() > 0) {
			return Integer.valueOf(String.valueOf(list.get(0)));
			// return list.size();
		} else {
			return 0;
		}
	}

	/**
	 * 功能描述：通过sql查询对象集合 使用方法： 使用约束： 逻辑：
	 * 
	 * @param sql
	 * @param parMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes" })
	private int queryObjectsHql(String hql, Map<String, Object> parMap) {

		List list = null;
		try {
			if (hql == null)
				throw new Exception("通过HQL查询数据失败：SQL语句为空!");
			if (hql.indexOf("from") == -1)
				throw new Exception("通过HQL查询数据失败：SQL语句缺失from关键字!");

			Session session = this.getHibernateSession();
			final String hqlStr = "select count(1)  " + hql.substring(hql.indexOf("from"));
			Query query = session.createQuery(hqlStr);
			if (parMap != null && !parMap.isEmpty()) {
				for (String paramName : parMap.keySet()) {
					query.setParameter(paramName, parMap.get(paramName));
				}
			}
			list = query.list();
		} catch (Exception ex) {
			log.info("========================================");
			log.error("根据sql语句和查询条件查询实例失败", ex);
			log.error(hql);
			log.info("========================================");
		} finally {
			// this.closeHibernateSessoin(session);

		}
		if (list != null && list.size() > 0) {
			return Integer.valueOf(String.valueOf(list.get(0)));
		} else {
			return 0;
		}
	}

	@SuppressWarnings({ "rawtypes" })
	public List queryObjectsBySql(Class clazz, String sql) {

		List list = null;
		try {
			Session session = this.getHibernateSession();
			SQLQuery query = session.createSQLQuery(sql);
			Class temp = List.class;
			if (clazz != null && clazz != temp) {
				query.addEntity(clazz);
			}
			list = query.list();
		} catch (Exception ex) {
			log.info("========================================");
			log.error("根据类名和sql号查询 " + clazz.getName() + " 实例失败", ex);
			log.error(sql);
			log.info("========================================");
		} finally {
			// closeHibernateSessoin(session);
		}

		return list;
	}

	@SuppressWarnings({ "rawtypes" })
	public List queryObjectsBySql(String sql) {

		List list = null;
		try {
			Session session = this.getHibernateSession();
			SQLQuery query = session.createSQLQuery(sql);
			list = query.list();
		} catch (Exception ex) {
			log.info("========================================");
			log.error("queryObjectsBySql查询 实例失败", ex);
			log.error(sql);
		} finally {
			//
		}

		return list;
	}

	@SuppressWarnings({ "rawtypes" })
	public List queryObjectsBySql(Class clazz, String sql, Map map) {
		List list = null;
		Session session = this.getHibernateSession();
		SQLQuery query = session.createSQLQuery(sql);
		if (clazz != null) {
			query.addEntity(clazz);
		}
		if (map != null) {
			Set set = map.keySet();
			if (set != null) {
				Iterator it = set.iterator();
				while (it.hasNext()) {
					String key = (String) it.next();
					Object value = map.get(key);
					query.setParameter(key, value);
				}
			}
		}
		list = query.list();
		return list;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List queryObjectsLimitByHql(String hql, int start, int limit) throws Exception {
		List list = new ArrayList();
		HibernateTemplate hibernateTemplate = this.getHibernateTemplate();
		Session session = this.getSession(hibernateTemplate);
		try {
			Query query = session.createQuery(hql);
			query.setFirstResult(start);
			query.setMaxResults(limit);
			list = query.list();
		} catch (Exception ex) {
			log.info("========================================");
			log.error("queryObjectsByHql执行失败！", ex);
			log.error(hql);
			log.info("========================================");

		} finally {
			// this.closeHibernateSessoin(session);
		}
		return list;
	}
}
