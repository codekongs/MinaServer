package com.codekong.run;

import com.codekong.config.ConnectionConfig;
import com.codekong.server.MinaService;
import com.codekong.util.ServerHandler;

/**
 * Created by szh on 2016/12/10.
 */
public class RunServer {
    public static void main(String[] args){
        ConnectionConfig connectionConfig = new ConnectionConfig.Builder()
                .setPort(9999)
                .setReadBufferSize(20480)
                .setIdleTime(10)
                .setIoHandlerAdapter(new ServerHandler())
                .builder();
        MinaService minaService = new MinaService();
        minaService.startServer(connectionConfig);
    }
}
