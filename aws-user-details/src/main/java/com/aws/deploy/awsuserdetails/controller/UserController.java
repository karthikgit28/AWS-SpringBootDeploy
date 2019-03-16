package com.aws.deploy.awsuserdetails.controller;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.aws.deploy.awsuserdetails.customexception.UserNotFoundException;
import com.aws.deploy.awsuserdetails.entity.UserDetails;
import com.aws.deploy.awsuserdetails.repository.UserRepository;

@RestController
@Transactional
public class UserController {

	@Autowired
	private UserRepository userRepo;


	//To Do - Encrypt Password and Decrypt Password
	@PostMapping("/user/createUser")
	public ResponseEntity<UserDetails> createUser(@Valid @RequestBody UserDetails userData) {
		UserDetails userDetails = userRepo.save(userData);
		return new ResponseEntity<UserDetails>(userDetails, HttpStatus.CREATED);
	}

	//Retrieve User with Id
	@GetMapping("/user/retrieveUserById/{id}")
	public UserDetails retrieveUser(@PathVariable Integer id) {
		Optional<UserDetails> userDetail = userRepo.findById(id);
		if(!userDetail.isPresent()) {
			throw new UserNotFoundException("User Details Not Found");
		}
		return userDetail.get();
	}

	//Retrieve All Users
	@GetMapping("/user/retrieveAllUser")
	public List<UserDetails> retrieveAllUser() {
		List<UserDetails> userList = userRepo.findAll();
		return userList;
	}

	//Retrieve User By Name
	@GetMapping("/user/retrieveUser/{name}")
	public List<UserDetails> retrieveUser(@PathVariable String name) {
		List<UserDetails> userList = userRepo.findByName(name);
		return userList;
	}

	//Delete User By Id
	@DeleteMapping("/user/deleteUser/{id}")
	public void deleteUser(@PathVariable Integer id) {
		userRepo.deleteById(id);
	}

	@GetMapping("/user/authenticate")
	public UserDetails authenticateUser(String userName,String password) {
		Optional<UserDetails> data = userRepo.findByNameAndPassword(userName, password);
		if(!data.isPresent()) {
			throw new UserNotFoundException("Authentication Failed");
		}
		UserDetails details = data.get();
		//Update Last Login Name
		details.setLastlogin(new Date());
		userRepo.save(details);
		return data.get();
	}

}
