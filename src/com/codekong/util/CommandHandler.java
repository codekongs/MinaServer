package com.codekong.util;

import com.codekong.config.CommunicationCommand;
import com.codekong.config.ConnectionConfig;
import com.codekong.server.MinaService;

/**
 * Created by szh on 2016/12/11.
 * 处理收到的指令类
 */
public class CommandHandler {
    /**
     * 处理解析出来的指令
     * @param msg
     */
    public static void handleCommand(String[] msg) {
        String command = msg[0];
        if (command.equals(CommunicationCommand.ACTIVE_LOGIN)){
            activeLogin(msg[1]);
        }else if (command.equals(CommunicationCommand.PASSIVE_LOGIN)){
            passiveLogin(msg[1]);
        }else if (command.equals(CommunicationCommand.HOLE_PUNCHING)){
            if (SessionManager.getInstance().getAttributeValue("passive_login").equals("true")){
                holePunching(msg[1]);
            }
        }
    }

    /**
     * 主动连接处理
     */
    private static void activeLogin(String data) {
        System.out.println("active_login" + data);
        //用户名和密码
        String[] res = data.split(" ");
        //验证用户名密码是否正确
        if (res[0].equals("admin0") && res[1].equals("admin0")){
            //验证成功
            SessionManager.getInstance().writeToServer(CommunicationCommand.STATUS_CODE
                    + ":" + CommunicationCommand.STATUS_CODE_SUCC);
            if (SessionManager.getInstance().getSession() != null){
                //存储Session
                SessionList.addSession(SessionManager.getInstance().getSession());
            }
        }else {
            //验证失败
            SessionManager.getInstance().writeToServer(CommunicationCommand.STATUS_CODE
                    + ":" + CommunicationCommand.STATUS_CODE_FAIL);
        }

    }

    /**
     * 被动连接请求
     */
    private static void passiveLogin(String data) {
        System.out.println("passive_login" + data);
        //用户名和密码
        String[] res = data.split(" ");
        //验证用户名密码是否正确
        if (res[0].equals("admin1") && res[1].equals("admin1")){
            //验证成功
            SessionManager.getInstance().writeToServer(CommunicationCommand.STATUS_CODE
                    + ":" + CommunicationCommand.STATUS_CODE_SUCC);
            SessionManager.getInstance().setAttributeValue("passive_login", "true");
        }else {
            //验证失败
            SessionManager.getInstance().writeToServer(CommunicationCommand.STATUS_CODE
                    + ":" + CommunicationCommand.STATUS_CODE_FAIL);
        }
    }

    /**
     * 打洞请求
     */
    private static void holePunching(String data) {
        System.out.println("hole_punching" + data);
        String[] res = data.split(" ");
        //盘ID和协议类型
        //盘ID为5431  协议为TCP
        if (res[0].equals("5431") && res[1].equals("6")){
            assignAssistPort();
        }
    }

    /**
     * 分配协助端口
     */
    private static void assignAssistPort() {
        int randomPort = (int) Math.round(Math.random() * (65535 - 1024) + 1025);
        System.out.println("port" + randomPort);
        ConnectionConfig connectionConfig = new ConnectionConfig.Builder()
                .setPort(randomPort)
                .setReadBufferSize(20480)
                .setIdleTime(10)
                .setIoHandlerAdapter(new ClientHoleHandler())
                .builder();
        MinaService minaService = new MinaService();
        minaService.startServer(connectionConfig);

        //向服务器返回端口分配信息
        String portInfo = "assist:" + randomPort + ":" +(Math.random() * 10000);
        SessionManager.getInstance().writeToServer(portInfo);
        SessionList.getIoSession(1).write(portInfo);
    }
}
