package com.huaboonline.activity;

import android.os.Bundle;
import com.android.huaboonline.R;
import com.android.volley.*;
import com.android.volley.toolbox.JsonObjectRequest;
import com.huaboonline.AppConfig;
import com.huaboonline.AppContext;
import com.huaboonline.beans.URLs;
import com.huaboonline.util.RequestResponseDataParseUtil;
import com.huaboonline.util.UIHelper;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.MathContext;
import java.util.HashMap;
import java.util.Map;

/**
 * 用户登录Activity
 * Created by dingdj on 2014/7/20.
 */
public class LoginActivity extends BaseActivity{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        String phoneNum = "18950295115";
        requestCompanyList(phoneNum);
    }


    /**
     * 发起請求
     */
    private void requestCompanyList(final String phoneNum) {
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, URLs.GET_ENTERPRISE_INFO, null,
                new Response.Listener<JSONObject>() {
                    /**
                     * Called when a response is received.
                     *
                     * @param response
                     */
                    @Override
                    public void onResponse(JSONObject response) {


                    }
                }, new Response.ErrorListener() {
            /**
             * Callback method that an error has been occurred with the
             * provided error code and optional user-readable message.
             *
             * @param error
             */
            @Override
            public void onErrorResponse(VolleyError error) {
                UIHelper.showErrorDialog(LoginActivity.this, error);
            }
        }) {
            /**
             * Returns a Map of parameters to be used for a POST or PUT request.  Can throw
             * {@link com.android.volley.AuthFailureError} as authentication may be required to provide these values.
             * <p/>
             * <p>Note that you can directly override {@link #getBody()} for custom data.</p>
             *
             * @throws com.android.volley.AuthFailureError in the event of auth failure
             */
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> paramMap = new HashMap<String, String>();
                JSONObject jsonRequest = RequestResponseDataParseUtil.getRequestJsonObjectTemplate();
                JSONObject dataObject = new JSONObject();
                try {
                    jsonRequest.put(RequestResponseDataParseUtil.DATA_SECTION, dataObject);
                    dataObject.put("phoneNumber", phoneNum);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                paramMap.put(RequestResponseDataParseUtil.POST_SECTION_NAME, jsonRequest.toString());
                return paramMap;
            }
        };

        AppContext.appContext.getRequestQueue().add(request);
    }


}
