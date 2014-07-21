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

    public static final int NETWORK_ERROR = 100;
    public static final int APP_SERVER_ERROR = 101;
    public static final int REQUEST_SUCCESS = 102;
    public static final int UNLOGIN = 601;
    public static final int USER_UNEXIST = 602;
    public static final int OLD_PASSWORD_ERROR = 603;
    public static final int USER_HAULT = 604;
    public static final int COURSE_EXIST = 605;
    public static final int COURSE_NEED_PRIVILAGE = 606;
    public static final int EXAM_UN_EXIST = 607;

    public static final String HEAD_SECTION = "head";
    public static final String HEAD_SECTION_CODE = "code";
    public static final String HEAD_SECTION_MESSAGE = "message";
    public static final String APP_VERSION_KEY = "appVersion";
    public static final String APP_PLATFORM_OS = "osPlatform";
    public static final String REQUEST_TIME = "requestTime";
    public static final String DATA_SECTION = "data";
    public static final String POST_SECTION_NAME = "requestData";
    public static final String RETURN_CODE = "returnCode";
    public static final String RETURN_RESULT = "returnResult";



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
