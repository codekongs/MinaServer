package com.codekong.config;

/**
 * Created by szh on 2016/12/11.
 * 自定义的通信指令
 */
public class CommunicationCommand {
    //主动登录指令
    public static final String ACTIVE_LOGIN = "active_login";
    //被动登录指令
    public static final String PASSIVE_LOGIN = "passive_login";
    //用户鉴权状态码
    public static final String STATUS_CODE = "status_code";
    //打洞请求
    public static final String HOLE_PUNCHING = "hole_punching";
    //辅助打洞端口
    public static final String ASSIST_PORT = "random_id";
    //随机数id
    public static final String RANDOM_ID = "random_id";
    //对方信息
    public static final String OTCL_ADDR = "otcl_addr";


    //状态码定义
    //成功
    public static final String STATUS_CODE_SUCC = "100";
    //失败
    public static final String STATUS_CODE_FAIL = "-404";
}
