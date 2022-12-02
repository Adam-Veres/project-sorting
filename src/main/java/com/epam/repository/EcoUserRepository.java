package com.epam.repository;


import com.epam.model.EcoUser;
import com.epam.security.EcoUserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EcoUserRepository extends JpaRepository<EcoUser, Long> {

	Optional<EcoUser> findByUsername(String username);

	List<EcoUser> findAllByUserRole(EcoUserRole role);
}