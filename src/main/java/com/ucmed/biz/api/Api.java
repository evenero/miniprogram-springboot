package com.ucmed.biz.api;

import net.sf.json.JSONObject;

/**
 * Created by MoLei on 2018/6/28.
 */
public interface Api {

    public static final String ERROR_MSG = "errorMsg";

    public static final String IS_SUCCESS = "isSuccess";

    public JSONObject execute(JSONObject params);
}
