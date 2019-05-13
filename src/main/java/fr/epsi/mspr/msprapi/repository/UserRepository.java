package fr.epsi.mspr.msprapi.repository;

import org.springframework.stereotype.Repository;

import fr.epsi.mspr.msprapi.entities.User;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}
