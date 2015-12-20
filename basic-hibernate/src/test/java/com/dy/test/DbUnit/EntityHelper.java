package com.dy.test.DbUnit;


import org.junit.Assert;

import com.dy.test.basic.model.User;

public class EntityHelper {
	private static User baseUser = new User(1,"admin1");
	public static void assertUser(User expected, User actual){
		Assert.assertNotNull(expected);
		Assert.assertEquals(expected.getId(), actual.getId());
		Assert.assertEquals(expected.getName(), actual.getName());
	}
	
	public static void assertUser(User expected){
		assertUser(expected, baseUser);
	}

}
