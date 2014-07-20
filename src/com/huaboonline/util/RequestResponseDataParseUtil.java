package com.huaboonline.util;

import com.ddj.commonkit.DateUtil;
import com.huaboonline.AppContext;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 请求体和返回体解析帮助类
 * Created by dingdj on 2014/7/20.
 */
public class RequestResponseDataParseUtil {

    private static final String HEAD_SECTION = "head";
    private static final String APP_VERSION_KEY = "appVersion";
    private static final String APP_PLATFORM_OS = "osPlatform";
    private static final String REQUEST_TIME = "requestTime";
    private static final String DATA_SECTION = "data";



    /**
     * 获取请求体的Json模板
     * @return
     */
    public static JSONObject getRequestJsonObjectTemplate(){
        JSONObject jsonObject = new JSONObject();
        JSONObject headJsonObject = new JSONObject();
        try {
            headJsonObject.put(APP_VERSION_KEY, AppContext.appVersion);
            headJsonObject.put(APP_PLATFORM_OS, AppContext.osPlatform);
            headJsonObject.put(REQUEST_TIME, DateUtil.getStringDateByFormat("yyyy-MM-dd HH:mm:ss"));
            jsonObject.put(HEAD_SECTION, headJsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }



}
