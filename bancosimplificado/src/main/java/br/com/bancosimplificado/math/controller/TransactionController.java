package br.com.bancosimplificado.math.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.bancosimplificado.math.model.Transactions;
import br.com.bancosimplificado.math.service.TransactionService;
import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping(value = "transaction")
@CrossOrigin("*")
public class TransactionController {
	
	@Autowired
	private TransactionService transactionService;
	
	@GetMapping
	@Operation(summary = "Lista todos os pagamentos")
	public ResponseEntity<List<Transactions>> findAllPayments() {
		List<Transactions> obj = transactionService.allTransactions();
		
		return new ResponseEntity<List<Transactions>>(obj, HttpStatus.OK);
	}
	
	@GetMapping(value = "/user/{userId}")
	@Operation(summary = "Lista todos os pagamentos de um usuário")
	public ResponseEntity<List<Transactions>> findAllPaymentsOfUser(@PathVariable UUID userId) {
		List<Transactions> obj = transactionService.allTransactionsOfUser(userId);
		
		return new ResponseEntity<List<Transactions>>(obj, HttpStatus.OK);
	}
	
	@PostMapping
	@Operation(summary = "Faz um pagamento")
	public ResponseEntity<Transactions> makePayment(@RequestBody Transactions transaction) throws Exception {
		Transactions obj = transactionService.makePayment(transaction);
		
		return new ResponseEntity<Transactions>(obj, HttpStatus.CREATED);
	}
	
	@PostMapping(value = "/revert/{id}")
	@Operation(summary = "Reverte um pagamento")
	public void revertPayment(@PathVariable UUID paymentId) {
		transactionService.revertPayment(paymentId);
	}
	
}
