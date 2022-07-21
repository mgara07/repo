package tn.esprit.softib.service;

import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.mail.SendFailedException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tn.esprit.softib.component.EmailServiceImpl;
import tn.esprit.softib.entity.Compte;
import tn.esprit.softib.entity.User;
import tn.esprit.softib.enums.ERole;
import tn.esprit.softib.repository.UserRepository;

@Service
public class UserServiceImpl implements IUserService {

	@Autowired
	UserRepository userRepo;
	@Autowired
	PasswordEncoder encoder;
	@Autowired
	EmailServiceImpl emailService;
	
	private static final String EMPTY_STRING = "";
	private static final String CHARS_UPPERCASE = "Chars (Uppercase)";
	private static final String CHARS_LOWERCASE = "Chars (Lowercase)";
	private static final String INVALID_SIZE = "Invalid size.";
	private static final String INVALID_INPUT = "Invalid input.";
	private static final String PASSWORD_LENGTH2 = "Password Length";
	private static final String _20S_S_N_N = "%-20s: %s%n%n";
	private static final String FINAL_PASSWORD = "Final Password";
	private static final String PASSWORD2 = "Password";
	private static final String DIGITS = "Digits";
	private static final String FORMAT = "%-20s: %s%n";
	private static final String CORDIALEMENT = "Cordialement.";
	private static final String PASSWORD = "	password : ";
	private static final String USERNAME = " 	username : ";
	private static final String YOU_CAN_FIND_BELOW_YOUR_CREDENTIALS = "you can find below your credentials : ";
	private static final String NEW_LINE = "\n";
	private static final String SEPARATOR = ",";
	private static final String ESPACE = " ";
	private static final String HELLO = "Hello ";
	private static final String WELCOME_TO_SOFTIB = "Welcome to SOFTIB";
	private static final String CHAR_LOWERCASE = "abcdefghijklmnopqrstuvwxyz";
	private static final String CHAR_UPPERCASE = CHAR_LOWERCASE.toUpperCase();
	private static final String DIGIT = "0123456789";
	private static final String OTHER_PUNCTUATION = "!@#&()–[{}]:;',?/*";
	private static final String OTHER_SYMBOL = "~$^+=<>";
	private static final String OTHER_SPECIAL = OTHER_PUNCTUATION + OTHER_SYMBOL;
	private static final int PASSWORD_LENGTH = 8;

	private static final String PASSWORD_ALLOW = CHAR_LOWERCASE + CHAR_UPPERCASE + DIGIT + OTHER_SPECIAL;

	private static SecureRandom random = new SecureRandom();

	@Override
	public List<User> getAllUsers() {
		return userRepo.findAll();
	}

	@Override
	public User getUserById(long id) {
		return userRepo.findById(id).orElse(null);
	}

	@Override
	public User getUserByEmail(String email) {
		return userRepo.findByEmail(email).orElse(null);
	}

	@Override
	public User getUserByCin(String cin) {
		return userRepo.findByCin(cin).orElse(null);
	}

	@Override
	public User addUser(User user) {
		return userRepo.save(user);
	}

	@Override
	public void deleteUser(long id) {
		userRepo.deleteById(id);

	}

	@Override
	@Transactional
	public User updateUser(User user) {
		User oldUser = userRepo.findById(user.getId()).orElse(null);
		if (user.getEmail() != null && !(EMPTY_STRING.equals(user.getEmail().trim()))
				&& !(oldUser.getEmail().equals(user.getEmail()))) {
			oldUser.setEmail(user.getEmail());
		}
		if (user.getPhone() != null && user.getPhone() != 0L && oldUser.getPhone() != user.getPhone()) {
			oldUser.setPhone(user.getPhone());
		}
		if (user.getAdresse() != null && !(EMPTY_STRING.equals(user.getAdresse().trim()))
				&& !(oldUser.getAdresse().equals(user.getAdresse()))) {
			oldUser.setAdresse(user.getAdresse());
		}
		if (user.getJob() != null && !(EMPTY_STRING.equals(user.getJob().trim())) && !(oldUser.getJob().equals(user.getJob()))) {
			oldUser.setAdresse(user.getJob());
		}
		if (user.getSalaireNet() != 0.0f && oldUser.getSalaireNet() != user.getSalaireNet()) {
			oldUser.setSalaireNet(user.getSalaireNet());
		}

		userRepo.save(oldUser);
		return oldUser;
	}

	@Override
	@Transactional
	public User signUser(long id) {
		User user = this.getUserById(id);
		user.setIsSigned(Boolean.TRUE);
		String pwd = generatePassword();
		user.setPassword(encoder.encode(pwd));
		try {
			emailService.sendSimpleMessage(user.getEmail(), WELCOME_TO_SOFTIB,
					HELLO + user.getFirstName() + ESPACE + user.getLastName() +SEPARATOR
							+NEW_LINE
							+NEW_LINE
							+YOU_CAN_FIND_BELOW_YOUR_CREDENTIALS
							+NEW_LINE + USERNAME + user.getUsername()
							+NEW_LINE+PASSWORD + pwd 
							+NEW_LINE 
							+NEW_LINE 
							+CORDIALEMENT);
		} catch (SendFailedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return user;
	}

	private String generatePassword() {
		StringBuilder result = new StringBuilder(PASSWORD_LENGTH);

		// at least 2 chars (lowercase)
		String strLowerCase = generateRandomString(CHAR_LOWERCASE, 2);
		System.out.format(FORMAT, CHARS_LOWERCASE, strLowerCase);
		result.append(strLowerCase);

		// at least 2 chars (uppercase)
		String strUppercaseCase = generateRandomString(CHAR_UPPERCASE, 2);
		System.out.format(FORMAT, CHARS_UPPERCASE, strUppercaseCase);
		result.append(strUppercaseCase);

		// at least 2 digits
		String strDigit = generateRandomString(DIGIT, 4);
		System.out.format(FORMAT, DIGITS, strDigit);
		result.append(strDigit);

		

		

		String password = result.toString();
		// combine all
		System.out.format(FORMAT, PASSWORD2, password);
		// shuffle again
		System.out.format(FORMAT, FINAL_PASSWORD, shuffleString(password));
		System.out.format(_20S_S_N_N, PASSWORD_LENGTH2, password.length());

		return password;
	}

	private static String generateRandomString(String input, int size) {

		if (input == null || input.length() <= 0)
			throw new IllegalArgumentException(INVALID_INPUT);
		if (size < 1)
			throw new IllegalArgumentException(INVALID_SIZE);

		StringBuilder result = new StringBuilder(size);
		for (int i = 0; i < size; i++) {
			// produce a random order
			int index = random.nextInt(input.length());
			result.append(input.charAt(index));
		}
		return result.toString();
	}

	public static String shuffleString(String input) {
		List<String> result = Arrays.asList(input.split(EMPTY_STRING));
		Collections.shuffle(result);
		// java 8
		return result.stream().collect(Collectors.joining());
	}

	@Override
	public User getUserByUsername(String username) {
		User user = userRepo.findByUsername(username).orElse(null);
		return user;
	}

	@Override
	@Transactional
	public User banUser(String username, String banRaison) {
		User user = this.getUserByUsername(username);
		user.setIsBanned(true);
		user.setBanRaison(banRaison);

		return user;
	}

	@Override
	public List<User> getClients() {
		List<User> clients = userRepo.findClients(ERole.ROLE_CLIENT);
		return clients;
	}

	@Override
	public Compte deleteAutoUser() {
		return userRepo.deleteAutoUser();

	}

}
