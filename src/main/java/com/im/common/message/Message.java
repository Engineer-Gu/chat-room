package com.im.common.message;

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

    private String mesType;//消息类型【在接口定义已知类型】

    public String getMesType() {
        return mesType;
    }

    public void setMesType(String mesType) {
        this.mesType = mesType;
    }

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

    @Override
    public String toString() {
        return "当前输入命令为："+ command + " => " + "由【" + value + "】输入";
    }
}
