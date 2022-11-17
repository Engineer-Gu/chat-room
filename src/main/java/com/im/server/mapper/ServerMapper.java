package com.im.server.mapper;

import com.im.common.entity.User;
import com.im.common.utils.DBUtil;
import com.im.common.message.Message;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author 老顾
 * @title: UserServiceImpl
 * @projectName chat-room
 * @email: 1437594522@qq.com
 * @date 2022/11/1 16:27
 */
public class ServerMapper {

    /**
     * 查询用户名
     * @param user
     * @return
     */
    public static User selectUserName(User user) {
        // 获取客户端传来的数据
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        // 查询用户表中所有数据
        try {
            // 获取连接
            connection = DBUtil.getConnection();
            // 执行SQL语句
            String sql = "SELECT * FROM tb_user WHERE username = ? ";
            // 获取查询结果集
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,user.getUserName());
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                String userName = resultSet.getString("username");
                String passWord = resultSet.getString("password");
                String email = resultSet.getString("email");
                return new User(userName,passWord,email);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(connection, preparedStatement, resultSet);
        }
        return null;
    }

    /**
     * 增加用户
     *
     * @param msg
     * @return
     */
    public static boolean insertUser(Message msg){
        // 获取客户端传来的数据
        User user = (User) msg.getValue();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        int resultSet = 0;
        // 把用户信息写入数据库
        try {
            // 获取连接
            connection = DBUtil.getConnection();
            // 执行SQL语句
            String sql = "INSERT INTO tb_user "+"(username,password,email)"+"VALUES(?,?,?)";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,user.getUserName());
            preparedStatement.setString(2,user.getPassword());
            preparedStatement.setString(3,user.getEmail());
            resultSet = preparedStatement.executeUpdate();
            if (resultSet == 1){
                System.out.println("注册成功！");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            //释放资源
            DBUtil.close(connection, preparedStatement, null);
        }
        return true;
    }

    /**
     * 用户登录
     * @param msg
     * @return
     */
    public static User login(Message msg) {
        // 获取客户端传来的数据
        User user = (User) msg.getValue();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = DBUtil.getConnection();
            // 执行SQL语句
            String sql = "SELECT * FROM tb_user WHERE username = ? AND password = ? AND email = ?";
            // 获取查询结果集
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,user.getUserName());
            preparedStatement.setString(2,user.getPassword());
            preparedStatement.setString(3,user.getEmail());
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                String userName = resultSet.getString("username");
                String passWord = resultSet.getString("password");
                String email = resultSet.getString("email");
                return new User(userName,passWord,email);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            DBUtil.close(connection, preparedStatement, resultSet);
        }
        return null;
    }

    /**
     * 删除用户
     */
    public static void deleteUser(Message msg) {
        // 获取客户端传来的数据
        User user = (User)msg.getValue();
        String currentUser = user.getUserName();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        int resultSet = 0;
        try {
            connection = DBUtil.getConnection();
            String sql = "DELETE FROM tb_user WHERE username= ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, currentUser);
            resultSet = preparedStatement.executeUpdate();
            if (resultSet == 1){
                System.out.println("删除用户成功，用户【" + currentUser + "】已删除");
            }
            // 执行SQL语句
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            DBUtil.close(connection,preparedStatement,null);
        }
    }

    /**
     * 修改用户密码
     */
    public static void updateUserPassword(Message msg) {
        // 获取客户端传来的数据
        User user = (User) msg.getValue();
        String currentUser = user.getUserName();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        int resultSet = 0;
        try {
            connection = DBUtil.getConnection();
            String sql = "UPDATE tb_user SET password = ? WHERE username = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, user.getNewPassword());
            preparedStatement.setString(2, currentUser);
            resultSet = preparedStatement.executeUpdate();
            if (resultSet == 1){
                System.out.println("修改密码成功");
            }
            // 执行SQL语句
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            DBUtil.close(connection,preparedStatement,null);
        }
    }

    /**
     * 查询旧密码是否存在
     * @param msg
     * @return
     */
    public static User findOnlineUser(Message msg) {
        // 获取客户端传来的数据
        User user = (User) msg.getValue();
        String onlineUser = user.getUserName();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = DBUtil.getConnection();
            // 执行SQL语句
            String sql = "SELECT * FROM tb_user WHERE username = ? AND password = ? ";
            // 获取查询结果集
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, onlineUser);
            preparedStatement.setString(2, user.getPassword());
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                String userName = resultSet.getString("username");
                String passWord = resultSet.getString("password");
                user.setUserName(userName);
                user.setPassword(passWord);
                return user;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            DBUtil.close(connection, preparedStatement, resultSet);
        }
        return null;
    }
}
