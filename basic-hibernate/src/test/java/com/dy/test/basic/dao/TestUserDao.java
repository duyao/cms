package com.dy.test.basic.dao;




import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.dbunit.dataset.IDataSet;
import org.dbunit.operation.DatabaseOperation;
import org.hibernate.ObjectNotFoundException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.orm.hibernate4.SessionHolder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import com.dy.basic.model.Pagination;
import com.dy.basic.model.SystemContext;
import com.dy.test.DbUnit.AbstractDBUnitTestCase;
import com.dy.test.DbUnit.EntityHelper;
import com.dy.test.basic.model.User;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/beans.xml")
//@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
//    DbUnitTestExecutionListener.class })
public class TestUserDao extends AbstractDBUnitTestCase {

	@Inject
	private IUserDao userDao;
	@Inject//解决延迟加载
	private SessionFactory sessionFactory;
	@Before
	public void setUp() throws Exception{
		Session s = sessionFactory.openSession();
		TransactionSynchronizationManager.bindResource(sessionFactory, new SessionHolder(s));
		this.backupAllTable();
		
	}
	@After
	public void tearDown() throws Exception{
		SessionHolder holder = (SessionHolder) TransactionSynchronizationManager.getResource(sessionFactory);
		Session s = holder.getSession(); 
		s.flush();
		TransactionSynchronizationManager.unbindResource(sessionFactory);
		this.resumeTable();
	}
	@Test
//	@DatabaseSetup("classpath:t_user.xml")
	public void testLoad() throws Exception {
		System.out.println("---------------testLoad------------");
		IDataSet ds = createDataSet("t_user");
		DatabaseOperation.CLEAN_INSERT.execute(connection,ds);
		User u = userDao.load(1);
		EntityHelper.assertUser(u);
		System.out.println("-------------end---------------");
	}
	
	@Test(expected=ObjectNotFoundException.class)
	public void testDelete() throws Exception {
		System.out.println("---------------testDelete------------");
		IDataSet ds = createDataSet("t_user");
		DatabaseOperation.CLEAN_INSERT.execute(connection,ds);
		userDao.delete(1);
		User tu = userDao.load(1);
		System.out.println(tu.getName());
		System.out.println("-------------end---------------");
	}
	
	@Test
	public void testListByArgs() throws Exception {
		System.out.println("---------------testListByArgs------------");
		IDataSet ds = createDataSet("t_user");
		DatabaseOperation.CLEAN_INSERT.execute(connection,ds);
		SystemContext.setOrder("desc");
		SystemContext.setSort("id");
		List<User> expected = userDao.list("from User where id > ? and id < ?", new Object[]{1,4});
		List<User> actuals = Arrays.asList(new User(3,"admin3"),new User(2,"admin2"));
		assertNotNull(expected);
		assertTrue(expected.size()==2);
		EntityHelper.assertUser(expected, actuals);
		System.out.println("-------------end---------------");

	}
	
	@Test
	public void testListByArgsAndAlias() throws Exception {
		System.out.println("---------------testListByArgsAndAlias------------");
		IDataSet ds = createDataSet("t_user");
		DatabaseOperation.CLEAN_INSERT.execute(connection,ds);
		SystemContext.setOrder("desc");
		SystemContext.setSort("id");
		Map<String, Object> aliasMap = new HashMap<String, Object>();
		aliasMap.put("ids", Arrays.asList(1,2,3,5,6,7,8,9,10));
		List<User> expected = userDao.list("from User where id > ? and id < ? and id in (:ids)", new Object[]{1,5},aliasMap);
		List<User> actuals = Arrays.asList(new User(3,"admin3"),new User(2,"admin2"));
		assertNotNull(expected);
		assertTrue(expected.size()==2);
		EntityHelper.assertUser(expected, actuals);
		System.out.println("-------------end---------------");

	}
	
	
	@Test
	public void testFindByArgsandAlias() throws Exception {
		System.out.println("---------------testFindByArgsandAlias------------");
		IDataSet ds = createDataSet("t_user");
		DatabaseOperation.CLEAN_INSERT.execute(connection,ds);
		SystemContext.setOrder("asc");
		SystemContext.setSort("id");
		SystemContext.setPageOffset(0);
		SystemContext.setPageSize(3);
		Map<String, Object> aliasMap = new HashMap<String, Object>();
		aliasMap.put("ids", Arrays.asList(1,2,4,5,6,7,8,10));
		Pagination<User> expected = userDao.find("from User where id >= ? and id <= ? and id in (:ids)", new Object[]{1,10},aliasMap);
		List<User> actuals = Arrays.asList(new User(1,"admin1"),new User(2,"admin2"),new User(4,"admin4"));
		assertNotNull(expected);
		assertTrue(expected.getTotal()==8);
		assertTrue(expected.getSize()==3);
		assertTrue(expected.getOffset()==0);
		EntityHelper.assertUser(expected.getData(), actuals);
		System.out.println("-------------end---------------");

	}
	
	@Test
	public void testFindByArgs() throws Exception {
		System.out.println("---------------testFindByArgs------------");
		IDataSet ds = createDataSet("t_user");
		DatabaseOperation.CLEAN_INSERT.execute(connection,ds);
		SystemContext.setOrder("desc");
		SystemContext.setSort("id");
		SystemContext.setPageOffset(0);
		SystemContext.setPageSize(3);
		Pagination<User> expected = userDao.find("from User where id >= ? and id <= ? ", new Object[]{1,10});
		List<User> actuals = Arrays.asList(new User(10,"admin10"),new User(9,"admin9"),new User(8,"admin8"));
		assertNotNull(expected);
		assertTrue(expected.getTotal()==10);
		assertTrue(expected.getSize()==3);
		assertTrue(expected.getOffset()==0);
		EntityHelper.assertUser(expected.getData(), actuals);
		System.out.println("-------------end---------------");

	}
	
	@Test
	public void testListSQLByArgs() throws Exception {
		System.out.println("---------------testListSQLByArgs------------");
		IDataSet ds = createDataSet("t_user");
		DatabaseOperation.CLEAN_INSERT.execute(connection,ds);
		SystemContext.setOrder("desc");
		SystemContext.setSort("id");
		List<User> expected = userDao.listBySql("select * from t_user where id > ? and id < ?", new Object[]{1,4},User.class,true);
		List<User> actuals = Arrays.asList(new User(3,"admin3"),new User(2,"admin2"));
		assertNotNull(expected);
		assertTrue(expected.size()==2);
		EntityHelper.assertUser(expected, actuals);
		System.out.println("-------------end---------------");
	}
	
	@Test
	public void testListSQLByArgsAndAlias() throws Exception {
		System.out.println("---------------testListSQLByArgsAndAlias------------");
		IDataSet ds = createDataSet("t_user");
		DatabaseOperation.CLEAN_INSERT.execute(connection,ds);
		SystemContext.setOrder("asc");
		SystemContext.setSort("id");
		Map<String,Object> alias = new HashMap<String,Object>();
		alias.put("ids", Arrays.asList(1,2,3,5,6,7,8,9,10));
		List<User> expected = userDao.listBySql("select * from t_user where id > ? and id < ? and id in (:ids)", new Object[]{1,4},alias,User.class,true);
		List<User> actuals = Arrays.asList(new User(2,"admin2"),new User(3,"admin3"));
		assertNotNull(expected);
		assertTrue(expected.size()==2);
		EntityHelper.assertUser(expected, actuals);
		System.out.println("-------------end---------------");
	}
}
