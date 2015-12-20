package com.dy.test.basic.dao;

import javax.inject.Inject;

import org.dbunit.dataset.IDataSet;
import org.dbunit.operation.DatabaseOperation;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.dy.test.DbUnit.AbstractDBUnitTestCase;
import com.dy.test.DbUnit.EntityHelper;
import com.dy.test.basic.model.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/beans.xml")
public class TestUserDao extends AbstractDBUnitTestCase {

	@Inject
	private IUserDao userDao;
	@Before
	public void setUp(){
		
	}
	@After
	public void tearDown(){
		
	}
	@Test
	public void TestLoad() throws Exception{
		IDataSet dataSet = createDataSet("t_user");
		DatabaseOperation.CLEAN_INSERT.execute(connection, dataSet);
		User user = userDao.load(1);
		EntityHelper.assertUser(user);
		
	}
	
}
