package com.ucmed.model.utils;

import java.io.PrintWriter;
import java.io.StringWriter;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class HnsrmyyUtil {

	public static Boolean getCardList(String type, String identify, String card) {
		JSONObject params = new JSONObject();
		params.put("type", type);
		params.put("identify", identify);
//		JSONObject res = HnsrmyyApi.getInstance().requestActionParams(
//				"api.hnsrmyy.unite.card.list", params, null);
		String aString = "{\"return_code\":0,\"return_params\":{\"ret_code\":0,\"count\":2,\"result\":[{\"id\":1522348,\"name\":\"孙沐辰\",\"card\":\"430522201501170267\",\"idCard\":\"430522201501170267\"},{\"id\":1522348,\"name\":\"王沐辰\",\"card\":\"430522222201170267\",\"idCard\":\"430522201501170267\"},{\"id\":1522173,\"name\":\"孙逸琳\",\"card\":\"430522198701120024\",\"idCard\":\"430522198701120024\"}]}}";
		JSONObject res = JSONObject.fromObject(aString);
		JSONObject obj = res.optJSONObject("return_params");
		if (obj.optInt("ret_code") == 0) {
			JSONArray result = obj.optJSONArray("result");
			for (Object object : result) {
				JSONObject obj1 = (JSONObject) object;
				String cardres = obj1.optString("card");
				if (card.equals(cardres)) {
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * 输出错误日志
	 * @param e
	 * @return
	 */
	public static String printErrInfo(Throwable e)
	  {
	    StringWriter sw = new StringWriter();
	    PrintWriter pw = new PrintWriter(sw, true);
	    e.printStackTrace(pw);
	    pw.flush();
	    sw.flush();
	    return sw.toString();
	  }
}
