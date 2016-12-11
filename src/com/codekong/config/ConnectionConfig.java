package com.codekong.config;

import org.apache.mina.core.service.IoHandlerAdapter;

/**
 * Created by szh on 2016/12/3.
 */

public class ConnectionConfig {
    private int idleTime;
    private int port;
    private int readBufferSize;
    private IoHandlerAdapter ioHandlerAdapter;

    public int getIdleTime(){
        return idleTime;
    }
    public int getPort() {
        return port;
    }

    public int getReadBufferSize() {
        return readBufferSize;
    }

    public IoHandlerAdapter getIoHandlerAdapter(){
        return ioHandlerAdapter;
    }



    public static class Builder{
        private int idleTime = 10;
        private int port = 9999;
        private int readBufferSize = 10240;
        private IoHandlerAdapter ioHandlerAdapter = new IoHandlerAdapter();

        public Builder(){}
        public Builder setIdleTime(int idleTime){
            this.idleTime = idleTime;
            return this;
        }
        public Builder setPort(int port) {
            this.port = port;
            return this;
        }

        public Builder setReadBufferSize(int readBufferSize) {
            this.readBufferSize = readBufferSize;
            return this;
        }

        public Builder setIoHandlerAdapter(IoHandlerAdapter ioHandlerAdapter){
            this.ioHandlerAdapter = ioHandlerAdapter;
            return this;
        }

        private void applyConfig(ConnectionConfig connectionConfig){
            connectionConfig.port = port;
            connectionConfig.readBufferSize = readBufferSize;
            connectionConfig.idleTime = idleTime;
            connectionConfig.ioHandlerAdapter = ioHandlerAdapter;
        }

        public ConnectionConfig builder(){
            ConnectionConfig config = new ConnectionConfig();
            applyConfig(config);
            return config;
        }
    }
}
