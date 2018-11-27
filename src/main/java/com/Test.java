package com;

public class Test {

    public static void main(String[] args) {
        String s = "abcdefg{哈:\"}哈";
        System.out.println(s);
        System.out.println(s.length());
        System.out.println(s.getBytes().length);
        byte[] obj = new byte[10];
        System.arraycopy(s.getBytes(),0,obj,0,10);
        System.out.println(new String(obj));

//        System.out.println(s.substring(0,50));
    }
}
