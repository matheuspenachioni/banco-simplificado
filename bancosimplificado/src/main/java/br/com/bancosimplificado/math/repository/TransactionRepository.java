package br.com.bancosimplificado.math.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.bancosimplificado.math.model.Transactions;

@Repository
public interface TransactionRepository extends JpaRepository<Transactions, UUID> {

}
