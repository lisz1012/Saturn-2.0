package db.favorite;

import java.util.List;
import java.util.Set;

import entity.Item;

public interface FavoriteDao {
	Set<String> getFavoriteItemIds(String user_id);
	List<Item> getFavoriteItems(String user_id);
	void addFavorite(String userId, String itemId);
	void deleteFavorite(String userId, String itemId);
}
