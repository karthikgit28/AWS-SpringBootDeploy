package com.aws.deploy.awsuserdetails.repository;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.aws.deploy.awsuserdetails.entity.UserDetails;

@Repository
@Transactional
public interface UserRepository extends JpaRepository<UserDetails, Integer>{
	
	//Retrieve user By Name
	public List<UserDetails> findByName(String name);
	
	public Optional<UserDetails> findByNameAndPassword(String name,String password);
	
}
