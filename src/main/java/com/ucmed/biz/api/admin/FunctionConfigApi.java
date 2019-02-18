package com.ucmed.biz.api.admin;

import java.util.Date;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ucmed.biz.api.Api;
import com.ucmed.model.bean.ProgramConfig;
import com.ucmed.model.service.ProgramConfigService;

import net.sf.json.JSONObject;

@Component
public class FunctionConfigApi implements Api{
	private static final Logger LOG = LoggerFactory.getLogger(FunctionConfigApi.class);
	
	@Autowired
	private ProgramConfigService configService;

	@Override
	public JSONObject execute(JSONObject params) {
		JSONObject res = new JSONObject();
		int code = 1;
		String info = "";
		try {
			String type = params.optString("type","list");
			if("list".equals(type)) {
				String functionType = params.optString("function_type","0");
				int pageIndex = params.optInt("pageIndex",1);
				int pageSize = params.optInt("pageSize",10);
				Integer pageStart = (pageIndex-1)*pageSize;
				List<ProgramConfig> list = configService.selectConfigPagination(functionType, pageStart, pageSize);
				int totalCount = configService.selectConfigTotalCount(functionType);
				res.put("list", list);
				res.put("totalCount", totalCount);
				code = 0;
				info = "查询成功";
			}else if("edit".equals(type)){
				Integer id = params.optInt("id");
				String value = params.optString("value");
				String note = params.optString("note");
				ProgramConfig record = new ProgramConfig();
				record.setId(id);
				if(null!=value&&!"".equals(value.trim())) {
					record.setConfigValue(value);
				}
				if(null!=note&&!"".equals(note.trim())) {
					record.setNote(note);
				}
				record.setUpdateTime(new Date());
				int i = configService.updateByPrimaryKeySelective(record);
				if(i>0) {
					code = 0;
					info = "数据更新成功";
				}else {
					info = "数据更新失败";
				}
			}
		} catch (Exception e) {
			LOG.info("",e);
		}
		res.put("ret_code", code);
		res.put("ret_info", info);
		return res;
	}

}
