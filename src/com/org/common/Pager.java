package com.org.common;

public class Pager {
	private Integer currentPage; // 当前页码
	private Integer totalCount; // 符合记录的总数
	private Integer totalPages; // 总页数
	private Integer begin; // 从begin开始显示页码
	private Integer end; // 页码显示结束到end
	private Integer prevPage; // 前一页的页码
	private Integer nextPage; // 后一页的页码
	private Integer firstPage = 1; // 第一页
	private Integer lastPage; // 最后一页码
	private Integer pageCount = 10; // 每页显示数量
	
	private final Integer UNIT = 3; // 页码显示的跨度

	public Integer getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}

	public Integer getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}

	public Integer getTotalPages() {
		if(totalPages == null) {
			totalPages = (totalCount % pageCount == 0) ? (totalCount / pageCount) : ((totalCount / pageCount) + 1); 
		}
		return totalPages;
	}

	public Integer getBegin() {
		begin = currentPage - UNIT;
		begin = (begin) <= 1 ? 1 : (begin);
		return begin;
	}

	public Integer getEnd() {
		end = currentPage + UNIT;
		end = end >= (getTotalPages()-1) ? (getTotalPages()-1) : end;
		return end;
	}

	public Integer getPrevPage() {
		prevPage = currentPage - 1;
		return prevPage;
	}

	public Integer getNextPage() {
		nextPage = currentPage + 1;
		return nextPage;
	}

	public Integer getFirstPage() {
		return firstPage;
	}

	public Integer getLastPage() {
		lastPage = totalPages;
		return lastPage;
	}

	public Integer getPageCount() {
		return pageCount;
	}

	public void setPageCount(Integer pageCount) {
		this.pageCount = pageCount;
	}

}
