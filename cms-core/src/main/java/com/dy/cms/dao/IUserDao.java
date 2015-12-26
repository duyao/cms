package com.dy.cms.dao;

import java.util.List;

import com.dy.basic.dao.IBaseDao;
import com.dy.basic.model.Pagination;
import com.dy.cms.model.Group;
import com.dy.cms.model.Role;
import com.dy.cms.model.RoleType;
import com.dy.cms.model.User;
import com.dy.cms.model.UserGroup;
import com.dy.cms.model.UserRole;

public interface IUserDao extends IBaseDao<User> {
	/**
	 * 获取用户的所有角色信息
	 * @param userId
	 * @return
	 */
	public List<Role> listUserRoles(int userId);
	/**
	 * 获取用户的所有角色的id
	 * @param userId
	 * @return
	 */
	public List<Integer> listUserRoleIds(int userId);
	/**
	 * 获取用户的所有组信息
	 * @param userId
	 * @return
	 */
	public List<Group> listUserGroups(int userId);
	/**
	 * 获取用户的所有组的id
	 * @param userId
	 * @return
	 */
	public List<Integer> listUserGroupIds(int userId);
	/**
	 * 根据用户和角色获取用户角色的关联对象
	 * @param userId
	 * @param roleId
	 * @return
	 */
	public UserRole loadUserRole(int userId,int roleId);
	/**
	 * 根据用户和组获取用户组关联对象
	 * @param userId
	 * @param groupId
	 * @return
	 */
	public UserGroup loadUserGroup(int userId,int groupId);
	/**
	 * 根据用户名获取用户对象
	 * @param username
	 * @return
	 */
	public User loadByUsername(String username);
	/**
	 * 根据角色id获取用户列表
	 * @param roleId
	 * @return
	 */
	public List<User> listRoleUsers(int roleId);
	/**
	 * 根据角色类型获取用户对象
	 * @param roleType
	 * @return
	 */
	public List<User> listRoleUsers(RoleType roleType);
	/**
	 * 获取某个组中的用户对象
	 * @param gid
	 * @return
	 */
	public List<User> listGroupUsers(int gid);
	
	/**
	 * 添加用户角色
	 * @param user
	 * @param role
	 */
	public void addUserRole(User user, Role role);
	/**
	 * 添加用户组
	 * @param user
	 * @param group
	 */
	public void addUsergroup(User user, Group group);
	

}
