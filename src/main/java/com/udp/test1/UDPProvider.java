package com.udp.test1;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.UUID;

public class    UDPProvider {

    public static void main(String[] args) throws IOException {
        //生成一份唯一表示
        String sn = UUID.randomUUID().toString();
        Provider provider = new Provider(sn);
        provider.start();

        // 读取任意j键盘信息后可以退出
        System.in.read();
        provider.exit();
    }

    private static class Provider extends Thread{

        private final String sn;
        private boolean done = false;
        private DatagramSocket ds = null;

        private Provider(String sn){
            this.sn = sn;
        }

        @Override
        public void run() {
            super.run();

            System.out.println("UDPProvider Started.");
            try {

                //监听20000端口
                ds = new DatagramSocket(20000);
                while(!done){
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

                    // 解析端口号
                    int responsePort = MessageCreator.parsePort(data);

                    if(responsePort != -1){
                        //构建一份回送数据
                        String responseData = MessageCreator.BuildWithSn(sn);
                        byte[] responseDataBytes = responseData.getBytes();
                        DatagramPacket responsePacket = new DatagramPacket(
                                responseDataBytes,
                                responseDataBytes.length,
                                receiverPack.getAddress(),
                                receiverPack.getPort());

                        ds.send(responsePacket);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                close();
            }

            // 完成
            System.out.println("UDPProvider Finished");
        }

        void exit(){
            done = true;
            ds.close();
            ds = null;
        }

        private void close(){
            if(ds != null){
                ds.close();
                ds = null;
            }
        }
    }
}
