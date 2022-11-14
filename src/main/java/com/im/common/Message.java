package com.im.common;

import java.io.Serializable;
import java.net.Socket;

/**
 * 消息
 * @author 86153
 * @param <T>
 */
public class Message<T,V> implements Serializable {

    private static final long serialVersionUID = 3077623444789213960L;

    /**
     * 操作命令
     */
    private int command;

    /**
     * 发送的数据
     */
    private T target;

    /**
     * 接受的数据
     */
    private V value;

    private Socket socket;

    public Message() {

    }

    public int getCommand() {
        return command;
    }

    public void setCommand(int command) {
        this.command = command;
    }

    public T getTarget() {
        return target;
    }

    public void setTarget(T target) {
        this.target = target;
    }

    public V getValue() {
        return value;
    }

    public void setValue(V value) {
        this.value = value;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    @Override
    public String toString() {
        return "当前输入命令为："+ command + " => " + "由【" + value + "】输入";
    }
}
