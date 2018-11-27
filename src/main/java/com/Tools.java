package com;

public class Tools {
    public static int byteArrayToInt(byte[] b){
//        System.out.println(b[3] & 0xff);
        System.out.println(Integer.toBinaryString(b[3] & 0xff));
        System.out.println(Integer.toBinaryString((b[2] & 0xff) << 8));
        System.out.println(Integer.toBinaryString((b[1] & 0xff) << 16));
        System.out.println(Integer.toBinaryString((b[0] & 0xff) << 24));
        System.out.println(b[3] & 0xff);
        System.out.println((b[2] & 0xff) << 8);
        System.out.println((b[1] & 0xff) << 16);
        System.out.println((b[0] & 0xff) << 24);

        // | 等于按位或
        return b[3] & 0xff|
                (b[2] & 0xff) << 8 |
                (b[1] & 0xff) << 16 |
                (b[0] & 0xff) << 24;
    }

    public static byte[] intToByteArray(int a){
        byte b0 =  (byte)((a >> 24) & 0xff);
        byte b1 = (byte)((a >> 16) & 0xff);
        byte b2 = (byte)((a >> 8) & 0xff);
        byte b3 =   (byte)(a & 0xff);
//        System.out.println(b0);
//        System.out.println(b1);
//        System.out.println(b2);
//        System.out.println(b3);
        return new byte[]{b0, b1, b2, b3};
    }

//    4字节
//    1字符8比特

    public static void main(String[] args) {

//        int i = 257;
//        byte[] bytes = intToByteArray(i);
//        int converToInt = byteArrayToInt(bytes);
//        System.out.println("converInt:"+converToInt);


        System.out.println(1|256|2);
    }
}
