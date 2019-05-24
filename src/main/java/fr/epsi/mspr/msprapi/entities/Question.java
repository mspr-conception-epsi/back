package fr.epsi.mspr.msprapi.entities;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;

import fr.epsi.mspr.msprapi.enums.QuestionType;

@Entity
public class Question {

	@Id
	private int id;
	private String label;
	private String content;
	private Form form;
	private int sort;
	@Enumerated(EnumType.STRING)
    private QuestionType type;

}
