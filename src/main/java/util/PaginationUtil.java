package util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PaginationUtil<T> {
	private int pageSize = 20; // 每页显示条数
	private int totalCount; // 总条数
	private int totalPages; // 总页数
	private List<T> pageList;// 数据

	public PaginationUtil(List<T> pageList) {
		this.totalCount = pageList.size();
		// 计算总页数
		this.totalPages = this.totalCount / this.pageSize;
		if (this.totalCount % this.pageSize != 0) {
			this.totalPages++;
		}
		this.pageList = pageList;
	}

	public Map<String, Object> subList(int pageNo) {
		Map<String, Object> map = new HashMap<String, Object>();
		int start = (pageNo - 1) * this.pageSize;
		int end = pageNo * this.pageSize;
		if (end > this.totalCount) {
			end = this.totalCount;
		}
		map.put("result", pageList.subList(start, end));
		map.put("totalpage", this.totalPages);
		return map;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public int getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}

	public List<T> getPageList() {
		return pageList;
	}

	public void setPageList(List<T> pageList) {
		this.pageList = pageList;
	}

}