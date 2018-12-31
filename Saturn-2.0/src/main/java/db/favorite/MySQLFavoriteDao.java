package db.favorite;

import java.util.List;
import java.util.Set;

import db.item.ItemDao;
import db.item.ItemDaoFactory;
import entity.Item;

public class MySQLFavoriteDao extends AbstractFavoriteDao {
	private static final ItemDao ITEM_DAO = ItemDaoFactory.get();
	
	static {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public List<Item> getFavoriteItems(String user_id) {
		Set<String> favoriteItemIds = getFavoriteItemIds(user_id);
		return ITEM_DAO.getItemsByIds(favoriteItemIds);
	}

}
