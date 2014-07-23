package com.huaboonline.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.android.huaboonline.R;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.ddj.commonkit.android.ActivityUtil;
import com.huaboonline.AppConfig;
import com.huaboonline.AppContext;
import com.huaboonline.api.JsonCookieSupportRequest;
import com.huaboonline.beans.URLs;
import com.huaboonline.util.RequestResponseDataParseUtil;
import com.huaboonline.util.UIHelper;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * 用户登录Activity
 * Created by dingdj on 2014/7/20.
 */
public class LoginActivity extends BaseActivity implements AdapterView.OnItemClickListener {

    ListView listView;
    EnterpriseAdapter adapter;
    CheckBox auto_login_checkBox;
    String phoneNum = "18950295115";
    int curCompanyId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        listView = (ListView) findViewById(R.id.enterprise_list);
        listView.setOnItemClickListener(this);

        adapter = new EnterpriseAdapter();
        listView.setAdapter(adapter);

        auto_login_checkBox = (CheckBox) findViewById(R.id.check_enterprise);
    }


    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences sharedPreferences = AppConfig.getSharedPreferences(this);
        if (sharedPreferences.getBoolean(AppConfig.AUTO_LOGIN, false)) { //用户已选定自动登陆
            int enterPriseId = sharedPreferences.getInt(AppConfig.ENTERPRISE_ID, -1);
            if (enterPriseId != -1) {
                requestLogin(enterPriseId, phoneNum);
                return;
            }
        }
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
                        new RequestResponseDataParseUtil.ResponseParse() {
                            @Override
                            public void parseResponseDataSection(JSONObject dataJsonObject) {//处理正常的业务逻辑
                                try {
                                    JSONArray jsonArray = dataJsonObject.getJSONArray("companyList");
                                    int length = jsonArray.length();
                                    int[] ids = new int[length];
                                    String[] names = new String[length];
                                    for (int i = 0; i < length; i++) {
                                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                                        ids[i] = jsonObject.getInt("Id");
                                        names[i] = jsonObject.getString("Name");
                                    }

                                    adapter.setIds(ids);
                                    adapter.setNames(names);
                                    //通知list进行刷新
                                    adapter.notifyDataSetChanged();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }.onResponse(response);

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
                UIHelper.showErrorDialog(AppContext.appContext, error);
            }
        }) {

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


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        if (view instanceof TextView) {
            curCompanyId = (Integer) view.getTag();
            requestLogin(curCompanyId, phoneNum);
        }
    }

    class EnterpriseAdapter extends BaseAdapter {

        private int[] ids;
        private String[] names;

        @Override
        public int getCount() {
            if (ids != null) {
                return ids.length;
            }
            return 0;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {

            try {
                if (view != null) {
                    TextView textView = (TextView) view;
                    textView.setText(names[i]);
                    textView.setTag(ids[i]);
                    return textView;
                } else {
                    TextView textView = new TextView(LoginActivity.this);
                    textView.setText(names[i]);
                    textView.setTag(ids[i]);
                    return textView;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        public void setIds(int[] ids) {
            this.ids = ids;
        }

        public void setNames(String[] names) {
            this.names = names;
        }
    }

    /**
     * 请求登录
     *
     * @param id
     * @param phoneNum
     */
    private void requestLogin(int id, String phoneNum) {
        //删除原有的Cookie
        AppConfig.getInstance().clearCookie();
        JsonCookieSupportRequest request = new JsonCookieSupportRequest(Request.Method.POST, URLs.USER_LOGIN, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        new RequestResponseDataParseUtil.ResponseParse() {
                            @Override
                            public void parseResponseDataSection(JSONObject dataJsonObject) {//登录成功处理
                                try {
                                    //只有在登陆成功后，下次自动登陆才生效
                                    if (auto_login_checkBox.isChecked()) {
                                        SharedPreferences sharedPreferences = AppConfig.getSharedPreferences(AppContext.appContext);
                                        sharedPreferences.edit().putBoolean(AppConfig.AUTO_LOGIN, true).commit();
                                        sharedPreferences.edit().putInt(AppConfig.ENTERPRISE_ID, curCompanyId).commit();
                                    }

                                    //转向欢迎页面
                                    LoginActivity.this.finish();
                                    ActivityUtil.startActivityByClassNameSafe(AppContext.appContext, ReadMeActivity.class.getName());
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }.onResponse(jsonObject);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        UIHelper.showErrorDialog(AppContext.appContext, volleyError);
                    }
                });

        AppContext.appContext.getRequestQueue().add(request);
    }
}
