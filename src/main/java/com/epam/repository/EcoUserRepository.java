package com.epam.repository;

import com.epam.model.EcoUser;
import com.epam.security.EcoUserRole;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EcoUserRepository extends JpaRepository<EcoUser, Long> {

  Optional<EcoUser> findByUsername(final String username);

  List<EcoUser> findAllByUserRole(final EcoUserRole role);
}
