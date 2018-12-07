package com.tcp.tcpChannel.client;

import com.tcp.udpSend.client.TCPClient;
import com.tcp.udpSend.client.UDPSearcher;
import com.udp.test2.client.bean.ServerInfo;

import java.io.IOException;

public class Client {
    public static void main(String[] args) {
        ServerInfo info = UDPSearcher.searchServer(10000);
        System.out.println("Server:" + info);

        if (info != null) {
            try {
                TCPClient.linkWith(info);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}