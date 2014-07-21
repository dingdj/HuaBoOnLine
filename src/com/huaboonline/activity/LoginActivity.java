package com.huaboonline.activity;

import android.os.Bundle;
import android.os.Handler;
import com.android.huaboonline.R;
import com.huaboonline.AppContext;
import com.huaboonline.api.ApiClient;
import com.huaboonline.util.RequestResponseDataParseUtil;
import com.huaboonline.util.ThreadUtil;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 用户登录Activity
 * Created by dingdj on 2014/7/20.
 */
public class LoginActivity extends BaseActivity{

    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        final String phoneNum = "18950295115";
        ThreadUtil.execute(new Runnable(){
            @Override
            public void run() {
                JSONObject jsonObject = ApiClient.getCompanyList(AppContext.appContext, phoneNum);
                if(jsonObject == null) {
                    //未知异常处理
                }else {
                    try {
                        int code = (Integer)jsonObject.get(RequestResponseDataParseUtil.RETURN_CODE);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });


    }




}
