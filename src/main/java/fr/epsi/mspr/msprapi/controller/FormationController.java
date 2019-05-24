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

import fr.epsi.mspr.msprapi.entities.Formation;
import fr.epsi.mspr.msprapi.repository.FormationRepository;
import io.swagger.annotations.ApiOperation;


@RestController
public class FormationController {

	@Autowired
	private FormationRepository formationRepository;

	@ApiOperation(value = "List of formation")
	@GetMapping("/formation")
	public List<Formation> findAll() {
		return formationRepository.findAll();
	}

	@ApiOperation(value = "Create new formation")
	@PostMapping("/formation/create")
	public Formation createFormation(@Valid @RequestBody Formation formation) {
		return formationRepository.save(formation);
	}
	
	@ApiOperation(value = "Update a formation")
	@PostMapping("/formation/update")
	public Map<String, String> updateFormation(@Valid @RequestBody Formation formation) {
		Map<String, String> msg = new HashMap<>();
		if(formation.getId() > 0) {
			Optional<Formation> form = formationRepository.findById((long)formation.getId());
			if(form.isPresent()) {
				formationRepository.save(formation);
				msg.put("success", "Formation modifiée");
			} else {
				msg.put("error", "Formation inexistante");
			}
		} else {
			msg.put("error", "Veuillez fournir l'identifiant");
		}
		return msg;
	}
	
	@ApiOperation(value = "Delete a formation")
	@PostMapping("/formation/delete")
	public Map<String, String> deleteFormation(@Valid @RequestBody Formation formation) {
		Map<String, String> msg = new HashMap<>();
		if(formation.getId() > 0) {
			Optional<Formation> form = formationRepository.findById((long)formation.getId());
			if(form.isPresent()) {
				formationRepository.delete(formation);
				msg.put("success", "Formation supprimée");
			} else {
				msg.put("error", "Formation inexistante");
			}
		} else {
			msg.put("error", "Veuillez fournir l'identifiant");
		}
		return msg;
	}
}
