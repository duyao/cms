package com.dy.cms.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.dy.basic.dao.BaseDao;
import com.dy.cms.model.Group;
import com.dy.cms.model.Role;
import com.dy.cms.model.RoleType;
import com.dy.cms.model.User;
import com.dy.cms.model.UserGroup;
import com.dy.cms.model.UserRole;

@Repository("userDao")
@SuppressWarnings("unchecked")
public class UserDao extends BaseDao<User> implements IUserDao {

	

	@Override
	public List<Role> listUserRoles(int userId) {
		String hql = "select ur.role from UserRole ur where ur.user.id = ?";
		return this.getSession().createQuery(hql).setParameter(0, userId).list();
	}

	@Override
	public List<Integer> listUserRoleIds(int userId) {
		String hql = "select ur.role.id from UserRole ur where ur.user.id = ?";
		return this.getSession().createQuery(hql).setParameter(0, userId).list();
	}

	@Override
	public List<Group> listUserGroups(int userId) {
		String hql = "select ug.group from UserGroup ug where ug.user.id = ?";
		return this.getSession().createQuery(hql).setParameter(0, userId).list();
	}

	
	@Override
	public List<Integer> listUserGroupIds(int userId) {
		String hql = "select ug.group.id from UserGroup ug where ug.user.id = ?";
		return this.getSession().createQuery(hql).setParameter(0, userId).list();
	}

	@Override
	public UserRole loadUserRole(int userId, int roleId) {
		String hql = "select ur from UserRole ur where ur.user.id = ? and ur.role.id = ?";
		return (UserRole) this.getSession().createQuery(hql).setParameter(0, userId).setParameter(1, roleId).uniqueResult();
	}

	@Override
	public UserGroup loadUserGroup(int userId, int groupId) {
		String hql = "select ug from UserGroup ug where ur.user.id = ? and ur.group.id = ?";
		return (UserGroup) this.getSession().createQuery(hql).setParameter(0, userId).setParameter(1, groupId).uniqueResult();
	}

	@Override
	public User loadByUsername(String username) {
		String hql = " from user where user.username = ?";
		return (User) this.getSession().createQuery(hql).setParameter(0, username).uniqueResult();
	}

	@Override
	public List<User> listRoleUsers(int roleId) {
		String hql = "select ur.user from UserRole ur where ur.role.id=?";
		return this.list(hql,roleId);
	}

	@Override
	public List<User> listRoleUsers(RoleType roleType) {
		String hql = "select ur.user from UserRole ur where ur.role.roleType=?";
		return this.list(hql,roleType);
	}

	@Override
	public List<User> listGroupUsers(int gid) {
		String hql = "select ug.user from UserGroup ug where ug.group.id=?";
		return this.list(hql,gid);
	}

	

}
