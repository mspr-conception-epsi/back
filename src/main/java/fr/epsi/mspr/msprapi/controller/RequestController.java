package fr.epsi.mspr.msprapi.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import fr.epsi.mspr.msprapi.dto.DtoPharmacyIdentifiation;
import fr.epsi.mspr.msprapi.entities.Request;
import fr.epsi.mspr.msprapi.repository.RequestRepository;
import io.swagger.annotations.ApiOperation;

@RestController
public class RequestController {

	@Autowired
	private RequestRepository requestRepository;

	@ApiOperation(value = "List of requests")
	@GetMapping("/request")
	public List<Request> findAll() {
		return requestRepository.findAll();
	}
	
	@ApiOperation(value = "List of requests by pharmacy")
	@PostMapping("/request/bypharmacy")
	public List<Request> findAllPharmacy(@Valid @RequestBody DtoPharmacyIdentifiation dto) {
		return findAll().stream().filter(s -> s.getPharmacy().getId() == dto.getPharmacyId()).collect(Collectors.toList());
	}
	
	@ApiOperation(value = "Create new request")
	@PostMapping("/request/create")
    public Request createRequest(@Valid @RequestBody Request request) {
		request.setId(0);
        return requestRepository.save(request);
    }
	
	@ApiOperation(value = "Update a request")
	@PostMapping("/request/update")
    public Map<String, String> updateRequest(@Valid @RequestBody Request request) {
		Map<String, String> msg = new HashMap<>();
		if(request.getId() > 0) {
			Optional<Request> option = requestRepository.findById(request.getId());
			if(option.isPresent()) {
				requestRepository.save(request);
				msg.put("success", "Utilisateur modifié");
			} else {
				msg.put("error", "Utilisateur inexistant");
			}
		} else {
			msg.put("error", "Veuillez fournir l'identifiant");
		}
		return msg;
    }
	
	@ApiOperation(value = "Delete request")
	@PostMapping("/request/delete")
    public Map<String, String> deleteRequest(@Valid @RequestBody Request request) {
		Map<String, String> msg = new HashMap<>();
		if(request.getId() > 0) {
			Optional<Request> option = requestRepository.findById(request.getId());
			if(option.isPresent()) {
				requestRepository.delete(option.get());
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
