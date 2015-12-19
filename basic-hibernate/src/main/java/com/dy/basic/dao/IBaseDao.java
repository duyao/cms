package com.dy.basic.dao;

import java.util.List;
import java.util.Map;

import com.dy.basic.model.Pagination;

/**
 * @author dy
 * 公共的处理对象，这个对象包含了Hibernate所有的基本操作和SQL操作
 * @param <T>
 */
/**
 * @author dy
 *
 * @param <T>
 */
public interface IBaseDao<T> {
	/**
	 * @param t
	 * @return
	 * 添加对象
	 */
	//返回值是T因为有可能要得到其id
	public T add(T t);
	/**
	 * @param t
	 * 修改对象
	 */
	public void update(T t);
	/**
	 * @param id
	 * 根据id删除对象
	 */
	public void delete(int id);
	/**
	 * @param id
	 * @return
	 * 根据id加载对象
	 */
	public T load(int id);
	
	//列表分为2中情况，一种是分页一种是不分页
	//分页函数是find，不分页函数是list
	/**
	 * 不分页
	 * @param hql 查询语句
	 * @param args 多个查询参数
	 * @return 不分页结果
	 */
	public List<T> list(String hql,Object[] args);
	public List<T> list(String hql,Object arg);
	public List<T> list(String hql);
	/**
	 * 基于别名和查询对象的混合不分页
	 * @param hql 查询语句
	 * @param args 多个查询参数
	 * @param alias 查询别名
	 * @return 不分页查询结果
	 */
	public List<T> list(String hql,Object[] args,Map<String, Object> alias);
	public List<T> list(String hql,Map<String, Object> alias);

	//分页情况
	/**
	 * 分页
	 * @param hql 查询语句
	 * @param args 多个查询参数
	 * @return 分页结果
	 */
	public Pagination<T> find(String hql,Object[] args);
	public Pagination<T> find(String hql,Object arg);
	public Pagination<T> find(String hql);
	/**
	 * 基于别名和查询对象的混合分页
	 * @param hql 查询语句
	 * @param args 多个查询参数
	 * @param alias 查询别名
	 * @return 分页查询结果
	 */
	public Pagination<T> find(String hql,Object[] args,Map<String, Object> alias);
	public Pagination<T> find(String hql,Map<String, Object> alias);

	/**
	 * 根据hql查询一组对象
	 * @param hql
	 * @param args
	 * @return
	 */
	public Object queryObejct(String hql,Object[] args);
	public Object queryObejct(String hql,Object arg);
	public Object queryObejct(String hql);
	
	
	/**
	 * 根据hql更新对象
	 * @param hql
	 * @param args
	 */
	public void updateByHql(String hql,Object[] args);
	public void updateByHql(String hql,Object arg);
	public void updateByHql(String hql);
	
	
	/**
	 * 根据sql返回不带分页查询结果
	 * @param sql
	 * @param args
	 * @return
	 */
	public List<Object> listBySql(String sql,Object[] args,Class<T> clz,boolean hasEntity);
	public List<Object> listBySql(String sql,Object arg,Class<T> clz,boolean hasEntity);
	public List<Object> listBySql(String sql,Class<T> clz,boolean hasEntity);
	public List<Object> listBySql(String sql,Object[] args,Map<String, Object> alias,Class<T> clz,boolean hasEntity);
	public List<Object> listBySql(String sql,Map<String, Object> alias,Class<T> clz,boolean hasEntity);
	
	/**
	 * 根据sql返回带分页结果
	 * @param sql
	 * @param args
	 * @return
	 */
	public Pagination<Object> findBySql(String sql,Object[] args,Class<T> clz,boolean hasEntity);
	public Pagination<Object> findBySql(String sql,Object arg,Class<T> clz,boolean hasEntity);
	public Pagination<Object> findBySql(String sql,Class<T> clz,boolean hasEntity);
	public Pagination<Object> findBySql(String sql,Object[] args,Map<String, Object> alias,Class<T> clz,boolean hasEntity);
	public Pagination<Object> findBySql(String sql,Map<String, Object> alias,Class<T> clz,boolean hasEntity);
	
	
}
