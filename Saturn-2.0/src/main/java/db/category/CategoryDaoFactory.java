package db.category;

public class CategoryDaoFactory {
	private static final CategoryDao CATEGORY_DAO = new CategoryDaoImpl();
	
	public static CategoryDao get() {
		return CATEGORY_DAO;
	}
}
