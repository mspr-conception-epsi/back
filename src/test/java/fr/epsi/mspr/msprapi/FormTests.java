package fr.epsi.mspr.msprapi;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import fr.epsi.mspr.msprapi.entities.Form;
import fr.epsi.mspr.msprapi.repository.FormRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FormTests {
	
	@Autowired
	private FormRepository formRepository;
	
	@Test
	public void addAndRemoveForm() {
		Form form = new Form();
		try {
			form.setName("junit test");
			Form createdForm = formRepository.save(form);
			formRepository.delete(createdForm);
			assertEquals(form.getName(), createdForm.getName());
		} catch(Exception ex) {
			System.out.println(ex);
			formRepository.delete(form);
		}
	}

}
