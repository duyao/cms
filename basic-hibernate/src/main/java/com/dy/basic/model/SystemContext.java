package com.dy.basic.model;

/**
 * @author dy
 * 用来传递对象的ThreadLocal属性
 */
public class SystemContext {
	/**
	 * 分页大小
	 */
	private static ThreadLocal<Integer> pageSize;
	/**
	 * 分页的起始页
	 */
	private static ThreadLocal<Integer> pageOffset;
	/**
	 * 分页的排序方式，即按照所给字段
	 */
	private static ThreadLocal<String> sort;
	/**
	 * 分页的排序方法，有升序和降序
	 */
	private static ThreadLocal<String> order;
	
	public static Integer getPageSize() {
		return pageSize.get();
	}
	public static void setPageSize(Integer _pageSize) {
		pageSize.set(_pageSize);
	}
	public static Integer getPageOffset() {
		return pageOffset.get();
	}
	public static void setPageOffset(Integer _pageOffset) {
		pageOffset.set(_pageOffset);;
	}
	public static String getSort() {
		return sort.get();
	}
	public static void setSort(String _sort) {
		sort.set(_sort);
	}
	public static String getOrder() {
		return order.get();
	}
	public static void setOrder(String _order) {
		order.set(_order);
	}
	public static void removePageSize() {
		 pageSize.remove();
	}
	public static void removePageOffset() {
		 pageOffset.remove();
	}
	public static void removeSort() {
		 sort.remove();
	}
	public static void removeOrder() {
		 order.remove();
	}
	

}
