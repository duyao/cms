/**
 * 
 */
package com.dy.basic.dao;

import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;


import javax.inject.Inject;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;

import com.dy.basic.model.Pagination;
import com.dy.basic.model.SystemContext;

/**
 * @author dy
 *
 */
//get clz使用泛型，编译时不知道其类型
@SuppressWarnings("unchecked")
public class BaseDao<T> implements IBaseDao<T> {
	
	private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	@Inject
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	protected Session getSession() {
		//
		return sessionFactory.getCurrentSession();
	}

	/**
	 * 创建一个class来获取泛型的class
	 */
	private Class<?> clz;
	public  Class<?> getClz() {
		if(clz == null){
			clz = ((Class<?>)
			(((ParameterizedType)(this.getClass().getGenericSuperclass())).getActualTypeArguments()[0]));
		}
		return clz;
	}
	
	/* (non-Javadoc)
	 * @see com.dy.basic.dao.IBaseDao#add(java.lang.Object)
	 */
	public T add(T t) {
		getSession().save(t);
		return t;
	}

	/* (non-Javadoc)
	 * @see com.dy.basic.dao.IBaseDao#update(java.lang.Object)
	 */
	public void update(T t) {
		getSession().update(t);
	}

	/* (non-Javadoc)
	 * @see com.dy.basic.dao.IBaseDao#delete(int)
	 */
	public void delete(int id) {
		//先load？获取删除的对象，然后进行删除
		getSession().delete(load(id));
	}

	/* (non-Javadoc)
	 * @see com.dy.basic.dao.IBaseDao#load(int)
	 */
	public T load(int id) {
		return (T)getSession().load(getClz(), id);
	}

	/* (non-Javadoc)
	 * @see com.dy.basic.dao.IBaseDao#list(java.lang.String, java.lang.Object[])
	 */
	public List<T> list(String hql, Object[] args) {
		return this.list(hql, args, null);
	}

	/* (non-Javadoc)
	 * @see com.dy.basic.dao.IBaseDao#list(java.lang.String, java.lang.Object)
	 */
	public List<T> list(String hql, Object arg) {
		return this.list(hql, new Object[]{arg},null);
	}

	/* (non-Javadoc)
	 * @see com.dy.basic.dao.IBaseDao#list(java.lang.String)
	 */
	public List<T> list(String hql) {
		return this.list(hql, null, null);
	}

	public static String initOrder(String hql){
		String order = SystemContext.getOrder();
		String sort = SystemContext.getSort();
		if(sort != null && !sort.equals("")){
			hql += " order by " + sort;
			if(order.equals("desc")){
				hql += " desc";
			}else{
				hql += " asc";
			}
		}
		
		return hql;
	}
	
	@SuppressWarnings("rawtypes")
	public static Query initAlias(Map<String, Object> alias, Query query){
		if(alias != null){
			Set<String> keys = alias.keySet();
			for (String key : keys) {
				Object value = alias.get(key);
				if(value instanceof Collection){
					//查询条件是列表
					//select prodImageId, prodImage from ProductImage where prodImageId=:id
					//这里id是一个列表
					query.setParameterList(key, (Collection) value);
				}else{
					query.setParameter(key, value);
				}
			}
		}
		return query;
	}
	
	public static Query initArgs(Object[] args, Query query){
		if(args != null && args.length > 0){
			int index = 0;
			for (Object agr : args) {
				query.setParameter(index++, agr);
			}
			
		}
		return query;
	}
	
	/* (non-Javadoc)
	 * @see com.dy.basic.dao.IBaseDao#list(java.lang.String, java.lang.Object[], java.util.Map)
	 */
	public List<T> list(String hql, Object[] args, Map<String, Object> alias) {
		//排序
		hql = initOrder(hql);
		//alias
		Query query = getSession().createQuery(hql);
		initAlias(alias, query);
		//args
		initArgs(args, query);
		
		return query.list();
		
	}

