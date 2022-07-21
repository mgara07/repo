package tn.esprit.softib.service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.esprit.softib.entity.Compte;
import tn.esprit.softib.entity.ConfirmationMessage;
import tn.esprit.softib.entity.FormByUserStat;
import tn.esprit.softib.entity.Formulaire;
import tn.esprit.softib.entity.Role;
import tn.esprit.softib.entity.User;
import tn.esprit.softib.enums.ERole;
import tn.esprit.softib.enums.FormStatus;
import tn.esprit.softib.enums.Gender;
import tn.esprit.softib.enums.Nature;
import tn.esprit.softib.enums.Status;
import tn.esprit.softib.repository.FormulaireRepository;
import tn.esprit.softib.repository.RoleRepository;
import tn.esprit.softib.repository.UserRepository;

@Service
public class FormulaireServiceImpl implements IFormulaireService {

	private static final String SEPARATOR = "-";
	private static final String NUMBERS = "0123456789";
	private static final String SPACE = " ";
	private static final String PROBLEM_ENCOUNTRED = "problem encountred";
	private static final String ERROR_ROLE_IS_NOT_FOUND = "Error: Role is not found.";
	private static final String SUCCEFULLY_CREATED_FOR_USER = " succefully created for user: ";
	private static final String ACCOUNT = "Account ";
	private static final String ALREADY_HAS_ACCOUNT = " already has account ";
	private static final String USER = "user ";
	@Autowired
	FormulaireRepository formulaireRepository;
	@Autowired
	RoleRepository roleRepository;
	
	@Autowired
	ICompteService compteService;
	@Autowired
	IUserService userService;

	@Override
	public List<Formulaire> getAllFormulaires() {
		return formulaireRepository.findAll();
	}

	@Override
	public Formulaire getFormulaireById(long id) {
		return formulaireRepository.findById(id).orElse(null);
	}

	@Override
	public Formulaire getFormulaireByEmail(String email) {
		return formulaireRepository.findByEmail(email).orElse(null);
	}

	@Override
	public Formulaire addFormulaire(Formulaire formulaire) {
		return formulaireRepository.save(formulaire);
	}

	@Override
	public void deleteFormulaire(long id) {
		formulaireRepository.deleteById(id);

	}

	@Override
	@Transactional
	public Formulaire updateFormulaire(Formulaire formulaire) {
		Formulaire oldFormulaire = formulaireRepository.findById(formulaire.getId()).orElse(null);
		if (oldFormulaire != null) {
			if (formulaire.getFirstName() != null && !("".equals(oldFormulaire.getFirstName().trim()))
					&& !(oldFormulaire.getFirstName().equals(formulaire.getFirstName()))) {
				oldFormulaire.setFirstName(formulaire.getFirstName());
			}
			if (formulaire.getLastName() != null && !("".equals(oldFormulaire.getLastName().trim()))
					&& !(oldFormulaire.getLastName().equals(formulaire.getLastName()))) {
				oldFormulaire.setLastName(formulaire.getLastName());
			}
			if (formulaire.getPhone() != null && !("".equals(oldFormulaire.getPhone()))
					&& !(oldFormulaire.getPhone().equals(formulaire.getPhone()))) {
				oldFormulaire.setPhone(formulaire.getPhone());
			}
			if (formulaire.getGender() != null && !("".equals(oldFormulaire.getGender()))
					&& !(oldFormulaire.getGender().equals(formulaire.getGender()))) {
				oldFormulaire.setGender(formulaire.getGender());
			}
			if (formulaire.getAdresse() != null && !("".equals(oldFormulaire.getAdresse().trim()))
					&& !(oldFormulaire.getAdresse().equals(formulaire.getAdresse()))) {
				oldFormulaire.setAdresse(formulaire.getAdresse());
			}
			if (formulaire.getEmail() != null && !("".equals(oldFormulaire.getEmail().trim()))
					&& !(oldFormulaire.getEmail().equals(formulaire.getEmail()))) {
				oldFormulaire.setEmail(formulaire.getEmail());
			}
			if (formulaire.getNatureCompte() != null && !("".equals(oldFormulaire.getNatureCompte()))
					&& !(oldFormulaire.getNatureCompte().equals(formulaire.getNatureCompte()))) {
				oldFormulaire.setNatureCompte(formulaire.getNatureCompte());
			}
			if (formulaire.getSalaireNet() != 0.0f && oldFormulaire.getSalaireNet() != formulaire.getSalaireNet()) {
				oldFormulaire.setSalaireNet(formulaire.getSalaireNet());
			}
			if (formulaire.getJob() != null && !("".equals(oldFormulaire.getJob().trim()))
					&& !(oldFormulaire.getJob().equals(formulaire.getJob()))) {
				oldFormulaire.setJob(formulaire.getJob());
			}
			if (formulaire.getCin() != null) {
				oldFormulaire.setCin(formulaire.getCin());
			}
			if (formulaire.getType() != null) {
				oldFormulaire.setType(formulaire.getType());
			}
			formulaireRepository.save(oldFormulaire);
			return oldFormulaire;
		}
		return null;
	}

