package br.com.bancosimplificado.math.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import br.com.bancosimplificado.math.enums.UserRole;
import br.com.bancosimplificado.math.exception.type.DataIntegrityViolationException;
import br.com.bancosimplificado.math.exception.type.ObjectNotFoundException;
import br.com.bancosimplificado.math.mensageria.RabbitMQService;
import br.com.bancosimplificado.math.model.Transactions;
import br.com.bancosimplificado.math.model.Users;
import br.com.bancosimplificado.math.repository.TransactionRepository;
import br.com.bancosimplificado.math.repository.UserRepository;

@Service
public class TransactionService  {
	
	@Autowired
	private TransactionRepository transactionRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RabbitMQService rabbitMQService;
	
	private RestTemplate restTemplate = new RestTemplate();
	
	public List<Transactions> allTransactions() {
		return transactionRepository.findAll();
	}
	
	public List<Transactions> allTransactionsOfUser(UUID userId) {
        return transactionRepository.findByPayerIdOrPayeeId(userId, userId);
    }
	
	public Transactions makePayment(Transactions transaction) throws Exception {
		Optional<Users> payer = userRepository.findById(transaction.getPayer().getId());
		Optional<Users> payee = userRepository.findById(transaction.getPayee().getId());
		
		if(payer.get().getRole() != UserRole.USER) {
			throw new DataIntegrityViolationException("Lojistas não podem fazer pagamentos!");
		}
		
		if(transaction.getValue().compareTo(BigDecimal.ZERO) <= 0) {
	        throw new DataIntegrityViolationException("O valor da transação deve ser maior que 0!");
	    }
		
		if(payer.get().getBalance() == null || payer.get().getBalance().compareTo(transaction.getValue()) < 0) {
			throw new DataIntegrityViolationException("Você não tem saldo o suficiente para realizar o pagamento!");
		}
		
		if(!isTransactionAuthorized()) {
            throw new DataIntegrityViolationException("Transação não autorizada!");
        }
		
        payer.get().setBalance(payer.get().getBalance().subtract(transaction.getValue()));
        userRepository.save(payer.get());
        
        rabbitMQService.sendNotificationMessage(transaction.getPayer().getEmail(),"Notificação de Pagamento","O pagamento para "+
        transaction.getPayee().getFirstName() + transaction.getPayee().getLastName() +" realizado com sucesso!");
        
        payee.get().setBalance(payee.get().getBalance().add(transaction.getValue()));
        userRepository.save(payee.get());
        
        rabbitMQService.sendNotificationMessage(transaction.getPayee().getEmail(),"Notificação de Pagamento","Você recebeu um pagamento de"+
        transaction.getPayer().getFirstName()+ transaction.getPayer().getLastName() +"!");
        
		return transactionRepository.saveAndFlush(transaction);
	}
	
	public void revertPayment(UUID paymentId) {
		Optional<Transactions> transaction = transactionRepository.findById(paymentId);
		Optional<Users> payer = userRepository.findById(transaction.get().getPayer().getId());
		Optional<Users> payee = userRepository.findById(transaction.get().getPayee().getId());
		
		if(payer.get().getId() != transaction.get().getPayer().getId() && payee.get().getId() != transaction.get().getPayee().getId()) {
			throw new ObjectNotFoundException("O Usuário e Lojista devem ser os mesmos deste pagamento!");
		}
		
        payee.get().setBalance(payee.get().getBalance().subtract(transaction.get().getValue()));
        userRepository.save(payee.get());
        
        payer.get().setBalance(payer.get().getBalance().add(transaction.get().getValue()));
        userRepository.save(payer.get());
	}
	
	private boolean isTransactionAuthorized() {
        final String AUTHORIZER_URL = "https://run.mocky.io/v3/8fafdd68-a090-496f-8c9a-3442cf30dae6";
        
        try {
            ResponseEntity<Map> response = restTemplate.getForEntity(AUTHORIZER_URL, Map.class);
            System.out.println(response);
            if (response.getStatusCode() == HttpStatus.OK && "Autorizado".equals(response.getBody().get("message"))) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
        	throw new RuntimeException("Não foi possível contatar o serviço de autorização!");
        }
    }
}
