package com.dao;

import java.util.List;
import java.util.Map;

import com.dto.SchoolDto;

/**
 * 数据库查询BaseMapper
 * 
 * @author Administrator
 * 
 */
public interface BaseMapper {

	// 学校信息
	List<SchoolDto> selectSchools(String type);

	// 更多学校信息
	List<SchoolDto> getMore(Map<String, Object> map);

	int insertSchool(SchoolDto schoolDto);

	int deleteSchool(int id);

	int updateSchool(SchoolDto schoolDto);

	// 获取类型信息
	List<String> getTypes();

}
