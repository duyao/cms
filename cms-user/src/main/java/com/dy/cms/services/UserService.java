package com.dy.cms.services;

import java.util.List;

import javax.inject.Inject;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.stereotype.Service;

import com.dy.basic.model.Pagination;
import com.dy.cms.dao.IGroupDao;
import com.dy.cms.dao.IRoleDao;
import com.dy.cms.dao.IUserDao;
import com.dy.cms.model.CmsException;
import com.dy.cms.model.Group;
import com.dy.cms.model.Role;
import com.dy.cms.model.User;
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
			//UserRole userRole = userDao.loadUserRole(user.getId(), rid);
			userDao.addUserRole(user, role);
		}
		//添加组
		for(Integer gid : gids){
			Group group = groupDao.load(gid);
			if(group == null) throw new CmsException("要添加的组不存在");
			userDao.addUsergroup(user, group);
		}
	}

	@Override
	public void delete(int id) {
		//TODO 检查文章文章存在
		//1.删除角色
		userDao.deleteUserRole(id);
		//2.删除组
		userDao.deleteUserGroup(id);
		//3.删除用户
		userDao.delete(id);
	}

	@Override
	public void update(User user, Integer[] rids, Integer[] gids) {
		//1.获取用户组和用户角色
		List<Role> roles = userDao.listUserRoles(user.getId());
		List<Group> groups = userDao.listUserGroups(user.getId());
		//2.添加新的组和角色
		for(Integer rid : rids){
			Role role = roleDao.load(rid);
			//原来没有，进行添加
			if(!roles.contains(role)){
				userDao.addUserRole(user, role);
			}
		}
		for(Integer gid : gids){
			Group group = groupDao.load(gid);
			if(!groups.contains(group)){
				userDao.addUsergroup(user, group);
			}
		}
		//3.删除组和角色
		for(Role role : roles){
			//commoms-lang中的工具类
			if(!ArrayUtils.contains(rids,role.getId())){
				userDao.deleteUserRole(user.getId(), role.getId());
			}
		}
		for(Group group : groups){
			if(!ArrayUtils.contains(gids, group.getId())){
				userDao.deleteUserGroup(user.getId(), group.getId());
			}
		}
		
	}

	@Override
	public void updateStatus(int id) {
		User user = userDao.load(id);
		if(user == null) throw new CmsException("要删除的用户不存在");
		if(user.getStatus() == 0){
			user.setStatus(1);
		}else{
			user.setStatus(0);
		}
		userDao.update(user);
	}

	@Override
	public Pagination<User> findUser() {
		return userDao.findUser();
	}

	@Override
	public User load(int id) {
		return userDao.load(id);
	}

	@Override
	public List<Role> listUserRoles(int id) {
		return userDao.listUserRoles(id);
	}

	@Override
	public List<Group> listUserGroups(int id) {
		return userDao.listUserGroups(id);
	}

	@Override
	public List<Integer> listUserRoleIds(int id) {
		return userDao.listUserRoleIds(id);
	}

	@Override
	public List<Integer> listUserGroupIds(int id) {
		return userDao.listUserGroupIds(id);
	}

	@Override
	public List<User> listGroupUsers(int gid) {
		return userDao.listGroupUsers(gid);
	}

	@Override
	public List<User> listRoleUsers(int rid) {
		return userDao.listRoleUsers(rid);
	}


}
