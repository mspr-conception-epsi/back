package fr.epsi.mspr.msprapi.controller;

import java.util.List;

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
	
	@ApiOperation(value = "Delete a formation")
	@PostMapping("/formation/delete")
	public void deleteFormation(@Valid @RequestBody Formation formation) {
		formationRepository.delete(formation);
	}
}
