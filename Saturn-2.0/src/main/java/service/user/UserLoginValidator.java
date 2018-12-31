package service.user;

public interface UserLoginValidator {
	/**
	 * Verify if a user can login with the username-password pair
	 * @param username
	 * @param password
	 * @return boolean true if user pass the validation, false if user can't pass the validation
	 */
	boolean validate(String username, String password);
}
