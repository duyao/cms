package com.dy.cms.dao;

import java.util.List;

import com.dy.basic.dao.BaseDao;
import com.dy.cms.model.Role;

public class RoleDao extends BaseDao<Role> implements IRoleDao {

	

	@Override
	public List<Role> listRole() {
		return this.list("from role");
	}

	@Override
	public void deleteRoleUsers(int rid) {
		this.updateByHql("delete UseRole ur where ur.role.id=?",rid);
	}

}
