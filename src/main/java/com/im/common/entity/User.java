package com.im.common.entity;

import java.io.Serializable;

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
     * @return
     */
    private String newPassword;

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
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User() {
    }

    public User(String username){
        this.username = username;
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
     * @param username
     * @param msg
     */
    public void deleteUser(String username,String msg){
        this.username = username;
        this.msg = msg;
    }

    /**
     * 修改密码
     * @param username
     * @param newPassword
     */
    public void updatePassword(String username, String oldPassword, String newPassword) {
        this.username = username;
        this.password = oldPassword;
        this.newPassword = newPassword;
    }

    @Override
    public String toString() {
        return "用户名："+username ;
    }
}
