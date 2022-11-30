package com.epam.repository;


import com.epam.model.EcoUser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EcoUserRepository extends CrudRepository<EcoUser, Integer> {

	Optional<EcoUser> findByUsername(String username);
	
}