package tn.esprit.softib.service;

import java.util.List;

import tn.esprit.softib.entity.Compte;
import tn.esprit.softib.entity.User;

public interface IUserService {
	public List<User> getAllUsers(); //admins & agents & users
	public List<User> getClients(); // only Users
	public User getUserById(long id);
	public User getUserByEmail(String email);
	public User getUserByCin(String cin);
	public User getUserByUsername(String username);
	public User addUser(User user);
	public void deleteUser(long id);
	public User updateUser(User user);
	public User signUser(long id);
	public User banUser(String username, String banRaison);
	public Compte deleteAutoUser();
	
}