	/**
	 * 
	 */
	@Override
	@Transactional
	public ConfirmationMessage confirmFormulaire(long id) {
		ConfirmationMessage confMsg = new ConfirmationMessage();
		Formulaire formulaire = getFormulaireById(id);
		User user = userService.getUserByEmail(formulaire.getEmail());
		
		if (user != null && !checkNatureForUser(formulaire.getNatureCompte(), user)) {
			confMsg.setStatus(Status.KO);
			confMsg.setMessage(USER+user.getUsername()+ALREADY_HAS_ACCOUNT+formulaire.getNatureCompte().toString());
			formulaire.setFormStatus(FormStatus.REJECTED);
			return confMsg;
		}else if (user != null && checkNatureForUser(formulaire.getNatureCompte(), user)) {
			mapFormToAccount(formulaire, user);
			confMsg.setStatus(Status.OK);
			confMsg.setMessage(ACCOUNT+formulaire.getNatureCompte().toString()+SUCCEFULLY_CREATED_FOR_USER+user.getUsername());
			formulaire.setFormStatus(FormStatus.CONFIRMED);
			formulaire.setUser(user);
			return confMsg;
		} else {
			if (user == null) {
				User newUser = mapFormulaireToUser(formulaire);
				Role userRole = roleRepository.findByName(ERole.ROLE_CLIENT)
						.orElseThrow(() -> new RuntimeException(ERROR_ROLE_IS_NOT_FOUND));
				Set<Role> roles = new HashSet<>();
				roles.add(userRole);
				newUser.setRoles(roles);
				newUser = userService.addUser(newUser);				
				Compte compte = mapFormToAccount(formulaire,newUser);	
				confMsg.setStatus(Status.OK);
				confMsg.setMessage(ACCOUNT+formulaire.getNatureCompte().toString()+SUCCEFULLY_CREATED_FOR_USER+newUser.getUsername());
				formulaire.setFormStatus(FormStatus.CONFIRMED);
				formulaire.setUser(newUser);
				return confMsg;
			}
		}
		confMsg.setStatus(Status.KO);
		confMsg.setMessage(PROBLEM_ENCOUNTRED);
		return confMsg;
	}

	private Compte mapFormToAccount(Formulaire formulaire, User user) {
		Compte compte = new Compte();
		compte.setNomComplet(formulaire.getLastName() + SPACE + formulaire.getFirstName());
		compte.setNatureCompte(formulaire.getNatureCompte());
		compte.setIban(generateRandomCode(30));
		compte.setCodeBic(generateRandomCode(5));
		compte.setSolde(new BigDecimal(0));
		compte.setUser(user);
		return compteService.addCompte(compte);
		
	}

	private String generateRandomCode(int length) {
		String numbers = NUMBERS;
		StringBuilder sb = new StringBuilder();
		Random random = new Random();
		for (int i = 0; i < length; i++) {
			int index = random.nextInt(numbers.length());
			char randomChar = numbers.charAt(index);
			sb.append(randomChar);
		}

		String randomString = sb.toString();
		return randomString;
	}

	private boolean checkNatureForUser(Nature natureCompte, User user) {
		List<Compte> comptes = user.getComptes();
		if (comptes != null & comptes.size() != 0) {
			 Optional<Compte> compte = comptes.stream()
					.filter(c -> c.getNatureCompte().toString().equals(natureCompte.toString())).findFirst();
			if (compte != null) {
				return false;
			}
		}
		return true;
	}

	private User mapFormulaireToUser(Formulaire formulaire) {
		User user = new User();
		user.setCin(formulaire.getCin());
		user.setFirstName(formulaire.getFirstName());
		user.setLastName(formulaire.getLastName());
		user.setUsername(formulaire.getCin() + SEPARATOR + formulaire.getLastName() + SEPARATOR + formulaire.getFirstName());
		user.setPhone(formulaire.getPhone());
		user.setGender(formulaire.getGender());
		user.setAdresse(formulaire.getAdresse());
		user.setEmail(formulaire.getEmail());
		user.setSalaireNet(formulaire.getSalaireNet());
		user.setJob(formulaire.getJob());
		user.setType(formulaire.getType());
		user.setIsSigned(false);
		user.setIsBanned(false);
		Date date = new Date();
		user.setCreationDate(date);
		
		return user;
	}

	@Override
	public List<FormByUserStat> getUserFormsStats() {
		List<FormByUserStat> stats = new ArrayList<FormByUserStat>();
		//List<User> users = userService.getClients();
		List<User> clients = new ArrayList<>();
		for (User user : userService.getAllUsers()) {
			if (user.getRoles().size()==1
					& user.getRoles().iterator().next().getName().equals(ERole.ROLE_CLIENT)) {
				clients.add(user);
			}
		}
		for (User user : clients) {
			FormByUserStat stat = new FormByUserStat();
			stat.setUsername(user.getUsername());
			int pending = formulaireRepository.findFormsByStatus(user.getCin(), FormStatus.PENDING);
			stat.setPending(pending);
			int confirmed = formulaireRepository.findFormsByStatus(user.getCin(), FormStatus.CONFIRMED);
			stat.setConfirmed(confirmed);
			int rejected = formulaireRepository.findFormsByStatus(user.getCin(), FormStatus.REJECTED);
			stat.setRejected(rejected);
			stats.add(stat);
		}

		return stats;
	}

	@Override
	@Transactional
	public Formulaire rejectFormulaire(long id) {
		Formulaire formulaire = getFormulaireById(id);
		formulaire.setFormStatus(FormStatus.REJECTED);
		return formulaire;
	}

}
