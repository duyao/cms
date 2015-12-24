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
	 * hibernate中count(*)返回值是long
	 */
	private long total;
	/**
	 * 分页内容
	 */
	private List<T> data;
	
	
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	public int getOffset() {
		return offset;
	}
	public void setOffset(int offset) {
		this.offset = offset;
	}
	public long getTotal() {
		return total;
	}
	public void setTotal(long total) {
		this.total = total;
	}
	public List<T> getData() {
		return data;
	}
	public void setData(List<T> data) {
		this.data = data;
	}
	

}