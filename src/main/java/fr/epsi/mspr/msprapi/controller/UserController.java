package fr.epsi.mspr.msprapi.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import fr.epsi.mspr.msprapi.entities.User;
import fr.epsi.mspr.msprapi.repository.UserRepository;
import io.swagger.annotations.ApiOperation;

@RestController
public class UserController {

	@Autowired
	private UserRepository userRepository;

	@ApiOperation(value = "List of users")
	@GetMapping("/user")
	public List<User> findAll() {
		return userRepository.findAll();
	}
	
	@ApiOperation(value = "Create new user")
	@PostMapping("/user/create")
    public User createUser(@Valid @RequestBody User user) {
        return userRepository.save(user);
    }
	
	@ApiOperation(value = "Update a user")
	@PostMapping("/user/update")
    public Map<String, String> updateUser(@Valid @RequestBody User user) {
		Map<String, String> msg = new HashMap<>();
		if(user.getId() > 0) {
			Optional<User> option = userRepository.findById(user.getId());
			if(option.isPresent()) {
				userRepository.save(user);
				msg.put("success", "Utilisateur modifié");
			} else {
				msg.put("error", "Utilisateur inexistant");
			}
		} else {
			msg.put("error", "Veuillez fournir l'identifiant");
		}
		return msg;
    }
	
	@ApiOperation(value = "Delete user")
	@PostMapping("/user/delete")
    public Map<String, String> deleteUser(@Valid @RequestBody User user) {
		Map<String, String> msg = new HashMap<>();
		if(user.getId() > 0) {
			Optional<User> option = userRepository.findById(user.getId());
			if(option.isPresent()) {
				userRepository.delete(option.get());
				msg.put("success", "Utilisateur supprimé");
			} else {
				msg.put("error", "Utilisateur inexistant");
			}
		} else {
			msg.put("error", "Veuillez fournir l'identifiant");
		}
		return msg;
    }
}
