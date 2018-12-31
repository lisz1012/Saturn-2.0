package db.item;

import java.util.Collection;
import java.util.List;

import entity.Item;

public interface ItemDao {
	void add(Item item);
	void add(Collection<Item> items);
	Item getItemById(String id);
	List<Item> getItemsByIds(Collection<String> itemIds);
}
