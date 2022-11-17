package com.im.common.entity;

import java.io.Serializable;
import java.util.Objects;

/**
 * @author 老顾
 * @title: User
 * @projectName chat-room
 * @email: 1437594522@qq.com
 * @date 2022/11/1 16:27
 */
public class User implements Serializable {

    private static final long serialVersionUID = 8676752272062127112L;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 消息
     */
    private String msg;

    /**
     * 新密码
     */
    private String newPassword;

    /**
     * 输入的内容
     */
    private String content;

    /**
     * 目标用户
     */
    private String targetUser;

    /**
     * 消息类型
     */
    private int mesType;

    public int getMesType() {
        return mesType;
    }

    public void setMesType(int mesType) {
        this.mesType = mesType;
    }

    public String getTargetUser() {
        return targetUser;
    }

    public void setTargetUser(String targetUser) {
        this.targetUser = targetUser;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUserName() {
        return username;
    }

    public void setUserName(String userName) {
        this.username = userName;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(username, user.username) &&
                Objects.equals(password, user.password) &&
                Objects.equals(email, user.email) &&
                Objects.equals(msg, user.msg) &&
                Objects.equals(newPassword, user.newPassword);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, password, email, msg, newPassword);
    }

    public User(String username, String password,String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public User() {
    }

    @Override
    public String toString() {
        return username;
    }
}
