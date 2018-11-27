package com.udp.test1;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * UDP 搜索者，用于搜索服务支持方
 */
public class UDPSearcher {

    private static final int LISTEN_PORT = 30000;

    public static void main(String[] args) throws IOException, InterruptedException {
        System.out.println("UDPSearcher Started.");
        Listener listener = listen();
        sendBroadcast();

        // 读取任意键盘息后可以退出
        System.in.read();
        List<Device> devices = listener.getDevicesAndClose();

        for (Device device : devices) {
            System.out.println("Device:" + device.toString());
        }

        // 完成
        System.out.println("UDPSearcher Finished.");
    }

    private static Listener listen() throws InterruptedException {
        System.out.println("UDPSearcher start listen.");
        CountDownLatch countDownLatch = new CountDownLatch(1);
        Listener listener = new Listener(LISTEN_PORT, countDownLatch);
        listener.start();

        countDownLatch.await();
        return listener;
    }

    private static void sendBroadcast() throws IOException {
        System.out.println("UDPSearcher sendBroadcast started.");

        // 作为搜索方，让系统自动分配端口
        DatagramSocket ds = new DatagramSocket();

        //构建一分请求数据
        String requestData = MessageCreator.BuildWithPort(LISTEN_PORT);
        byte[] requestDataBytes = requestData.getBytes();
        // 直接根据发送者构建一份回送信息
        DatagramPacket requestPacketPack = new DatagramPacket(requestDataBytes, requestDataBytes.length);
        // 20000端口，广播地址
        requestPacketPack.setAddress(InetAddress.getByName("255.255.255.255"));
        requestPacketPack.setPort(20000);
        // 发送
        ds.send(requestPacketPack);
        ds.close();

        System.out.println("UDPSearcher FInished.");
    }

    private static class Device{
        int port;
        String ip;
        String sn;

        public Device(int port, String ip, String sn) {
            this.port = port;
            this.ip = ip;
            this.sn = sn;
        }

        @Override
        public String toString() {
            return "Device{" +
                    "port=" + port +
                    ", ip='" + ip + '\'' +
                    ", sn='" + sn + '\'' +
                    '}';
        }
    }

    private static class Listener extends  Thread{

        private final int listenPort;
        private final CountDownLatch countDownLatch;
        private final List<Device> devices = new ArrayList<>();
        private boolean done = false;
        private DatagramSocket ds = null;

        public Listener(int listenPort, CountDownLatch countDownLatch){

            this.listenPort = listenPort;
            this.countDownLatch = countDownLatch;
        }

        @Override
        public void run() {
            super.run();
            countDownLatch.countDown();
            try {
                // 监听回送端口
                ds = new DatagramSocket(listenPort);
                while (!done){
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
                    System.out.println("UDPSearcher receive form ip:"+ip+"\tport:"+port+"\tdata:"+data);

                    String sn = MessageCreator.ParseSn(data);
                    if(sn != null){
                        Device device = new Device(port, ip, sn);
                        devices.add(device);
                    }

                }
            } catch (Exception e){

            } finally {
                close();
            }
            System.out.println("UDPSearcher listener finished");
        }

        private void close(){
            if (ds != null) {
                ds.close();
                ds = null;
            }
        }

        List<Device> getDevicesAndClose(){
            done = true;
            close();
            return devices;
        }
    }
}
