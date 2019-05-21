package fr.epsi.mspr.msprapi;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import fr.epsi.mspr.msprapi.entities.User;
import fr.epsi.mspr.msprapi.repository.UserRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MsprApiApplicationTests {
	
	@Autowired
	private UserRepository userRepository;
	
	@Test
	public void addAndRemoveUser() {
		User user = new User();
		try {
			user.setAdmin(false);
			user.setName("junit test");
			user.setPassword("junit password");
			User createdUser = userRepository.save(user);
			userRepository.delete(createdUser);
			assertEquals(user.getName(), createdUser.getName());
		} catch(Exception ex) {
			System.out.println(ex);
			userRepository.delete(user);
		}
	}

}
