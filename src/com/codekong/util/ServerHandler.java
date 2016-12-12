package com.codekong.util;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

/**
 * Created by szh on 2016/12/10.
 * 负责session对象的创建监听及消息发送和接收的监听
 */
public class ServerHandler extends IoHandlerAdapter{

    public ServerHandler(){
        super();
        System.err.println("handler");
    }
    @Override
    public void sessionCreated(IoSession session) throws Exception {
        // TODO Auto-generated method stub
        super.sessionCreated(session);
        System.out.println("sessionCreated");
    }

    @Override
    public void sessionOpened(IoSession session) throws Exception {
        // TODO Auto-generated method stub
        super.sessionOpened(session);
        //设置Session
        SessionManager.getInstance().setSession(session);
        SessionManager.getInstance().setAttributeValue("login", "false");
        System.out.println("sessionOpened");
    }

    /**
     * 消息的接收处理
     */
    @Override
    public void messageReceived(IoSession session, Object message)
            throws Exception {
        // TODO Auto-generated method stub
        super.messageReceived(session, message);
        System.out.println("messageReceived id = " + session.getId() + session.getRemoteAddress().toString());
        String[] msg;
        msg = parseCommand(message.toString());
        CommandHandler.handleCommand(msg);
    }

    /**
     * 消息的发送处理
     */
    @Override
    public void messageSent(IoSession session, Object message)
            throws Exception {
        // TODO Auto-generated method stub
        super.messageSent(session, message);
    }

    @Override
    public void sessionClosed(IoSession session) throws Exception {
        // TODO Auto-generated method stub
        super.sessionClosed(session);
    }

    @Override
    public void sessionIdle(IoSession session, IdleStatus status)
            throws Exception {
        // TODO Auto-generated method stub
        super.sessionIdle(session, status);
        System.out.println("sessionIdle");
    }

    @Override
    public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
        super.exceptionCaught(session, cause);
        SessionManager.getInstance().closeSession();
        SessionManager.getInstance().removeSessio();
    }

    /**
     * 解析收到的指令
     * @return
     */
    private String[] parseCommand(String command){
        String regx = ":";
        String[] res = command.split(regx);
        if (res.length != 0){
            return res;
        }
        return null;
    }
}
