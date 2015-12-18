package com.dy.basic.model;

import java.util.List;


/**
 * @author dy
 * 分页对象
 */
public class Pagination<T> {
	/**
	 * 分页大小
	 */
	private int size;
	
	/**
	 * 起始页
	 */
	private int offset;
	/**
	 * 总页数
	 */
	private int total;
	/**
	 * 分页内容
	 */
	private List<T> data;
	

}
