package db.recommend;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import db.MySQLDBUtil;

public class RecommendationDaoImpl implements RecommendationDao {
	
	static {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public Set<String> getRecommendedCategories(String userId) {
		String sql = "select c.category from history h join categories c on (h.item_id = c.item_id) where h.user_id = ?";
		Set<String> categories = new HashSet<>();
		try (Connection connection = DriverManager.getConnection(MySQLDBUtil.URL);
			 PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setString(1, userId);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				categories.add(rs.getString(1));
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return categories;
	}
}
