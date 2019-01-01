package db.category;

import java.util.Set;

public interface CategoryDao {
	void add(String itemId, String category);

	Set<String> getCategory(String id);
}
