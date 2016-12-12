package com.codekong.util;

import org.apache.mina.core.session.IoSession;

/**
 * Session管理
 */

public class SessionManager {
    private static SessionManager mInstance = null;
    private IoSession mSession;

    private SessionManager(){}
    /**
     * 单例模式
     * 最终与服务器进行通信的对象
     * @return
     */
    public static SessionManager getInstance(){
        if (mInstance == null){
            synchronized (SessionManager.class){
                if (mInstance == null){
                    mInstance = new SessionManager();
                }
            }
        }
        return mInstance;
    }

    /**
     * 设置session对象
     * @param session
     */
    public void setSession(IoSession session){
        this.mSession = session;
        System.err.println("set session" + mSession.getId());
    }

    public IoSession getSession(){
        if (mSession != null){
            return mSession;
        }else {
            return null;
        }
    }

    public void setAttributeValue(String key, String value){
        if (mSession != null){
            mSession.setAttribute(key, value);
        }
    }

    public String getAttributeValue(String key){
        if (mSession != null){
            return (String) mSession.getAttribute(key);
        }
        return null;
    }

    /**
     * 向服务器写数据
     * @param msg
     */
    public void writeToServer(String msg){
        if (mSession != null){
            mSession.write(msg);
            System.err.println("writing" + mSession.getId());
        }
    }

    /**
     * 关闭session
     */
    public void closeSession(){
        if (mSession != null){
            mSession.closeOnFlush();
        }
    }

    /**
     * 移除session
     */
    public void removeSessio(){
        this.mSession = null;
    }
}
