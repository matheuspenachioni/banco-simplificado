package br.com.bancosimplificado.math.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.com.bancosimplificado.math.model.Users;
import br.com.bancosimplificado.math.service.UserService;
import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping(value = "user")
@CrossOrigin("*")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@GetMapping
	@Operation(summary = "Lista todos os usuários")
	public ResponseEntity<List<Users>> listAllUsers() {
		List<Users> obj = userService.getAllUsers();
		
		return new ResponseEntity<List<Users>>(obj, HttpStatus.OK);
	}
	
	@GetMapping(value = "/{id}")
	@Operation(summary = "Busca um usuário pelo id")
	public ResponseEntity<Users> findUserById(@PathVariable UUID id) {
		Users obj = userService.findUser(id);
		
		return new ResponseEntity<Users>(obj, HttpStatus.OK);
	}
	
	@PostMapping
	@Operation(summary = "Salva um usuário")
	public ResponseEntity<Users> registerUser(@RequestBody Users user) {
		Users obj = userService.saveUser(user);
		
		return new ResponseEntity<Users>(obj, HttpStatus.OK);
	}
}
