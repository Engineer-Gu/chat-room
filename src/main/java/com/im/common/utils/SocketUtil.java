package com.im.common.utils;

import java.io.*;
import java.net.Socket;

/**
 * @author 陈潇
 * @description Socket工具类
 * @projectName chat-room
 */
public class SocketUtil {

    /**
     * 发送消息
     *
     * @param socket
     * @param msg
     * @param <T>
     */
    public static <T> void sendMsg(Socket socket, T msg){
        try {
            //获取输出流
            OutputStream os = socket.getOutputStream();
            //将输出流包装为对象输出流
            ObjectOutputStream oos = new ObjectOutputStream(os);
            //对象输出流写对象，也就是发送消息
            oos.writeObject(msg);
            //强制将通道中的数据刷出
            oos.flush();
            //告知接收端，消息发送已经完毕
//            socket.shutdownOutput();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 接收消息
     *
     * @param socket
     * @param <T>
     * @return
     */
    public static <T> T receiveMsg(Socket socket){
        try {
            //获取输入流
            InputStream is = socket.getInputStream();
            //将输入流包装为对象输入流
//            System.out.println("转换前");
            ObjectInputStream ois = new ObjectInputStream(is);
//            System.out.println("转换后，读取前");
            //读取对象
            T t = (T) ois.readObject();
//            System.out.println("读取后");
            //告诉发送端，信息读取已经完毕
//            socket.shutdownInput();
            return t;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
