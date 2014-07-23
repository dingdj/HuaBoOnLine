package com.huaboonline.beans;

/**
 * url管理类
 * Created by dingdj on 2014/7/20.
 */
public class URLs {

    public final static String HOST = "www.oschina.net";//192.168.1.213
    public final static String HTTP = "http://";
    public final static String HTTPS = "https://";

    private final static String URL_SPLITTER = "/";
    private final static String URL_UNDERLINE = "_";

    private final static String URL_API_HOST = HTTP + HOST + URL_SPLITTER;

    //获取公司列表
    public final static String GET_ENTERPRISE_INFO = HTTP + HOST + URL_SPLITTER + "web/mobile/login/0/getCompanyList";


    //用户登录
    public final static String USER_LOGIN= HTTP + HOST + URL_SPLITTER + "web/mobile/login/0/getCompanyList";

}
