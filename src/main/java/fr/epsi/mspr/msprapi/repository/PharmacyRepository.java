package fr.epsi.mspr.msprapi.repository;

import org.springframework.stereotype.Repository;

import fr.epsi.mspr.msprapi.entities.Pharmacy;
import fr.epsi.mspr.msprapi.entities.User;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface PharmacyRepository extends JpaRepository<Pharmacy, Long> {

}
