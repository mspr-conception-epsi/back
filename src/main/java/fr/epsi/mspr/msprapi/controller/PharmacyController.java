package fr.epsi.mspr.msprapi.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import fr.epsi.mspr.msprapi.entities.Pharmacy;
import fr.epsi.mspr.msprapi.entities.service.PharmacyService;
import fr.epsi.mspr.msprapi.repository.PharmacyRepository;
import io.swagger.annotations.ApiOperation;


@RestController
public class PharmacyController {

	@Autowired
	private PharmacyRepository pharmacyRepository;
	
	@Autowired
	private PharmacyService pharmacyService;

	@ApiOperation(value = "List of pharmacies")
	@GetMapping("/pharmacy")
	public List<Pharmacy> findAll() {
		return pharmacyRepository.findAll();
	}

	@ApiOperation(value = "Create new pharmacy")
	@PostMapping("/pharmacy/create")
	public Pharmacy createPharmacy(@Valid @RequestBody Pharmacy pharmacy) {
		pharmacy.setId(0);
		return pharmacyRepository.save(pharmacy);
	}
	
	@ApiOperation(value = "Update a pharmacy")
	@PostMapping("/pharmacy/update")
	public Map<String, String> updatePharmacy(@Valid @RequestBody Pharmacy pharmacy) {
		Map<String, String> msg = new HashMap<>();
		if(pharmacy.getId() > 0) {
			Optional<Pharmacy> option = pharmacyRepository.findById(pharmacy.getId());
			if(option.isPresent()) {
				pharmacyRepository.save(pharmacy);
				msg.put("success", "Pharmacie modifiée");
			} else {
				msg.put("error", "Pharmacie inexistante");
			}
		} else {
			msg.put("error", "Veuillez fournir l'identifiant");
		}
		return msg;
	}
	
	@ApiOperation(value = "Delete pharmacy")
	@PostMapping("/pharmacy/delete")
	public Map<String, String> deletePharmacy(@Valid @RequestBody Pharmacy pharmacy) {
		Map<String, String> msg = new HashMap<>();
		if(pharmacy.getId() > 0) {
			Optional<Pharmacy> option = pharmacyRepository.findById(pharmacy.getId());
			if(option.isPresent()) {
				pharmacyRepository.delete(option.get());
				msg.put("success", "Pharmacie supprimée");
			} else {
				msg.put("error", "Pharmacie inexistante");
			}
		} else {
			msg.put("error", "Veuillez fournir l'identifiant");
		}
		return msg;
	}

	@ApiOperation(value = "List of pharmacies in area size")
	@GetMapping("/pharmacy/{coordLong}/{coordLat}/{areaSize}")
	public List<Pharmacy> findAllInArea(@PathVariable("coordLong") double coordLong, @PathVariable("coordLat") double coordLat, @PathVariable("areaSize") double areaSize) {
		return pharmacyService.getPharmacyList(coordLat, coordLong, areaSize);
	}
}
