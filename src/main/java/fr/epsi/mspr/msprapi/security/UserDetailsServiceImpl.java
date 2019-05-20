package fr.epsi.mspr.msprapi.security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import fr.epsi.mspr.msprapi.entities.User;
import fr.epsi.mspr.msprapi.repository.UserRepository;

@Service   // It has to be annotated with @Service.
public class UserDetailsServiceImpl implements UserDetailsService  {
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> user = userRepository.findByName(username);
		if(user.isPresent()) {
			return user.get();
		}
		throw new UsernameNotFoundException("Username: " + username + " not found");
	}
}