package com.udp.test1;

public class MessageCreator {

    private static final String SN_HEADER = "收到暗号，我是（SN）";
    private static final String PORT_HEADER = "这是暗号，请回电端口（Port）:";

    public static String BuildWithPort(int port){

        return PORT_HEADER+port;
    }

    public static int parsePort(String data){
        if(data.startsWith(PORT_HEADER)){
            return Integer.parseInt(data.substring(PORT_HEADER.length()));
        }

        return -1;
    }

    public static String BuildWithSn(String sn){
        return SN_HEADER+sn;
    }

    public static String ParseSn(String data){

        if(data.startsWith(SN_HEADER)){
            return data.substring(PORT_HEADER.length());
        }

        return null;
    }
}
