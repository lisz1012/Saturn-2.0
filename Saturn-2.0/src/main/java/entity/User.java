package entity;

import org.json.JSONException;
import org.json.JSONObject;

public class User {
	private String id;
	private String password;
	private String firstName;
	private String lastName;
	
	public String getId() {
		return id;
	}
	public String getPassword() {
		return password;
	}
	public String getFirstName() {
		return firstName;
	}
	public String getLastName() {
		return lastName;
	}
	
	private User (UserBuilder builder) {
		id = builder.id;
		password = builder.password;
		firstName = builder.firstName;
		lastName = builder.lastName;
	}
	
	public JSONObject toJSONObject() {
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put("id", id)
					  //.put("password", password) Hide the user's password
			   		  .put("firstName", firstName)
			   		  .put("lastName", lastName);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return jsonObject;
	}
	
	public static class UserBuilder {
		private String id;
		private String password;
		private String firstName;
		private String lastName;
		
		
		public UserBuilder setId(String id) {
			this.id = id;
			return this;
		}
		
		public UserBuilder setPassword(String password) {
			this.password = password;
			return this;
		}
		
		public UserBuilder setFirstName(String firstName) {
			this.firstName = firstName;
			return this;
		}
		
		public UserBuilder setLastName(String lastName) {
			this.lastName = lastName;
			return this;
		}
		
		public User build() {
			return new User(this);
		}
	}
}
