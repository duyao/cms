package com.dy.cms.services;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.dy.basic.model.Pagination;
import com.dy.cms.dao.IGroupDao;
import com.dy.cms.dao.IRoleDao;
import com.dy.cms.dao.IUserDao;
import com.dy.cms.model.CmsException;
import com.dy.cms.model.Group;
import com.dy.cms.model.Role;
import com.dy.cms.model.User;
import com.dy.cms.model.UserRole;
@Service("userService")
public class UserService implements IUserService {
	private IUserDao userDao;
	private IRoleDao roleDao;
	private IGroupDao groupDao;

	@Inject
	public IUserDao getUserDao() {
		return userDao;
	}
	@Inject
	public void setUserDao(IUserDao userDao) {
		this.userDao = userDao;
	}
	@Inject
	public IRoleDao getRoleDao() {
		return roleDao;
	}
	
	public void setRoleDao(IRoleDao roleDao) {
		this.roleDao = roleDao;
	}
	
	public IGroupDao getGroupDao() {
		return groupDao;
	}
	
	public void setGroupDao(IGroupDao groupDao) {
		this.groupDao = groupDao;
	}

	@Override
	public void add(User user, Integer[] rids, Integer[] gids) {
		//添加用户
		User tu = userDao.loadByUsername(user.getUsername());
		if(tu != null) throw new CmsException("添加的用户对象已经存在，不能继续添加");
		userDao.add(tu);
		//添加角色
		for (Integer rid : rids) {
			//1.检查角色是否存在，不存在就抛出异常
			Role role = roleDao.load(rid);
			if(role == null) throw new CmsException("要添加的角色不存在");
			//2.检查用户角色是否存在，如果不存在，就添加
			
		}
	}

	@Override
	public void delete(int id) {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(User user, Integer[] rids, Integer[] gids) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateStatus(int id) {
		// TODO Auto-generated method stub

	}

	@Override
	public Pagination<User> findUser() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User load(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Role> listUserRoles(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Group> listUserGroups(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Integer> listUserRoleIds(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Integer> listUserGroupIds(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<User> listGroupUsers(int gid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<User> listRoleUsers(int rid) {
		// TODO Auto-generated method stub
		return null;
	}


}
