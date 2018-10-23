package util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PaginationUtil {

	@SuppressWarnings("rawtypes")
	public static List results = new ArrayList();

	private static final Integer PAGE_SIZE = 20;

	@SuppressWarnings("rawtypes")
	public static Map<String, Object> getResults(int currentPage) {
		Map<String, Object> result = new HashMap<String, Object>();
		// 总记录数
		Integer totalCount = results.size();
		result.put("totalCount", totalCount);
		// 总页数
		Integer totalpage = 0;
		if (totalCount % PAGE_SIZE == 0) {
			totalpage = totalCount / PAGE_SIZE;
		} else {
			totalpage = totalCount / PAGE_SIZE + 1;
		}
		result.put("totalpage", totalpage);
		// 获取起始数
		Integer fromIndex = (currentPage - 1) * PAGE_SIZE;
		// 结尾数
		Integer toIndex = currentPage * PAGE_SIZE;
		if (toIndex > totalCount) {
			toIndex = totalCount;
		}
		List subList = results.subList(fromIndex, toIndex);
		result.put("result", subList);
		return result;
	}

	public static void main(String[] args) {
		List<Long> datas = Arrays.asList(new Long[] { 1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L });
		PaginationUtil.results = datas;
		PaginationUtil.getResults(3);
	}
}
