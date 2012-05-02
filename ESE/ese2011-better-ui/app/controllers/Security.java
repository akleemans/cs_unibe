package controllers;

import models.Database;
import models.User;

public class Security extends Secure.Security{

	static boolean authenticate(String username, String password){
		
		for(User user : Database.getUserList()){
			if (user.getName().equals(username) && user.getPassword().equals(password))
				return true;
		}
		return false;
	}
}
