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

import fr.epsi.mspr.msprapi.dto.DtoInResponse;
import fr.epsi.mspr.msprapi.entities.Question;
import fr.epsi.mspr.msprapi.entities.Response;
import fr.epsi.mspr.msprapi.repository.QuestionRepository;
import fr.epsi.mspr.msprapi.repository.ResponseRepository;
import io.swagger.annotations.ApiOperation;


@RestController
public class ReponseControlleur {

	@Autowired
	private ResponseRepository responseRepository;
	@Autowired
	private QuestionRepository questionRepository;

	@ApiOperation(value = "List of response")
	@GetMapping("/response")
	public List<Response> findAll() {
		return responseRepository.findAll();
	}

	@ApiOperation(value = "Create new response")
	@PostMapping("/response/create")
	public Response createResponse(@Valid @RequestBody DtoInResponse response) {
		Optional<Question> option = questionRepository.findById(response.getQuestionId());
		if(option.isPresent()) {
			Question question = option.get();
			Response r = new Response();
			r.setQuestion(question.getId());
			r.setLabel(question.getLabel());
			r.setContent(response.getAnswer());
			return responseRepository.save(r);
		} else {
			return null;
		}
	}
	
	@ApiOperation(value = "Delete a response")
	@PostMapping("/response/delete")
	public Map<String, String> deleteResponse(@Valid @RequestBody Response response) {
		Map<String, String> msg = new HashMap<>();
		if(response.getId() > 0) {
			Optional<Response> option = responseRepository.findById(response.getId());
			if(option.isPresent()) {
				responseRepository.delete(option.get());
				msg.put("success", "Réponse supprimée");
			} else {
				msg.put("error", "Réponse inexistante");
			}
		} else {
			msg.put("error", "Veuillez fournir l'identifiant");
		}
		return msg;
	}
}
