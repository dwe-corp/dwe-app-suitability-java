package br.com.fiap.suitability.repository;

import java.util.Optional;
import java.util.List;


import br.com.fiap.suitability.model.PerfilSuitability;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PerfilSuitabilityRepository extends JpaRepository<PerfilSuitability, Long> {
    Optional<PerfilSuitability> findByEmail(String email);
    List<PerfilSuitability> findAllByEmail(String email);
}
