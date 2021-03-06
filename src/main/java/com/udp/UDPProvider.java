package com.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class UDPProvider {

    public static void provider() throws IOException {
        System.out.println("UDPProvider Started.");

        // 作为接受者，指定一个端口用于数据接收
        DatagramSocket ds = new DatagramSocket(20000);

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
        System.out.println("UDPProvider receive form ip:."+ip+"\tport:"+port+"\tdata:"+data);
        //构建一份回送数据
        String responseData = "Receive data with len:" + dataLen;
        byte[] responseDataBytes = responseData.getBytes();
        DatagramPacket responsePacket = new DatagramPacket(responseDataBytes, responseDataBytes.length,
                receiverPack.getAddress(), receiverPack.getPort());

        ds.send(responsePacket);

        System.out.println("UDPProvider Finished");
        ds.close();
    }


    public static void main(String[] args) throws IOException {
        provider();
    }
}
