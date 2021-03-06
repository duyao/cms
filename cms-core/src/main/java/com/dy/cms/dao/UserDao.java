package com.dy.cms.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.dy.basic.dao.BaseDao;
import com.dy.basic.model.Pagination;
import com.dy.cms.model.CmsException;
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
		String hql = "select ur from UserRole ur left join fetch ur.user u left join fetch ur.role r where u.id = ? and r.id = ?";
		return (UserRole) this.getSession().createQuery(hql).setParameter(0, userId).setParameter(1, roleId).uniqueResult();
	}

	@Override
	public UserGroup loadUserGroup(int userId, int groupId) {
		//fetch抓取u和g，这样就使用一条语句获得结果
		String hql = "select ug from UserGroup ug left join fetch ug.user u left join fecth ug.group g where u.id = ? and g.id = ?";
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

	@Override
	public void addUserRole(User user, Role role) {
		UserRole userRole = loadUserRole(user.getId(), role.getId());
		if(userRole == null){
			userRole = new UserRole();
			userRole.setUser(user);
			userRole.setUser(user);
			this.getSession().save(userRole);
		}else{
			new CmsException("用户角色已经存在，不能添加");
		}
	}

	@Override
	public void addUsergroup(User user, Group group) {
		UserGroup userGroup = loadUserGroup(user.getId(), group.getId());
		if(userGroup == null){
			userGroup = new UserGroup();
			userGroup.setGroup(group);
			userGroup.setUser(user);
			this.getSession().save(userGroup);
		}else{
			new CmsException("用户组已经存在，不能添加");
		}
	}

	@Override
	public void deleteUserRole(Integer uid) {
		String hql = "delete UserRole ur where ur.user.id = ?";
		this.getSession().update(hql, uid);
	}

	@Override
	public void deleteUserGroup(Integer uid) {
		String hql = "delete UserGroup ug where ug.user.id = ?";
		this.getSession().update(hql, uid);
	}
	@Override
	public Pagination<User> findUser(){
		return this.find("from user");
	}

	@Override
	public void deleteUserGroup(Integer uid, Integer gid) {
		String hql ="delete UserGroup ug where ug.user.id = ? and ug.group.id = ?";
		this.getSession().update(hql, new Object[]{uid, gid});
	}

	@Override
	public void deleteUserRole(Integer uid, Integer rid) {
		String hql ="delete UserRole ur where ur.user.id = ? and ur.group.id = ?";
		this.getSession().update(hql, new Object[]{uid, rid});
	}
	

}
