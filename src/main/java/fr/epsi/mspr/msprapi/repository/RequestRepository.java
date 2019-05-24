package fr.epsi.mspr.msprapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fr.epsi.mspr.msprapi.entities.Request;

@Repository
public interface RequestRepository extends JpaRepository<Request, Integer> {

}
