package db.item;

public class ItemDaoFactory {
	private static final ItemDao ITEM_DAO = new MySQLItemDao();
	
	public static ItemDao get() {
		return ITEM_DAO;
	}
}
