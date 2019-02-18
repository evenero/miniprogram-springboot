package com.ucmed.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ucmed.model.bean.pojo.ProgramConfig;
import com.ucmed.model.mapper.ProgramConfigMapper;


@Service
public class ProgramConfigServiceImpl implements ProgramConfigService {
	
	@Autowired
	private ProgramConfigMapper programConfigMapper;

	@Override
	public ProgramConfig selectByPrimaryKey(Integer id) {
		return programConfigMapper.selectByPrimaryKey(id);
	}
	@Override
	public int updateByPrimaryKeySelective(ProgramConfig record) {
		return programConfigMapper.updateByPrimaryKeySelective(record);
	}
	@Override
	public int deleteByPrimaryKey(Integer id) {
		return programConfigMapper.deleteByPrimaryKey(id);
	}
	@Override
	public int insert(ProgramConfig record) {
		return programConfigMapper.insert(record);
	}
	@Override
	public int insertSelective(ProgramConfig record) {
		return programConfigMapper.insertSelective(record);
	}
	@Override
	public int updateByPrimaryKey(ProgramConfig record) {
		return programConfigMapper.updateByPrimaryKey(record);
	}
	
	
	@Override
	public ProgramConfig selectConfigByNameHosid(String configname,
			String hospitalId) {
		return programConfigMapper.selectConfigByNameHosid(configname,
				hospitalId);
	}
	
	@Override
	public List<ProgramConfig> selectConfigPagination(String type, Integer pageStart, Integer pageSize) {
		return programConfigMapper.selectConfigPagination(type, pageStart, pageSize);
	}

	@Override
	public int selectConfigTotalCount(String type) {
		return programConfigMapper.selectConfigTotalCount(type);
	}
}
