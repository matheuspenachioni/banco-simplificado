package br.com.bancosimplificado.math.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.bancosimplificado.math.model.Users;

@Repository
public interface UserRepository extends JpaRepository<Users, UUID> {

}
