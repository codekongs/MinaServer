package com.codekong.util;

import org.apache.mina.core.session.IoSession;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by szh on 2016/12/11
 * 存储创建的Session
 */
public class SessionList {
    //所有登录到服务器的设备
    private static List<IoSession> allOnlineSessionList = new ArrayList<>();
    public static void addSession(IoSession ioSession){
        allOnlineSessionList.add(ioSession);
    }

    public static IoSession getIoSession(long id){
        IoSession ioSession = null;
        for (IoSession session : allOnlineSessionList) {
            if (session.getId() == id){
                ioSession = session;
                break;
            }
        }
        return ioSession;
    }
}
