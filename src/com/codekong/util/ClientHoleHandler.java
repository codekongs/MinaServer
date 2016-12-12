package com.codekong.util;

import com.codekong.config.CommunicationCommand;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

/**
 * Created by szh on 2016/12/11.
 * 用于处理打洞相关的信息
 */
public class ClientHoleHandler extends IoHandlerAdapter{
    private int randomId;
    private static IoSession firstIoSession = null;
    public ClientHoleHandler(int randomId) {
        super();
        this.randomId = randomId;
    }

    @Override
    public void sessionCreated(IoSession session) throws Exception {
        super.sessionCreated(session);
        System.out.println("assist sessionCreated");
    }

    @Override
    public void sessionOpened(IoSession session) throws Exception {
        super.sessionOpened(session);
        System.out.println("assist sessionOpened");
        if (firstIoSession == null){
            firstIoSession = session;
        }
    }

    @Override
    public void sessionClosed(IoSession session) throws Exception {
        super.sessionClosed(session);
    }

    @Override
    public void sessionIdle(IoSession session, IdleStatus status) throws Exception {
        super.sessionIdle(session, status);
        System.out.println("assist sessionIdle");
    }

    @Override
    public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
        super.exceptionCaught(session, cause);
    }

    @Override
    public void messageReceived(IoSession session, Object message) throws Exception {
        super.messageReceived(session, message);
        System.out.println("assist messageReceived " + message.toString());
        //处理打洞信息
        handleClientHole(session, message.toString());

    }


    @Override
    public void messageSent(IoSession session, Object message) throws Exception {
        super.messageSent(session, message);
    }


    /**
     * 处理客户端打洞
     */
    private void handleClientHole(IoSession session, String msg) throws Exception {
        if (msg != null){
            String[] res = msg.split(":");
            if (res[0].equals(CommunicationCommand.RANDOM_ID) && res[1].equals(String.valueOf(randomId))){
                //随机数验证成功
                if (firstIoSession != null){
                    //0、1位主动打还是被动打
                    session.write(CommunicationCommand.OTCL_ADDR + ":" + firstIoSession.getRemoteAddress() + " " + 0);
                    firstIoSession.write(CommunicationCommand.OTCL_ADDR + ":" + session.getRemoteAddress() + " " + 1);

                    session.closeOnFlush();
                    firstIoSession.closeOnFlush();
                }
            }else {
                throw new Exception("random_id is wrong");
            }
        }else {
            throw new Exception("msg is null");
        }
    }
}
