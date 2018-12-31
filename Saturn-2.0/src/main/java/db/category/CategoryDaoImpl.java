package db.category;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import db.MySQLDBUtil;

public class CategoryDaoImpl implements CategoryDao {
	
	static {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void add(String itemId, String category) {
		String sql = "insert IGNORE into categories values (?, ?)";
		try (Connection connection = DriverManager.getConnection(MySQLDBUtil.URL);
			 PreparedStatement ps = connection.prepareStatement(sql);) {
			ps.setString(1, itemId);
			ps.setString(2, category);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
