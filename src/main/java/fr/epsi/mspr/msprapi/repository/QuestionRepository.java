package fr.epsi.mspr.msprapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fr.epsi.mspr.msprapi.entities.Formation;
import fr.epsi.mspr.msprapi.entities.Question;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Integer> {

}
