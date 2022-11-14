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

    private String content;

    private String targetUser;

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

    /**
     * 注册
     * @param username
     * @param password
     * @param email
     */
    public User(String username, String password,String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    /**
     * 登录
     * @param username
     * @param password
     */
    public void login(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public User() {
    }

    /**
     * 查找用户密码
     * @param username
     * @param email
     */
    public void findPassword(String username, String email){
        this.username = username;
        this.email = email;
    }

    /**
     * 删除用户
     * @param msg
     */
    public void deleteUser(String msg){
        this.msg = msg;
    }

    /**
     * 修改密码
     * @param oldPassword
     * @param newPassword
     */
    public void updatePassword(String oldPassword, String newPassword) {
        this.password = oldPassword;
        this.newPassword = newPassword;
    }

    @Override
    public String toString() {
        return username;
    }
}
