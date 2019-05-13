package fr.epsi.mspr.msprapi.controller;

import java.util.List;

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
	@GetMapping("/user/")
	public List<User> findAll() {
		return userRepository.findAll();
	}
	
	@ApiOperation(value = "Create new user")
	@PostMapping("/user/create")
    public User createUser(@Valid @RequestBody User user) {
        return userRepository.save(user);
    }
}
