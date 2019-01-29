package com.ucmed.model.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.type.JdbcType;

import com.ucmed.model.bean.HospitalParam;


@Mapper
public interface HospitalParamMapper {
	
	@Select({
		"select id, name, remark, type, v, is_effect, descr from hospital_param ",
		"where name=#{n,jdbcType=VARCHAR} ",
		"and is_effect=#{ie,jdbcType=INTEGER}"
	})
	@Results({
		@Result(column="id", property="id", jdbcType=JdbcType.INTEGER),
		@Result(column="name", property="name", jdbcType=JdbcType.VARCHAR),
		@Result(column="remark", property="remark", jdbcType=JdbcType.VARCHAR),
		@Result(column="type", property="type", jdbcType=JdbcType.VARCHAR),
		@Result(column="v", property="v", jdbcType=JdbcType.VARCHAR),
		@Result(column="is_effect", property="isEffect", jdbcType=JdbcType.VARCHAR),
		@Result(column="descr", property="descr", jdbcType=JdbcType.VARCHAR)
	})
	HospitalParam getByName(@Param("n") String name, @Param("ie") int isEffect);

	@Select({
		"select id, name, remark, type, v, is_effect, descr from hospital_param ",
		"where type=#{t,jdbcType=VARCHAR} ",
		"and is_effect=#{ie,jdbcType=INTEGER}"
	})
	@Results({
		@Result(column="id", property="id", jdbcType=JdbcType.INTEGER),
		@Result(column="name", property="name", jdbcType=JdbcType.VARCHAR),
		@Result(column="remark", property="remark", jdbcType=JdbcType.VARCHAR),
		@Result(column="type", property="type", jdbcType=JdbcType.VARCHAR),
		@Result(column="v", property="v", jdbcType=JdbcType.VARCHAR),
		@Result(column="is_effect", property="isEffect", jdbcType=JdbcType.VARCHAR),
		@Result(column="descr", property="descr", jdbcType=JdbcType.VARCHAR)
	})
	List<HospitalParam> getByType(@Param("t") String type, @Param("ie") int isEffect);
}
