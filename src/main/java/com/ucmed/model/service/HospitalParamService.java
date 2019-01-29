package com.ucmed.model.service;

import java.util.List;

import com.ucmed.model.bean.HospitalParam;

public interface HospitalParamService {
	public HospitalParam getByName(String name, int isEffect);
	public List<HospitalParam> getByType(String type, int isEffect);
}
