package com.ucmed.model.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ucmed.model.bean.pojo.ProgramConfig;

@Mapper
public interface ProgramConfigMapper {
	int deleteByPrimaryKey(Integer id);
	int insert(ProgramConfig record);
	int insertSelective(ProgramConfig record);
	ProgramConfig selectByPrimaryKey(Integer id);
	int updateByPrimaryKeySelective(ProgramConfig record);
	int updateByPrimaryKey(ProgramConfig record);
	
	/**
	 * 通过配置名称和医院id获取数据
	 * @param configname
	 * @param hospitalId
	 * @return
	 */
	ProgramConfig selectConfigByNameHosid(String configname,String hospitalId);

	/**
	 * 分页查询数据
	 * @param type
	 * @param pageStart
	 * @param pageSize
	 * @return
	 */
	List<ProgramConfig> selectConfigPagination(@Param("type") String type, @Param("pageStart") Integer pageStart,
			@Param("pageSize") Integer pageSize);

	/**
	 * 获取数据总数（分页用）
	 * @param type
	 * @return
	 */
	int selectConfigTotalCount(@Param("type") String type);
}