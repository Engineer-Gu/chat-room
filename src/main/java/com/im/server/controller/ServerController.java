package com.im.server.controller;

import com.im.common.Command;
import com.im.common.entity.User;
import com.im.common.utils.SocketUtil;
import com.im.server.message.Message;
import com.im.server.service.ServerDao;

import java.net.Socket;

/**
 * @author 老顾
 * 控制器-服务端
 */
public class ServerController implements Runnable {

    private Socket socket;

    public ServerController(Socket socket) {
        this.socket = socket;
    }

    private static final String YES = "是";
    private static final String NO = "否";

    @Override
    public void run() {
        // 接收数据
        Message msg = SocketUtil.receiveMsg(socket);
        System.out.println(msg);
        switch (msg.getCommand()) {
                //注册
            case Command.REGISTER:
                processRegister(msg);
                break;
                //登录
            case Command.LOGIN:
                processLogin(msg);
                break;
                //找回密码
            case Command.FIND_PASSWORD:
                processGetPasswordBack(msg);
                break;
                //账号注销
            case Command.CANCELLATION_ACCOUNT:
                processCancellationAccount(msg);
                break;
                //修改密码
            case Command.UPDATE_PASSWORD:
                updatePassword(msg);
                //查看用户
            case Command.VIEW_USERS:
//                processViewUsers();
                break;
        }
    }

    /**
     * 处理注册
     * @param msg
     */
    private void processRegister(Message msg) {
        User user = (User) msg.getData();
        // 查询用户账户是否存在
        User findUser = ServerDao.selectUserName(user);
        if (findUser != null) {
            // 账户存在
            SocketUtil.sendMsg(socket, -1);
        }else {
            // 注册用户
            boolean success = ServerDao.insertUser(msg);
            // 注册成功返回给客户端
            SocketUtil.sendMsg(socket, success ? 1 : 0);
        }
    }

    /**
     * 处理登录
     * @param msg
     */
    private void processLogin(Message msg) {
        User user = (User) msg.getData();
        // 查询用户是否存在
        User findUser = ServerDao.selectUserName(user);
        if (findUser != null) {
            // 用户存在，则进行登录
            User userLoginInto = ServerDao.login(msg);
            SocketUtil.sendMsg(socket, userLoginInto);
        }else {
            SocketUtil.sendMsg(socket, null);
        }
    }

    /**
     * 处理找回密码
     * @param msg
     */
    private void processGetPasswordBack(Message msg) {
        User user = (User) msg.getData();
        //查询数据库中是否有该用户
        User findUser = ServerDao.selectUserName(user);
        if(findUser!=null){
            //如果存在，就把用户信息发送回客户端
            SocketUtil.sendMsg(socket, findUser);
        } else {
            //账号不存在
            SocketUtil.sendMsg(socket, null);
        }
    }

    /**
     * 处理注销用户
     * @param msg
     */
    private void processCancellationAccount(Message msg) {
        User user = (User) msg.getData();
        User findUser = ServerDao.selectUserName(user);
        if (findUser!=null&&YES.equals(user.getMsg())){
            ServerDao.deleteUser(msg);
            SocketUtil.sendMsg(socket,findUser);
        }else if (NO.equals(user.getMsg())){
            SocketUtil.sendMsg(socket,null);
        }
    }

    /**
     * 修改密码
     * @param msg
     */
    private void updatePassword(Message msg) {
        // 接收数据
        User user = (User) msg.getData();
        //查找用户是否存在 如果用户名存在，用户名正确，旧密码正确，则进行修改旧密码为新密码
        User findUser = ServerDao.selectUserName(user);
        if (findUser != null && findUser.getUserName().equals(user.getUserName())&&findUser.getPassword().equals(user.getPassword())){
            ServerDao.updateUserPassword(msg);
            SocketUtil.sendMsg(socket, findUser);
        }else {
            SocketUtil.sendMsg(socket,null);
        }
    }
    /**
     * 处理查看用户
     */
    private void processViewUsers(User user) {
        User users = ServerDao.selectUserName(user);
        SocketUtil.sendMsg(socket, users);
    }
}
