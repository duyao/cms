package com.dy.cms.dao;

import java.util.List;

import com.dy.basic.dao.IBaseDao;
import com.dy.basic.model.Pagination;
import com.dy.cms.model.Group;

public interface IGroupDao extends IBaseDao<Group> {
	public List<Group> listGroup();
	public Pagination<Group> findGroup();
	public void deleteGroupUsers(int gid);

}
