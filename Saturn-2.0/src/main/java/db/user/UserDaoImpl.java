package db.user;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import db.MySQLDBUtil;
import entity.User;
import entity.User.UserBuilder;

public class UserDaoImpl implements UserDao {

	static {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public int getUserCountWithUsernameAndPassword(String username, String password) {
		String sql = "select count(*) from users where user_id = ? and password = ?";
		int count = 0;
		try (Connection connection = DriverManager.getConnection(MySQLDBUtil.URL);
			 PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setString(1, username);
			ps.setString(2, password);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				count = rs.getInt(1);
			}
			rs.close();
			return count;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return 0;
	}

	@Override
	public User getUser(String userId, String password) {
		String sql = "select * from users where user_id = ? and password = ?";
		UserBuilder userBuilder = new UserBuilder();
		try (Connection connection = DriverManager.getConnection(MySQLDBUtil.URL);
			 PreparedStatement ps = connection.prepareStatement(sql)) {
			
			ps.setString(1, userId);
			ps.setString(2, password);
			ResultSet rs = ps.executeQuery();
			User result = null;
			if (rs.next()) {
				userBuilder.setId(rs.getString(1))
						   .setPassword(rs.getString(2))
						   .setFirstName(rs.getString(3))
						   .setLastName(rs.getString(4));
				result = userBuilder.build();
			}
			rs.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public void add(User user) {
		// users table schema: user_id, password, first_name, last_name
		String sql = "insert into users values (?, ?, ?, ?)";
		try (Connection connection = DriverManager.getConnection(MySQLDBUtil.URL);
			 PreparedStatement ps = connection.prepareStatement(sql);) {
			
			ps.setString(1, user.getId());
			ps.setString(2, user.getPassword());
			ps.setString(3, user.getFirstName());
			ps.setString(4, user.getLastName());
			
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public User getUserById(String userId) {
		String sql = "select * from users where user_id = ?";
		UserBuilder userBuilder = new UserBuilder();
		try (Connection connection = DriverManager.getConnection(MySQLDBUtil.URL);
			 PreparedStatement ps = connection.prepareStatement(sql)) {
			
			ps.setString(1, userId);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				userBuilder.setId(rs.getString(1))
						   .setPassword(rs.getString(2))
						   .setFirstName(rs.getString(3))
						   .setLastName(rs.getString(4));
			}
			rs.close();
			return userBuilder.build();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}
}
