package service.user;

import entity.User;

public interface UserService {
	void add(User user);
	User getUserById(String UserId);
	// TODO more business logic methods should be added
}
