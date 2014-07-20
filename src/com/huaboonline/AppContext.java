package com.huaboonline;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import com.android.huaboonline.R;
import com.ddj.commonkit.android.system.SystemEnvUtil;
import com.ddj.commonkit.android.system.TelephoneUtil;

/**
 * Created by dingdj on 2014/7/20.
 */
public class AppContext extends Application{

    public static final int PAGE_SIZE = 10;//默认分页大小
    private boolean login = false;	//登录状态

    //系统版本
    public static String osPlatform;
    //app版本
    public static String appVersion;
    //用户手机号
    private String phoneNum = "";

    @Override
    public void onCreate() {
        super.onCreate();
        //注册App异常崩溃处理器
        Thread.setDefaultUncaughtExceptionHandler(AppException.getAppExceptionHandler());

        //一些初始化动作
        init();
    }

    /**
     * 初始化动作
     */
    private void init(){
        osPlatform = "Android_"+  Build.VERSION.SDK;
        appVersion = this.getResources().getString(R.string.app_version);
    }


    /**
     * 获取App安装包信息
     * @return
     */
    public PackageInfo getPackageInfo() {
        PackageInfo info = null;
        try {
            info = getPackageManager().getPackageInfo(getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace(System.err);
        }
        if(info == null) info = new PackageInfo();
        return info;
    }


    public String getOsPlatform() {
        return osPlatform;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }
}
