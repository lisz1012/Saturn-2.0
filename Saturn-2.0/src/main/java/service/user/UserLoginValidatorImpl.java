package service.user;

import db.user.UserDao;
import db.user.UserDaoFactory;

public class UserLoginValidatorImpl implements UserLoginValidator {
	private static final UserDao USER_DAO = UserDaoFactory.get();
	@Override
	public boolean validate(String username, String password) {
		int userCount = USER_DAO.getUserCountWithUsernameAndPassword(username, password);
		return userCount == 1;
	}

}
