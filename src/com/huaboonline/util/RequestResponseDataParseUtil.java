package com.huaboonline.util;

import com.android.huaboonline.R;
import com.ddj.commonkit.DateUtil;
import com.ddj.commonkit.StringUtils;
import com.huaboonline.AppContext;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 请求体和返回体解析帮助类
 * Created by dingdj on 2014/7/20.
 */
public class RequestResponseDataParseUtil {

    public static final int RESPONSE_OK = 200;
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


    /**
     * 获取请求体的Json模板
     *
     * @return
     */
    public static JSONObject getRequestJsonObjectTemplate() {
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

    /**
     * 处理服务端返回数据通用类
     */
    public static abstract class ResponseParse {

        public void onResponse(JSONObject response) {
            try {
                JSONObject headObject = response.getJSONObject(RequestResponseDataParseUtil.HEAD_SECTION);
                int code = headObject.getInt(RequestResponseDataParseUtil.HEAD_SECTION_CODE);

                switch (code) {
                    case RequestResponseDataParseUtil.RESPONSE_OK: //200
                        JSONObject dataJsonObject = response.getJSONObject(RequestResponseDataParseUtil.DATA_SECTION);
                        parseResponseDataSection(dataJsonObject);
                        break;
                    case RequestResponseDataParseUtil.UNLOGIN: //用户未登录
                        actionUserUnLogin(headObject);
                        break;
                    case RequestResponseDataParseUtil.USER_UNEXIST: //用户不存在
                       actionUserUnLogin(headObject);
                        break;
                    case RequestResponseDataParseUtil.OLD_PASSWORD_ERROR: //旧密码错误
                      actionOldPasswdWrong(headObject);
                        break;
                    case RequestResponseDataParseUtil.USER_HAULT: //用户被停用
                        actionUserHalt(headObject);
                        break;
                    case RequestResponseDataParseUtil.COURSE_EXIST: //课程已存在
                        actionCourseExist(headObject);
                        break;
                    case RequestResponseDataParseUtil.COURSE_NEED_PRIVILAGE: //只能选择一门
                        actionOnlySelectOneCourse(headObject);
                        break;
                    case RequestResponseDataParseUtil.EXAM_UN_EXIST: //考试不存在
                        actionExamNonExist(headObject);
                        break;

                    default: //500错误
                        UIHelper.getServerErrorDialog(AppContext.appContext).show();
                        break;
                }
            } catch (JSONException e) {
                e.printStackTrace();
                //未知异常
            }
        }

        /**
         * 处理服务端返回数据data部分
         * @param dataJsonObject
         */
        public abstract void parseResponseDataSection(JSONObject dataJsonObject);


        /**
         * 用户未登录
         * @param headObject
         */
        public void actionUserUnLogin(JSONObject headObject) {
            actionWhenBusinessError(headObject, R.string.unlogin);
        }


        /**
         * 用户不存在
         * @param headObject
         */
        public void actionUserNonExist(JSONObject headObject) {
            actionWhenBusinessError(headObject, R.string.user_non_exist);
        }



        /**
         * 旧密码错误
         * @param headObject
         */
        public void actionOldPasswdWrong(JSONObject headObject) {
            actionWhenBusinessError(headObject, R.string.old_passwd_wrong);
        }


        /**
         * 用户被停用
         * @param headObject
         */
        public void actionUserHalt(JSONObject headObject) {
            actionWhenBusinessError(headObject, R.string.user_halt);
        }


        /**
         * 课程已存在
         * @param headObject
         */
        public void actionCourseExist(JSONObject headObject) {
            actionWhenBusinessError(headObject, R.string.course_exist);
        }


        /**
         * 试用商户只能选一个课程
         * @param headObject
         */
        public void actionOnlySelectOneCourse(JSONObject headObject) {
            actionWhenBusinessError(headObject, R.string.only_one_course_select);
        }


        /**
         * 试卷不存在
         * @param headObject
         */
        public void actionExamNonExist(JSONObject headObject) {
            actionWhenBusinessError(headObject, R.string.exam_no_exist);
        }




        /**
         * 处理普通的业务逻辑错误
         * @param headObject
         * @param resId
         */
        public void actionWhenBusinessError(JSONObject headObject, int resId) {
            try {
                String message = headObject.getString(RequestResponseDataParseUtil.HEAD_SECTION_MESSAGE);
                if(!StringUtils.isNotEmpty(message)) {
                    message = AppContext.appContext.getString(resId);
                }
                UIHelper.getBusinessMessageDialog(AppContext.appContext, message).show();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


}

