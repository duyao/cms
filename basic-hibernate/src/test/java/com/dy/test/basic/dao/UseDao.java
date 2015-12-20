package com.dy.test.basic.dao;


import org.springframework.stereotype.Repository;

import com.dy.basic.dao.BaseDao;
import com.dy.test.basic.model.User;

@Repository
//means this class is dao
public class UseDao extends BaseDao<User> implements IUserDao {

	
}
