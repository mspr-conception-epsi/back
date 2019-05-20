package fr.epsi.mspr.msprapi.repository;

import org.springframework.stereotype.Repository;

import fr.epsi.mspr.msprapi.entities.User;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findByName(String name);

	Optional<User> findByToken(String token);

}
