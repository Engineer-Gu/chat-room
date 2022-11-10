package com.im.server.message;

import java.io.Serializable;

/**
 * 消息
 * @author 86153
 * @param <T>
 */
public class Message<T> implements Serializable {

    private static final long serialVersionUID = 3077623444789213960L;

    /**
     * 操作命令
     */
    private int command;

    /**
     * 发送的数据
     */
    private T data;

    public Message(int command, T data) {
        this.command = command;
        this.data = data;
    }

    public int getCommand() {
        return command;
    }

    public T getData() {
        return data;
    }

    @Override
    public String toString() {
        return command + "=>" + data;
    }
}
