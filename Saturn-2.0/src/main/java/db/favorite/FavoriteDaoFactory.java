package db.favorite;

public class FavoriteDaoFactory {
	private static final FavoriteDao FAVORITE_DAO = new MySQLFavoriteDao();
	
	public static FavoriteDao get() {
		return FAVORITE_DAO;
	}
}
