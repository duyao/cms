package com.dy.cms.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.dy.basic.dao.BaseDao;
import com.dy.basic.model.Pagination;
import com.dy.cms.model.Group;

@Repository("groupDao")
public class GroupDao extends BaseDao<Group> implements IGroupDao {

	

	@Override
	public List<Group> listGroup() {
		return this.list("from Group");
	}

	@Override
	public Pagination<Group> findGroup() {
		return this.find("from group");
	}

	@Override
	public void deleteGroupUsers(int gid) {
		this.updateByHql("delete UserGroup ug where ug.group.id=?",gid);
	}

}
