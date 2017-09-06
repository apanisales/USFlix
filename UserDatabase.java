//Author: Anthony Panisales
package usflix;

import java.util.*;  

public class UserDatabase extends Object {
	
	public static final int minPwdLength = 6;
	private HashMap<String,User> userDB;

	public UserDatabase() {
		userDB = new HashMap<String,User>();
	}


	public User login(String u, String p) {
		//Checks for username and password requirements
		if (userDB.containsKey(u) && userDB.get(u).getPassword().equals(p)) {
			return userDB.get(u);
		} else {
			return null;
		}
	}

	public boolean isAvailable(String u) {
		return userDB.containsKey(u);
	}

	public User createAccount(String f, String l, String u, String p) {
		//Checks for username and password requirements
		if (p.contains(u) || p.length() < minPwdLength || userDB.containsKey(u)) {
			return null;
		} else {
			User x = new User(f, l, u, p);
			userDB.put(u, x);
			return x;
		}
	}

	public HashMap<String,User> getUserDB() {
		return userDB;
	}

}