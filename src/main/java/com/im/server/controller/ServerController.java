package com.im.server.controller;

import com.im.common.Command;
import com.im.common.entity.User;
import com.im.common.managesocket.ManageServerConnectClientSocket;
import com.im.common.utils.SocketUtil;
import com.im.common.Message;
import com.im.server.service.ServerService;
import com.im.server.starter.ServerStarter;

import java.io.IOException;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author 老顾
 * 控制器-服务端
 */
public class ServerController implements Runnable {

    private static final String YES = "是";
    private Socket socket;
    User user = new User();

    public ServerController(Socket socket) {
        this.socket = socket;
    }


    @Override
    public void run() {
        // 接收数据
        Message msg = SocketUtil.receiveMsg(socket);
            switch (msg.getCommand()) {
                //注册
                case Command.REGISTER:
                    processRegister(msg);
                    break;
                //登录
                case Command.LOGIN:
                    processLogin(msg);
                    break;
                    //退出登录
                case Command.GO_BACK_LOGIN:
                    processLogout(msg);
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
                    processUpdatePassword(msg);
                    break;
                    //查看在线用户
                case Command.SHOW_ONLINE_USERS_LIST:
                    processShowOnlinePersonList();
                    break;
                //私聊
                case Command.PRIVATE_CHAT:
                    processPrivateChat(msg);
                    break;
                //群聊
                case Command.PUBLIC_CHAT:
                    processPublicChat(msg);
                    break;
        }
    }

    /**
     * 处理注册
     * @param msg
     */
    private void processRegister(Message msg) {
        user = (User) msg.getValue();
        // 查询用户账户是否存在
        User findUser = ServerService.selectUserName(user);
        if (findUser != null) {
            // 账户存在
            SocketUtil.sendMsg(socket, -1);
        }else {
            // 注册用户
            boolean success = ServerService.insertUser(msg);
            // 注册成功返回给客户端
            SocketUtil.sendMsg(socket, success ? 1 : 0);
        }
    }

    /**
     * 处理登录
     * @param msg
     */
    private void processLogin(Message msg) {
        user = (User) msg.getValue();
        // 查询用户是否存在
        User findUser = ServerService.selectUserName(user);
        if (findUser != null
                && findUser.getUserName().equals(user.getUserName())
                && findUser.getPassword().equals(user.getPassword())
                && findUser.getEmail().equals(user.getEmail())) {
            // 用户存在，则进行登录
            User userLoginInto = ServerService.login(msg);
            // 输出用户登录的时间
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String currentTime = format.format(new Date());
            System.out.println("登录成功，用户【"+userLoginInto.getUserName()+"】在【"+currentTime+"】已上线");
            SocketUtil.sendMsg(socket, userLoginInto);
            ManageServerConnectClientSocket.addServerConnectClientSocket(userLoginInto.getUserName(), socket);
        }else {
            System.out.println("登录失败");
            SocketUtil.sendMsg(socket, null);
        }
    }

    /**
     * 退出登录
     * @param msg
     */
    private void processLogout(Message msg) {
        user = (User) msg.getValue();
        boolean cancel = ManageServerConnectClientSocket.removeServerConnectClientSocket(user.getUserName());
        // 输出用户登录的时间
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentTime = format.format(new Date());
        System.out.println("退出登录成功，用户【"+user.getUserName()+"】在【"+currentTime+"】已下线");
        SocketUtil.sendMsg(socket, cancel);
    }

    /**
     * 处理在线用户
     */
    private void processShowOnlinePersonList() {
        String onlineUser = ManageServerConnectClientSocket.getOnlineUser();
        SocketUtil.sendMsg(socket, onlineUser);
    }

    /**
     * 处理私聊
     * @param msg
     */
    private void processPrivateChat(Message msg){
        user = (User) msg.getValue();
        System.out.println("目标用户："+user.getUserName());
        //取出所有用户
        String onlineUserAll = ManageServerConnectClientSocket.getUserAll();
        // 判断目标用户是否存在在当前在线用户中，如果在，则发消息，不在，则提示不在线
        System.out.println(onlineUserAll.contains(user.getUserName()));
        if (onlineUserAll.contains(user.getUserName())){
            //服务端日志
            System.out.println(user.getUserName() +"，对你说："+user.getContent());
            //发送者消息
            String message = user.getUserName() + "，对你说：" + user.getContent();
            //发送回客户端
            SocketUtil.sendMsg(socket, message);
        }else {
            SocketUtil.sendMsg(socket, null);
            System.out.println("对方不在线");
        }

    }

    /**
     * 处理群聊
     * @param msg
     */
    private void processPublicChat(Message msg) {

    }

    /**
     * 处理找回密码
     * @param msg
     */
    private void processGetPasswordBack(Message msg) {
        //获取客户端数据
        user = (User) msg.getValue();
        //查询数据库中是否有该用户
        User findUser = ServerService.selectUserName(user);
        if(findUser != null){
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
        //获取客户端数据
        user = (User) msg.getValue();
        String onlineUser = ManageServerConnectClientSocket.getUser();
        if (YES.equals(user.getMsg())){
            //先删库
            ServerService.deleteUser();
            SocketUtil.sendMsg(socket, onlineUser);
            //再删key
            ManageServerConnectClientSocket.removeOnlineUser(onlineUser);
        }else {
            SocketUtil.sendMsg(socket,null);
        }
    }

    /**
     * 修改密码
     * @param msg
     */
    private void processUpdatePassword(Message msg) {
        user = (User) msg.getValue();
        //查询当前在线用户
        User findOnlineUser = ServerService.findOnlineUser(msg);
        if (findOnlineUser == null){
            SocketUtil.sendMsg(socket,null);
        }else if (findOnlineUser.getPassword().equals(user.getPassword())){
            //如果输入的密码和库中密码一致，则修改旧密码为新密码
            ServerService.updateUserPassword(msg);
            SocketUtil.sendMsg(socket, findOnlineUser);
            ManageServerConnectClientSocket.removeOnlineUser(findOnlineUser.getUserName());
        }
    }

}
