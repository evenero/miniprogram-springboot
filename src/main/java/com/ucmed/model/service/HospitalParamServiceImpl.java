package com.ucmed.model.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ucmed.model.bean.HospitalParam;
import com.ucmed.model.mapper.HospitalParamMapper;

@Service
public class HospitalParamServiceImpl implements HospitalParamService {
	
	private HospitalParamMapper hospitalParamMapper;
	public void setHospitalParamMapper(HospitalParamMapper hospitalParamMapper) {
		this.hospitalParamMapper = hospitalParamMapper;
	}

	@Override
	public HospitalParam getByName(String name, int isEffect) {
		return hospitalParamMapper.getByName(name, isEffect);
	}

	@Override
	public List<HospitalParam> getByType(String type, int isEffect) {
		return hospitalParamMapper.getByType(type, isEffect);
	}

}
