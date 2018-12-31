package db.user;

import entity.User;

public interface UserDao {
	/**
	 * Use this method to verify if a userId password pair is valid
	 * @param String userId
	 * @param String password
	 * @return
	 */
	@Deprecated
	int getUserCountWithUsernameAndPassword(String userId, String password);
	
	/**
	 * Use this method to get the user by userId and password,
	 * usually used in the validation
	 * @param String userId 
	 * @param String password 
	 * @return User
	 */
	User getUser(String userId, String password);
	
	/**
	 * Use this method to add a given User object to the users table
	 * @param User user
	 * @return
	 */
	void add(User user);
	
	/**
	 * Use this method to get the user by userId.
	 * @param String userId 
	 * @return User
	 */
	User getUserById(String userId);
}
