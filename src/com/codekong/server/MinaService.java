package com.codekong.server;

import com.codekong.config.ConnectionConfig;
import com.codekong.util.SessionManager;
import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;

public class MinaService {
    private IoAcceptor acceptor;

    /**
     * 开启服务器
     * @param config
     */
    public void startServer(ConnectionConfig config){
        //创建监听对象
        acceptor = new NioSocketAcceptor();
        //添加日志过滤器
        acceptor.getFilterChain().addLast("codec",
                new ProtocolCodecFilter(
                new TextLineCodecFactory(Charset.forName("UTF-8"))));
        acceptor.setHandler(config.getIoHandlerAdapter());
        //设置读缓冲区大小
        acceptor.getSessionConfig().setReadBufferSize(config.getReadBufferSize());
        //超过10秒没有读和写就设置为空闲状态
        acceptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, config.getIdleTime());
        try {
            acceptor.bind(new InetSocketAddress(config.getPort()));
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
    }

    /**
     * 关闭连接
     */
    public void closeServer(){
        acceptor.dispose(true);
        SessionManager.getInstance().closeSession();
        SessionManager.getInstance().removeSessio();
    }
}
