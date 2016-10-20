package com.jinmengzhu.groupdining.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.jinmengzhu.groupdining.domain.PagerModel;

public interface BasicDao<T> {

	public void menu(T o);

	public void delete(int id);
	
	public void delete(String id);
	
	public void delete(Long id);

	public void delete(T t);

	public Serializable save(T o);

	public void saveOrUpdate(T o);

	public T merge(T o);

	public T getById(Integer id);
	
	public T getById(Long id);

	public List<T> getEntityListAll();

	public T getEntityByProperty(String property, Object value);
	
	public T getEntityByPropertyOther(String property, Object value, Long id);

	public T getEntityByPropertyNull(String property);

	public T getEntityByProperties(String[] properties, Object[] values);

	public List<T> getEntityListByProperty(String property, Object value);

	public List<T> getEntityListByProperty(String property, Object value, String orderProperty, boolean isAsc);

	public List<T> getEntityListByProperty(String property, Object value, String[] orderProperties, boolean[] isAscs);

	public List<T> getEntityListByPropertyNull(String property);

	public List<T> getEntityListByPropertyNull(String property, String orderProperty, boolean isAsc);

	public List<T> getEntityListByPropertyNull(String property, String[] orderPropertirs, boolean[] isAscs);

	public List<T> getEntityByPropertyBetween(String property, Object lo, Object hi);

	public List<T> getEntityByPropertyBetween(String property, Object lo, Object hi, String orderProperty, boolean isAsc);

	public List<T> getEntityByPropertyBetween(String property, Object lo, Object hi, String[] orderProperties,
			boolean[] isAscs);

	public List<T> getEntityByPropertyLike(String property, String value, Object lo, Object hi);

	public List<T> getEntityByPropertyLike(String property, String value, Object lo, Object hi, String orderProperty,
			boolean isAsc);

	public List<T> getEntityByPropertyLike(String property, String value, Object lo, Object hi,
			String[] orderProperties, boolean[] isAscs);

	public List<T> getEntityListByProperties(String[] properties, Object[] values);

	public List<T> getEntityListByProperties(String[] properties, Object[] values, String orderProperty, boolean isAsc);

	public List<T> getEntityListByProperties(String[] properties, Object[] values, String[] orderProperties,
			boolean[] isAscs);

	public List<T> getEntityListByPropertiesLike(String[] properties, String[] values);

	public List<T> getEntityListByPropertiesLike(String[] properties, String[] values, String orderProperty,
			boolean isAsc);

	public List<T> getEntityListByPropertiesLike(String[] properties, String[] values, String[] orderProperties,
			boolean[] isAscs);

	public List<T> getEntityListByPropertyBetween(String property, Object lo, Object hi);

	public List<T> getEntityListByPropertyBetween(String property, Object lo, Object hi, String orderProperty,
			boolean isAsc);

	public List<T> getEntityListByPropertyBetween(String property, Object lo, Object hi, String[] orderProperties,
			boolean[] isAscs);

	@SuppressWarnings("rawtypes")
	public PagerModel findEntityList(PagerModel pagerModel);

	@SuppressWarnings("rawtypes")
	public PagerModel findEntityListByProperties(PagerModel pagerModel, String[] properties, Object[] values);

	@SuppressWarnings("rawtypes")
	public PagerModel findEntityListByPropertiesLike(PagerModel pagerModel, String[] properties, String[] values);

	@SuppressWarnings("rawtypes")
	public PagerModel findEntityListByPropertiesNull(PagerModel pagerModel, String[] properties);

	@SuppressWarnings("rawtypes")
	public PagerModel findEntityListByPropertyBetween(PagerModel pagerModel, String property, Object lo, Object hi);
	
	@SuppressWarnings("rawtypes")
	public Object queryObjectById(Class clz, Long id) throws Exception;
	
	@SuppressWarnings("rawtypes")
	public PagerModel queryObjectPageByHql(String hql, PagerModel page,
			Map<String, Object> params) throws Exception;

	/**
	 * 功能描述：根据ids批量删除
	 * 使用方法：
	 * 使用约束：
	 * 逻辑：
	 * @param longIds
	 * @throws Exception
	 */
	public void deleteByIds(Class<?> clazz,String longIds);
	
	/**
	 * 功能描述:  查询集合
	 * 使用方法：
	 * 使用约束：
	 * 逻辑：
	 * 类型：
	 * @param hql
	 * @param map
	 * @return
	 * @throws Exception
	 * @see #
	 */
	@SuppressWarnings("rawtypes")
	public List queryObjectsByHql(String hql, Map map) throws Exception;
	
	/**
	  * 功能描述：查询集合 纯hql语句
	  * 使用约束：
	  * @param hql
	  * @return
	  * @throws Exception
	  * create by yxg 2015年11月23日下午6:53:25
	  */
	@SuppressWarnings("rawtypes")
	public List queryObjectsByHql(String hql) throws Exception;
	
	@SuppressWarnings("rawtypes")
	public List queryObjectsLimitByHql(String hql,int start,int limit) throws Exception;
	
	/**
	 * 功能描述：根据id 查询对象 
	 * 使用方法：from xxx where id in(1,2,3)
	 * 使用约束：
	 * 逻辑：
	 * @param ids
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public List queryObjectByIds(Class<?> clazz,String ids) throws Exception;
	
	 /**
	 * 功能描述：通过sql分页查询对象集合  返回分页对象
	 * 使用方法：
	 * 使用约束：
	 * 逻辑：
	 * @param clazz
	 * @param sql
	 * @param parmMap
	 * @param page
	 * @return
	 * @throws Exception
	 * create yxg 2015年7月4日 上午10:21:39
	 */
	@SuppressWarnings("rawtypes")
	public PagerModel queryObjectsSqlAndPage(Class clazz, String sql, Map<String, Object> parmMap, PagerModel page) throws Exception;
	
	 /**
	 * 功能描述：通过sql分页查询对象集合  返回List集合
	 * 使用方法：
	 * 使用约束：
	 * 逻辑：
	 * @param clazz
	 * @param sql
	 * @param parMap
	 * @param pageSize
	 * @param pageIndex
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public List queryObjectsSql(Class clazz, String sql, Map<String, Object> parMap, int pageSize, int pageIndex);
	
	@SuppressWarnings("rawtypes")
	public List queryListObjectsSql(String sql, Map<String, Object> parMap, int pageSize, int pageIndex);
	
	/**
	 * 功能描述：纯sql查询 提供返回对象类型
	 * 使用方法：
	 * 使用约束：
	 * 逻辑：
	 * @param clazz
	 * @param sql
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public List queryObjectsBySql(Class clazz, String sql);
	/**
	 * 功能描述：纯sql查询
	 * 使用方法：
	 * 使用约束：
	 * 逻辑：
	 * @param sql
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List queryObjectsBySql(String sql);
	/**
	 * 功能描述：纯sql查询 map携带参数
	 * 使用方法：
	 * 使用约束：
	 * 逻辑：
	 * @param clazz
	 * @param sql
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public List queryObjectsBySql(Class clazz, String sql, Map map);
	
	/**
	  * 功能描述：
	  * 使用约束：
	  * @param hql
	  */
	public void commitHQL(String hql);

	/**
	  * 功能描述：
	  * 使用约束：
	  * @param property
	  * @param value
	  * @param id
	  * @return
	  * create by yxg 2015年12月15日下午2:17:53
	  */
	@SuppressWarnings("rawtypes")
	public List getEntitysByPropertyOther(String property, Object value, Long id);
	
	
}
