package db.item;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import db.MySQLDBUtil;
import entity.Item;
import entity.Item.ItemBuilder;

public class MySQLItemDao implements ItemDao {

	static {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void add(Item item) {
		String sql = "insert ignore into items values (?, ?, ?, ?, ?, ?, ?)";
		
		try (Connection connection = DriverManager.getConnection(MySQLDBUtil.URL);
			  PreparedStatement ps = connection.prepareStatement(sql)) {
			
			ps.setString(1, item.getId());
			ps.setString(2, item.getName());
			ps.setDouble(3, item.getRating());
			ps.setString(4, item.getAddress());
			ps.setString(5, item.getImageUrl());
			ps.setString(6, item.getUrl());
			ps.setDouble(7, item.getDistance());
			
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	
	@Override
	public void add(Collection<Item> items) {
		for (Item item : items) {
			add(item);
		}
	}

	
	@Override
	public Item getItemById(String id) {
		String sql = "select * from items where item_id = ?";
		
		try (Connection connection = DriverManager.getConnection(MySQLDBUtil.URL);
			  PreparedStatement ps = connection.prepareStatement(sql)) {
			
			ps.setString(1, id);
			ResultSet rs = ps.executeQuery();
			ItemBuilder builder = new ItemBuilder();
			if (rs.next()) {
				builder.setId(rs.getString(1));
				builder.setName(rs.getString(2));
				builder.setRating(rs.getDouble(3));
				builder.setAddress(rs.getString(4));
				builder.setImageUrl(rs.getString(5));
				builder.setUrl(rs.getString(6));
				builder.setDistance(rs.getDouble(7));
			}
			
			return builder.build();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	
	@Override
	public List<Item> getItemsByIds(Collection<String> itemIds) {
		List<Item> items = new ArrayList<>();
		for (String itemId : itemIds) {
			items.add(getItemById(itemId));
		}
		return items;
	}
	
}
