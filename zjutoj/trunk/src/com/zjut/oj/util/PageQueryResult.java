package com.zjut.oj.util;

import java.util.List;

@SuppressWarnings("rawtypes")
public class PageQueryResult {

	private long totalCount;

	private int pageSize;

	private int pageNo;

	private List list;

	public PageQueryResult() {
		pageNo = 1;
		pageSize = 30;
	}

	public long getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public List getList() {
		return list;
	}

	public void setList(List list) {
		this.list = list;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}
	
	public int getStartNum() {
		if(pageNo <= 0) {
			this.pageNo = 1;
		}
		return (pageNo - 1) * this.pageSize;
	}
	
	public int getEndNum() {
		return pageNo * pageSize;
	}
	
	public long getTotalPage() {
		if(totalCount == 0) {
			return 1;
		}
		return (this.totalCount / this.pageSize) + ((this.totalCount % this.pageSize) != 0 ? 1 : 0);
	}
}
