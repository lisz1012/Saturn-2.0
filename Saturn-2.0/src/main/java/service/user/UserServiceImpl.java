package service.user;

import db.user.UserDao;
import db.user.UserDaoFactory;
import entity.User;

public class UserServiceImpl implements UserService {
	private UserDao userDao = UserDaoFactory.get();
	
	@Override
	public void add(User user) {
		userDao.add(user);
	}

	@Override
	public User getUserById(String UserId) {
		// TODO Auto-generated method stub
		return null;
	}

}
