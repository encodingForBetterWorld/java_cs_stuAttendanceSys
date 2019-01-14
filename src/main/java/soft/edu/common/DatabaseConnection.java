package soft.edu.common;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseConnection {
		private static final String DBDRIVER = "com.mysql.jdbc.Driver"; // 驱动串
		private static final String DBURL = "jdbc:mysql://localhost:3306/attend?useUnicode=True&characterEncoding=utf8";// 连接URL
		private static final String DBUSER = "root"; // 用户名
		private static final String DBPASSWORD = "root"; // 密码
		public static Connection getConnection() {
			Connection conn = null; // 声明一个连接对象
		try {
			Class.forName(DBDRIVER); // 注册驱动
		    conn = (Connection) DriverManager.getConnection(DBURL, DBUSER, DBPASSWORD);
		    } catch (ClassNotFoundException e) {
		    	e.printStackTrace();
		    	} catch (SQLException e) {
		    		e.printStackTrace();
		    		}return conn;
		    		}
		public static void close(ResultSet rs) {
			if (rs != null) { // 如果结果集对象不为空
				try {rs.close(); // 关闭结果集对象
				} catch (SQLException e) {
					e.printStackTrace();
					}
				}
			}
		public static void close(Connection conn) {
			if (conn != null) { // 如果连接对象不为空
			try {
				conn.close(); // 关闭连接对象
				} catch (SQLException e) {
					e.printStackTrace();
					} } }
		public static void close(PreparedStatement pstmt) {
			if (pstmt != null) { // 如果预处理对象不为空
				try {pstmt.close(); // 关闭预处理对象
				} catch (SQLException e) {
					e.printStackTrace();
					}
				}
			}
		public static void close(Statement stmt) {
			if (stmt != null) { 
				try {stmt.close(); 
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
}
