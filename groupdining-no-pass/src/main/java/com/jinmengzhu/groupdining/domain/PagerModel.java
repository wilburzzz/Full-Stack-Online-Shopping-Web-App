package com.jinmengzhu.groupdining.domain;

import java.util.List;

public class PagerModel<T> {
	
	private Long total;//总记录数
	
	private List<T> rows;//当前页的数据
	
	private int pageSize = 10;//每页显示数据条数
	
	private int pageNumber = 1;//当前第几页
	
	private int pages = 1; // 总页数
	
	private boolean isFirstPage = false; // 是否为第一页
	
	private boolean isLastPage = false; // 是否为最后一页
	
	public String sort; //排序字段名

	public String order; //升序或降序
	
	private String[] orderByProperties = null;
	
	private boolean[] isAscs = null;
	
	public List<T> getRows() {
		return rows;
	}

	public void setRows(List<T> rows) {
		if(total == null)total = 0l;
		int pages = (int)((total - 1) / pageSize + 1);
		this.pages = pages;
		if(pageNumber == 1){
			isFirstPage = true;
		}
		
		if(pageNumber == pages){
			isLastPage = true;
		}
		this.rows = rows;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		if(pageSize <= 0){
			pageSize = 10;
		}
		this.pageSize = pageSize;
	}

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		if(pageNumber < 0){
			pageNumber = 1;
		}
		this.pageNumber = pageNumber;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}

	public int getPages() {
		return pages;
	}

	public void setPages(int pages) {
		this.pages = pages;
	}

	public boolean isFirstPage() {
		return isFirstPage;
	}

	public void setFirstPage(boolean isFirstPage) {
		this.isFirstPage = isFirstPage;
	}

	public boolean isLastPage() {
		return isLastPage;
	}

	public void setLastPage(boolean isLastPage) {
		this.isLastPage = isLastPage;
	}

	public String[] getOrderByProperties() {
		return orderByProperties;
	}

	public void setOrderByProperties(String[] orderByProperties) {
		this.orderByProperties = orderByProperties;
	}

	public boolean[] getIsAscs() {
		return isAscs;
	}

	public void setIsAscs(boolean[] isAscs) {
		this.isAscs = isAscs;
	}
	
	public static void main(String[] args) {
		int size = 5;
		int pageSize = 10;
		System.out.println((size - 1) / pageSize + 1);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void clonePage(PagerModel respsPage1,
			PagerModel page1Data) {
		page1Data.setPageNumber(respsPage1.getPageNumber());
		page1Data.setPages(respsPage1.getPages());
		page1Data.setPageSize(respsPage1.getPageSize());
		page1Data.setRows(respsPage1.getRows());
		page1Data.setTotal(respsPage1.getTotal());
	}
	
}
