package com.im.common.utils;

import java.sql.*;
import java.util.ResourceBundle;

/**
 * @author 曾雨农
 * @title: DBUtils
 * @projectName chat-room
 * @date 2022/11/5 12:33
 */
public class DBUtil {
    /**
     * 类加载器时绑定属性资源文件
     */
    private static ResourceBundle bundle = ResourceBundle.getBundle("jdbc");
    static {
        //注册驱动
        try {
            Class.forName(bundle.getString("driver"));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取数据库连接对象，返回一个新的连接对象
     * @return
     * @throws SQLException
     */
    public static Connection getConnection() throws SQLException {
        String url = bundle.getString("url");
        String user = bundle.getString("user");
        String password = bundle.getString("password");
        return DriverManager.getConnection(url, user, password);
    }

    /**
     * 释放资源
     * @param conn
     * @param stmt
     * @param rs
     */
    public static void close(Connection conn,Statement stmt, ResultSet rs){
        if (rs!=null){
            try {
                rs.close();
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
        if (stmt!=null){
            try {
                stmt.close();
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
        if (conn!=null){
            try {
                conn.close();
            }catch (SQLException e){
                e.printStackTrace();
            }
        }

    }
}

