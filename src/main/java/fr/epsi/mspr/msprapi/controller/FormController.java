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

import fr.epsi.mspr.msprapi.entities.Form;
import fr.epsi.mspr.msprapi.repository.FormRepository;
import io.swagger.annotations.ApiOperation;


@RestController
public class FormController {

	@Autowired
	private FormRepository formRepository;

	@ApiOperation(value = "List of form")
	@GetMapping("/form")
	public List<Form> findAll() {
		return formRepository.findAll();
	}

	@ApiOperation(value = "Create new form")
	@PostMapping("/form/create")
	public Form createForm(@Valid @RequestBody Form form) {
		return formRepository.save(form);
	}
	
	@ApiOperation(value = "Update a form")
	@PostMapping("/form/update")
	public Map<String, String> updateForm(@Valid @RequestBody Form form) {
		Map<String, String> msg = new HashMap<>();
		if(form.getId() > 0) {
			Optional<Form> option = formRepository.findById(form.getId());
			if(option.isPresent()) {
				formRepository.save(form);
				msg.put("success", "Formulaire modifiée");
			} else {
				msg.put("error", "Formulaire inexistante");
			}
		} else {
			msg.put("error", "Veuillez fournir l'identifiant");
		}
		return msg;
	}
	
	@ApiOperation(value = "Delete a form")
	@PostMapping("/form/delete")
	public Map<String, String> deleteForm(@Valid @RequestBody Form form) {
		Map<String, String> msg = new HashMap<>();
		if(form.getId() > 0) {
			Optional<Form> option = formRepository.findById(form.getId());
			if(option.isPresent()) {
				formRepository.delete(option.get());
				msg.put("success", "Formulaire supprimée");
			} else {
				msg.put("error", "Formulaire inexistante");
			}
		} else {
			msg.put("error", "Veuillez fournir l'identifiant");
		}
		return msg;
	}
}
