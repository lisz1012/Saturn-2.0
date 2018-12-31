package db.user;

public class UserDaoFactory {
	private static final UserDao USER_DAO = new UserDaoImpl();
	
	public static UserDao get() {
		return USER_DAO;
	}
}