	/* (non-Javadoc)
	 * @see com.dy.basic.dao.IBaseDao#list(java.lang.String, java.util.Map)
	 */
	public List<T> list(String hql, Map<String, Object> alias) {
		return this.list(hql, null, alias);
	}

	/* (non-Javadoc)
	 * @see com.dy.basic.dao.IBaseDao#find(java.lang.String, java.lang.Object[])
	 */
	public Pagination<T> find(String hql, Object[] args) {
		return find(hql, args, null);
	}

	/* (non-Javadoc)
	 * @see com.dy.basic.dao.IBaseDao#find(java.lang.String, java.lang.Object)
	 */
	public Pagination<T> find(String hql, Object arg) {
		return find(hql, new Object[]{arg}, null);
	}

	/* (non-Javadoc)
	 * @see com.dy.basic.dao.IBaseDao#find(java.lang.String)
	 */
	public Pagination<T> find(String hql) {
		return find(hql, null, null);
	}

	@SuppressWarnings("rawtypes")
	public static void initPage( Pagination pagination, Query query){
		Integer size = SystemContext.getPageSize();
		if(size < 0 || size == null){
			size = 15;
		}
		Integer offset = SystemContext.getPageOffset();
		if(offset < 0 || offset == null){
			offset = 0;
		}
		pagination.setOffset(offset);
		pagination.setSize(size);
		//分页查询语句
		query.setFirstResult(offset);
		query.setMaxResults(size);
		
	}
	
	public static String setCountString(String hql,boolean ishql){
		//找到from子句
		String end = hql.substring(hql.indexOf("from"));
		String beginsString = "select count(*) " + end;
		//去掉fetch部分
		if(ishql){
			beginsString.replaceAll("fetch", "");
		}
		return beginsString;
	}
	
	/* (non-Javadoc)
	 * @see com.dy.basic.dao.IBaseDao#find(java.lang.String, java.lang.Object[], java.util.Map)
	 */
	public Pagination<T> find(String hql, Object[] args,Map<String, Object> alias) {
		hql = initOrder(hql);
		//用于查询分页结果
		Query query = getSession().createQuery(hql);
		//初始化各种参数
		initAlias(alias, query);
		initArgs(args, query);
		
		
		//用于查询total，调用countHql语句生成，拼接查询条件
		String cString = setCountString(hql, true);
		initOrder(cString);
		Query cQuery = getSession().createQuery(cString);
		//初始化各种参数
		initAlias(alias, cQuery);
		initArgs(args, cQuery);
		//得到总条数
		long total = (long) cQuery.uniqueResult();
		//分页结果集
		Pagination<T> pagination = new Pagination<T>();
		initPage(pagination, query);
		//查询数据
		pagination.setData(query.list());
		pagination.setTotal(total);
		return pagination;
	}

	/* (non-Javadoc)
	 * @see com.dy.basic.dao.IBaseDao#find(java.lang.String, java.util.Map)
	 */
	public Pagination<T> find(String hql, Map<String, Object> alias) {
		return this.find(hql, null, alias);
	}

	/* (non-Javadoc)
	 * @see com.dy.basic.dao.IBaseDao#queryObejct(java.lang.String, java.lang.Object[])
	 */
	public Object queryObejct(String hql, Object[] args) {
		Query query = getSession().createQuery(hql);
		initOrder(hql);
		initArgs(args, query);
		return query.uniqueResult();
	}

	/* (non-Javadoc)
	 * @see com.dy.basic.dao.IBaseDao#queryObejct(java.lang.String, java.lang.Object)
	 */
	public Object queryObejct(String hql, Object arg) {
		return queryObejct(hql, new Object[]{arg});
	}

	/* (non-Javadoc)
	 * @see com.dy.basic.dao.IBaseDao#queryObejct(java.lang.String)
	 */
	public Object queryObejct(String hql) {
		return queryObejct(hql, null);
	}

