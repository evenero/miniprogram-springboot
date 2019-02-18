package com.ucmed.model.service;

import java.util.List;

import com.ucmed.model.bean.pojo.ProgramConfig;

public interface ProgramConfigService {
	int deleteByPrimaryKey(Integer id);
	int insert(ProgramConfig record);
	int insertSelective(ProgramConfig record);
	int updateByPrimaryKeySelective(ProgramConfig record);
	int updateByPrimaryKey(ProgramConfig record);
	
	//自定义方法
	ProgramConfig selectByPrimaryKey(Integer id);
	ProgramConfig selectConfigByNameHosid(String configname, String hospitalId);
	List<ProgramConfig> selectConfigPagination(String type, Integer pageStart, Integer pageSize);
	int selectConfigTotalCount(String type);
}
