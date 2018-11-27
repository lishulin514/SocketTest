package com.udp;

import java.io.IOException;
import java.net.*;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * UDP 搜索者，用于搜索服务支持方
 */
public class UDPSearcher {

    public static void Searcher() throws IOException {
        System.out.println("UDPSearch Started.");

        // 作为搜索方，让系统自动分配端口
        DatagramSocket ds = new DatagramSocket();

        //构建一份请求数据
        String requestData = "HelloWord!";
        byte[] requestDataBytes = requestData.getBytes();
        //        // 直接根据发送者构建一份回送信息
        DatagramPacket requestPacketPack = new DatagramPacket(requestDataBytes, requestDataBytes.length);
        // 本机20000端口
        requestPacketPack.setAddress(InetAddress.getLocalHost());
        requestPacketPack.setPort(20000);
        // 发送
        ds.send(requestPacketPack);

        // 构建接收实体
        final byte[] buf = new byte[512];
        DatagramPacket receiverPack = new DatagramPacket(buf, buf.length);

        // 接收
        ds.receive(receiverPack);

        // 打印接收到的信息与发送者的信息
        // 发送者的IP地址
        String ip = receiverPack.getAddress().getHostAddress();
        int port = receiverPack.getPort();
        int dataLen = receiverPack.getLength();
        String data = new String(receiverPack.getData(), 0, dataLen);
        System.out.println("UDPSearcher receive form ip:."+ip+"\tport:"+port+"\tdata:"+data);

        // 完成
        System.out.println("UDPSearcher Finished.");
        ds.close();
    }

    public static void main(String[] args) throws IOException {




        Searcher();
    }
}