	/* (non-Javadoc)
	 * @see com.dy.basic.dao.IBaseDao#updateByHql(java.lang.String, java.lang.Object[])
	 */
	public void updateByHql(String hql, Object[] args) {
		Query query = getSession().createQuery(hql);
		initOrder(hql);
		initArgs(args, query);
		query.executeUpdate();
		
	}

	/* (non-Javadoc)
	 * @see com.dy.basic.dao.IBaseDao#updateByHql(java.lang.String, java.lang.Object)
	 */
	public void updateByHql(String hql, Object arg) {
		this.updateByHql(hql, new Object[]{arg});
	}

	/* (non-Javadoc)
	 * @see com.dy.basic.dao.IBaseDao#updateByHql(java.lang.String)
	 */
	@Override
	public void updateByHql(String hql) {
		this.updateByHql(hql, null);
	}
	
	
	@Override
	public <N extends Object>List<N> listBySql(String sql, Object[] args, Class<?> clz,
			boolean hasEntity) {
		return this.listBySql(sql, args, null, clz, hasEntity);
	}

	@Override
	public <N extends Object>List<N> listBySql(String sql, Object arg, Class<?> clz,
			boolean hasEntity) {
		return this.listBySql(sql, new Object[]{arg}, null, clz, hasEntity);
	}

	@Override
	public <N extends Object>List<N> listBySql(String sql, Class<?> clz, boolean hasEntity) {
		return this.listBySql(sql, null, null, clz, hasEntity);
	}

	@Override
	public <N extends Object>List<N> listBySql(String sql, Object[] args,
			Map<String, Object> alias, Class<?> clz, boolean hasEntity) {
		sql = initOrder(sql);
		SQLQuery query = getSession().createSQLQuery(sql);
		initAlias(alias, query);
		initArgs(args, query);
		if(hasEntity){
			query.addEntity(clz);
		}else{
			query.setResultTransformer(Transformers.aliasToBean(clz));
		}
		return query.list();
	}

	@Override
	public <N extends Object>List<N> listBySql(String sql, Map<String, Object> alias,
			Class<?> clz, boolean hasEntity) {
		return this.listBySql(sql, null, alias, clz, hasEntity);
	}

	@Override
	public <N extends Object>Pagination<N> findBySql(String sql, Object[] args,
			Class<?> clz, boolean hasEntity) {
		return this.findBySql(sql, args, null, clz, hasEntity);

	}

	@Override
	public <N extends Object>Pagination<N> findBySql(String sql, Object arg, Class<?> clz,
			boolean hasEntity) {
		return this.findBySql(sql,new Object[]{arg}, null, clz, hasEntity);

	}

	@Override
	public <N extends Object>Pagination<N> findBySql(String sql, Class<?> clz,
			boolean hasEntity) {
		return this.findBySql(sql, null, null, clz, hasEntity);
	}

	@Override
	public <N extends Object>Pagination<N> findBySql(String sql, Object[] args,
			Map<String, Object> alias, Class<?> clz, boolean hasEntity) {
		
		sql = initOrder(sql);
		SQLQuery query = getSession().createSQLQuery(sql);
		initAlias(alias, query);
		initArgs(args, query);
		if(hasEntity){
			query.addEntity(clz);
		}else{
			query.setResultTransformer(Transformers.aliasToBean(clz));
		}
		Pagination<N> pagination = new Pagination<N>();
		
		String cstring = setCountString(sql, false);
		cstring = initOrder(cstring);
		Query cQuery = getSession().createQuery(cstring);
		initAlias(alias, cQuery);
		initArgs(args, cQuery);
		//这个按页数查询设置对象是query，而不是查询条数的语句
		initPage(pagination, query);
		long total = (long) cQuery.uniqueResult();
		pagination.setTotal(total);
		pagination.setData(query.list());
		return pagination;
	}

	@Override
	public <N extends Object>Pagination<N> findBySql(String sql, Map<String, Object> alias,
			Class<?> clz, boolean hasEntity) {
		return this.findBySql(sql, null, alias, clz, hasEntity);

	}

}
