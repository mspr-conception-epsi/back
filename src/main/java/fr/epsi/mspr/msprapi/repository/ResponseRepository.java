package fr.epsi.mspr.msprapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fr.epsi.mspr.msprapi.entities.Response;

@Repository
public interface ResponseRepository extends JpaRepository<Response, Integer> {
	
}
