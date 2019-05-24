package fr.epsi.mspr.msprapi.entities;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import fr.epsi.mspr.msprapi.enums.QuestionType;

@Entity
public class Question {

	@Id
	private int id;
	private String label;
	private String content;
	private int sort;
	
	@ManyToOne()
    @JoinColumn(name = "form", referencedColumnName = "id")
	private Form form;
	
	@Enumerated(EnumType.STRING)
    private QuestionType type;

}
