package shuju;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class baiduDao {

	private Connection conn = null;
	public baiduDao() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String url = "jdbc:mysql://localhost:3306/test?"
					+ "user=root&password=921104&useUnicode=true&characterEncoding=UTF8";

			//String url = "jdbc:mysql://127.0.0.1:3306/test?user=root&password=921104";

			conn = DriverManager.getConnection(url);
			conn.createStatement();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public int add(baidu shuju) {
		try {
			String sql = "INSERT INTO `test`.`shuju` (`author`) VALUES (?);";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, shuju.getAuthor());
			
			return ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}

}
