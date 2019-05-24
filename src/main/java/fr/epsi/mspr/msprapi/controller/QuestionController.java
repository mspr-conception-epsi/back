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

import fr.epsi.mspr.msprapi.entities.Question;
import fr.epsi.mspr.msprapi.enums.QuestionType;
import fr.epsi.mspr.msprapi.repository.QuestionRepository;
import io.swagger.annotations.ApiOperation;


@RestController
public class QuestionController {

	@Autowired
	private QuestionRepository questionRepository;

	@ApiOperation(value = "List of question")
	@GetMapping("/question")
	public List<Question> findAll() {
		return questionRepository.findAll();
	}
	
	@ApiOperation(value = "List of question type")
	@GetMapping("/question/type")
	public QuestionType[] findAllType() {
		return QuestionType.values();
	}

	@ApiOperation(value = "Create new question")
	@PostMapping("/question/create")
	public Question createQuestion(@Valid @RequestBody Question question) {
		return questionRepository.save(question);
	}
	
	@ApiOperation(value = "Update a question")
	@PostMapping("/question/update")
	public Map<String, String> updateQuestion(@Valid @RequestBody Question question) {
		Map<String, String> msg = new HashMap<>();
		if(question.getId() > 0) {
			Optional<Question> option = questionRepository.findById(question.getId());
			if(option.isPresent()) {
				questionRepository.save(question);
				msg.put("success", "Question modifiée");
			} else {
				msg.put("error", "Question inexistante");
			}
		} else {
			msg.put("error", "Veuillez fournir l'identifiant");
		}
		return msg;
	}
	
	@ApiOperation(value = "Delete a question")
	@PostMapping("/question/delete")
	public Map<String, String> deleteQuestion(@Valid @RequestBody Question question) {
		Map<String, String> msg = new HashMap<>();
		if(question.getId() > 0) {
			Optional<Question> option = questionRepository.findById(question.getId());
			if(option.isPresent()) {
				questionRepository.delete(option.get());
				msg.put("success", "Question supprimée");
			} else {
				msg.put("error", "Question inexistante");
			}
		} else {
			msg.put("error", "Veuillez fournir l'identifiant");
		}
		return msg;
	}
}
