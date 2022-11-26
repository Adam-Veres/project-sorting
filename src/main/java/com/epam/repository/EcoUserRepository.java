package com.epam.repository;


import com.epam.model.EcoUser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EcoUserRepository extends CrudRepository<EcoUser, Integer> {
	
	EcoUser findByUsername(String username);
	
}