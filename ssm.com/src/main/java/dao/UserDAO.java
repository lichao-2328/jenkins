package dao;

import entity.User;

public interface UserDAO {
	 User findByUsername(String username);
}
