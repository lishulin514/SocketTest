package com.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public static void main(String[] args) throws IOException {

        ServerSocket server = new ServerSocket(2000);
        System.out.println("服务器准备就绪~");
        System.out.println("服务器信息："+server.getInetAddress()+"P:"+server.getLocalPort());

        // 等待客户端连接
        for(;;){
            Socket client = server.accept();

            ClientHandler clientHandler = new ClientHandler(client);
            clientHandler.start();
        }

    }

    /**
     * 客户端消息处理
     */
    private static class ClientHandler extends Thread{

        private Socket socket;
        private boolean flag = true;

        @Override
        public void run() {
            super.run();
            System.out.println("新客户端连接:"+ socket.getInetAddress() + "P:" + socket.getPort());
            try{

                // 得到打印流，用于数据输出；服务器会送数据使用
                PrintStream socketOutput = new PrintStream(socket.getOutputStream());
                //输入流
                BufferedReader socketinput = new BufferedReader(new InputStreamReader(
                        socket.getInputStream()));

                do{
                    String str = socketinput.readLine();
                    if("bye".equalsIgnoreCase(str)){
                        flag = false;
                        // 回送
                        socketOutput.println("bye");
                    }else{
                        // 打印到屏幕，并回送数据长度
                        System.out.println(str);
                        socketOutput.println("回送："+str.length());
                    }
                }while(flag);
                socketinput.close();
                socketOutput.close();
            }catch(Exception e){
                e.printStackTrace();
                System.out.println("连接异常断开");
            }finally{
                // 连接关闭
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            System.out.println("客户端已推出"+ socket.getInetAddress() + "P:" + socket.getPort());

        }

        public ClientHandler(Socket socket){
            this.socket = socket;
        }
    }

}
