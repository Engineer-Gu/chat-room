import com.im.common.utils.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author 曾雨农
 * @title: TestJDBC
 * @projectName chat-room
 * @email: 1437594522@qq.com
 * @date 2022/11/7 17:28
 */
public class TestJDBC {
    public static void main(String[] args) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            //获取连接
            conn = DBUtil.getConnection();
            System.out.println(conn);
            //获取预编译的数据库操作对象
            String sql = "SELECT * FROM tb_user WHERE username = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1,"username");
            //给?传值
            //执行sql
            rs = ps.executeQuery();
            //处理结果集
            while (rs.next()){
                System.out.println(rs.getString("username"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            //释放资源
            DBUtil.close(conn,ps,rs);
            //如果没有结果集对象，调用这个方法的时候第三个参数传null
//            DBUtil.close(conn,ps,null);
        }
    }
}
