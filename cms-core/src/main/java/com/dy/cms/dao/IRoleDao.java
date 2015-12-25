package com.dy.cms.dao;

import java.util.List;

import com.dy.basic.dao.IBaseDao;
import com.dy.cms.model.Role;

public interface IRoleDao extends IBaseDao<Role> {
	public List<Role> listRole();
	public void deleteRoleUsers(int rid);
}
