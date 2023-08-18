package br.com.bancosimplificado.math.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.bancosimplificado.math.model.Users;

@Repository
public interface UserRepository extends JpaRepository<Users, UUID> {

	Optional<Users> findByDocument(String document);

	Optional<Users> findByEmail(String email);
	
}
