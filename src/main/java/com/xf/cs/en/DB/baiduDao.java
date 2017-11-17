package com.xf.cs.en.DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class baiduDao {

	private Connection conn = null;
	private Statement stmt = null;
	
	public baiduDao() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String url = "jdbc:mysql://localhost:3306/test?"
					+ "user=root&password=921104&useUnicode=true&characterEncoding=UTF8";

			//String url = "jdbc:mysql://127.0.0.1:3306/test?user=root&password=921104";

			conn = DriverManager.getConnection(url);
			stmt = conn.createStatement();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public int add(baidu shujuku) {
		try {
			String sql = "INSERT INTO `test`.`shujuku` (`author`, `titles`, `belongs` , `dateq`, `des`, `answers`, `withname`, `withoutname`, `dater`) VALUES (?, ?, ?, ?, ?, ?, ?, ?,?);";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, shujuku.getAuthor());
			ps.setString(2, shujuku.getTitle());
			ps.setString(3, shujuku.getBelongs());
			ps.setString(4, shujuku.getDateq());
			ps.setString(5, shujuku.getDes());
			ps.setString(6, shujuku.getAnswers());
			ps.setString(7, shujuku.getWithname());
			ps.setString(8, shujuku.getWithoutname());
			ps.setString(9, shujuku.getDater());
			return ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}

}
