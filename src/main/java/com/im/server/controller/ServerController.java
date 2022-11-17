package com.im.server.controller;

import com.im.client.menu.MenuManager;
import com.im.client.page.Page;
import com.im.common.message.Command;
import com.im.common.entity.User;
import com.im.common.utils.SocketUtil;
import com.im.common.message.Message;
import com.im.server.service.ServerService;

import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

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

    private static final ConcurrentHashMap<String, Socket> map = new ConcurrentHashMap<>();

    @Override
    public void run() {
        while (true){
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
                //查看在线用户
                case Command.SHOW_ONLINE_USERS_LIST:
                    processShowOnlinePersonList(msg);
                    break;
                //私聊
                case Command.PRIVATE_CHAT:
                    processPrivateChat(msg);
                    break;
                //群聊
                case Command.PUBLIC_CHAT:
                    processPublicChat(msg);
                    break;
                //账号注销
                case Command.CANCELLATION_ACCOUNT:
                    processCancellationAccount(msg);
                    break;
                //修改密码
                case Command.UPDATE_PASSWORD:
                    processUpdatePassword(msg);
                    break;
                //退出登录
                case Command.GO_BACK_LOGIN:
                    processLogout(msg);
                    break;
            }
        }

    }

    /**
     * 处理注册
     * @param msg
     */
    private void processRegister(Message msg) {
        user = (User) msg.getValue();
        // 注册用户
        ServerService.insertUser(msg);
    }

    /**
     * 处理登录
     * @param msg
     */
    private void processLogin(Message msg) {
        user = (User) msg.getValue();
        User userLoginInfo = ServerService.login(msg);
        // 输出用户登录的时间
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentTime = format.format(new Date());
        System.out.println("登录成功，用户【"+userLoginInfo.getUserName()+"】在【"+currentTime+"】已上线");
        map.put(user.getUserName(), socket);
    }

    /**
     * 处理在线用户
     * @param msg
     */
    private void processShowOnlinePersonList(Message msg) {
        user = (User) msg.getValue();
        String onlineUser = getOnlineUser();
        user.setUserName(onlineUser);
        user.setMesType(Command.SHOW_ONLINE_USERS_LIST);
        SocketUtil.sendMsg(socket, user);
    }

    /**
     * 处理私聊
     * @param msg
     */
    private void processPrivateChat(Message msg){
        //读取客户端发来的user对象
        user = (User) msg.getValue();
        System.out.println("当前用户:【"+user.getUserName()+"】,"+"目标用户:【"+user.getTargetUser()+"】");
        //取出所有在线用户
        String onlineUserAll = getOnlineUser();
        //获取接受消息的目标socket
        Socket getTargetUserSocket = map.get(user.getTargetUser());
        //判断目标用户是否存在在当前在线用户中，如果在，则发消息，不在，则提示不在线
        if (onlineUserAll.contains(user.getTargetUser())){
            //服务端日志
            System.out.println("用户【"+user.getUserName() +"】发来一条消息："+ user.getContent());
            //发送者消息
            SocketUtil.sendMsg(getTargetUserSocket, user);
        }else {
            //对方不在线，给自己发对方不在线的提示
            System.out.println("【"+user.getTargetUser()+"】不在线！");
        }
    }

    /**
     * 处理群聊
     * @param msg
     */
    private void processPublicChat(Message msg) {
        user = (User)msg.getValue();
        Iterator<String> iterator = map.keySet().iterator();
        while (iterator.hasNext()){
            String targetUser = iterator.next();
            if (!targetUser.equals(user.getUserName())){
                Socket getTargetUserSocket = map.get(targetUser);
                user.setTargetUser(targetUser);
                System.out.println("收到来自用户【"+user.getUserName() +"】群发的一条消息，内容是："+ user.getContent());
                //传除了自己的用户的所有socket
                SocketUtil.sendMsg(getTargetUserSocket, user);
            }
        }
    }

    /**
     * 处理注销用户
     * @param msg
     */
    private void processCancellationAccount(Message msg) {
        //获取客户端数据
        user = (User) msg.getValue();
        if (YES.equals(user.getMsg())){
            //先删库
            ServerService.deleteUser(msg);
            //再删key
            map.remove(user.getUserName());
            System.out.println("注销成功");
        }else {
            System.out.println("注销失败");
        }
    }

    /**
     * 修改密码
     * @param msg
     */
    private void processUpdatePassword(Message msg) {
        user = (User) msg.getValue();
        ServerService.updateUserPassword(msg);
        map.remove(user.getUserName());
    }

    /**
     * 退出登录
     * @param msg
     */
    private void processLogout(Message msg) {
        user = (User) msg.getValue();
        // 输出用户登录的时间
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentTime = format.format(new Date());
        System.out.println("退出登录成功，用户【"+user.getUserName()+"】在【"+currentTime+"】已下线");
        map.remove(user.getUserName());
    }

    /**
     * 返回在线用户列表
     * @return
     */
    public static String getOnlineUser() {
        //集合遍历，遍历 map的key
        Iterator<String> iterator = map.keySet().iterator();
        StringBuilder onlineUserList = new StringBuilder();
        while (iterator.hasNext()){
            onlineUserList.append(iterator.next()).append(" ");
        }
        return onlineUserList.toString();
    }
}
