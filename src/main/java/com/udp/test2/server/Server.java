package com.udp.test2.server;

import com.udp.test2.TCPConstants;

import java.io.IOException;

public class Server {

    public static void main(String[] args) {
        ServerProvider.start(TCPConstants.PORT_SERVER);

        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }

        ServerProvider.stop();
    }
}
