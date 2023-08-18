package br.com.bancosimplificado.math.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.bancosimplificado.math.exception.type.DataIntegrityViolationException;
import br.com.bancosimplificado.math.exception.type.ObjectNotFoundException;
import br.com.bancosimplificado.math.model.Users;
import br.com.bancosimplificado.math.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	public List<Users> getAllUsers() {
		return userRepository.findAll();
	}
	
	public Users findUser(UUID id) {
		return userRepository.findById(id).orElseThrow(() ->  new ObjectNotFoundException("O usuário não foi encontrado! ID: "+ id));
	}
	
	public Users saveUser(Users user) {
		validateEmailAndDocument(user);
		encryptPassword(user);
		
		return userRepository.saveAndFlush(user);
	}
	
	public void validateEmailAndDocument(Users user) {
		Optional<Users> objEmail = userRepository.findByEmail(user.getEmail());
		
		if(objEmail.isPresent() && objEmail.get().getId() != user.getId()) {
			throw new DataIntegrityViolationException("Já existe um usuário com o e-mail: "+ user.getEmail());
		}
		
		Optional<Users> objDocument = userRepository.findByDocument(user.getDocument());
		
		if(objDocument.isPresent() && objDocument.get().getId() != user.getId()) {
			throw new DataIntegrityViolationException("Já existe um usuário com o CPF/CNPJ: "+ user.getDocument());
		}
	}
	
	public void encryptPassword(Users user){
        BCryptPasswordEncoder encrypt = new BCryptPasswordEncoder();
        String encryptedPassword = null;
        
        if (user.getId() == null) {
            encryptedPassword = encrypt.encode(user.getPassword());
            user.setPassword(encryptedPassword);
        } else {
            if (!userRepository.findById(user.getId()).get().getPassword().equals(user.getPassword())) {
                encryptedPassword = encrypt.encode(user.getPassword());
                user.setPassword(encryptedPassword);
            }
        }
    }
	
}
