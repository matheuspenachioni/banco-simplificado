package br.com.bancosimplificado.math.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.com.bancosimplificado.math.model.Users;
import br.com.bancosimplificado.math.service.UserService;

@RestController
@RequestMapping(value = "user")
@CrossOrigin("*")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@GetMapping
	public ResponseEntity<List<Users>> listAllUsers() {
		List<Users> obj = userService.getAllUsers();
		
		return new ResponseEntity<List<Users>>(obj, HttpStatus.OK);
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<Users> findUserById(@PathVariable UUID id) {
		Users obj = userService.findUser(id);
		
		return new ResponseEntity<Users>(obj, HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<Users> registerUser(@RequestBody Users user) {
		Users obj = userService.saveUser(user);
		
		return new ResponseEntity<Users>(obj, HttpStatus.OK);
	}
}
