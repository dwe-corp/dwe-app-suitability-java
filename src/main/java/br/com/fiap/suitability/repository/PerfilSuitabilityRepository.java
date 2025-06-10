package br.com.fiap.suitability.repository;

import java.util.Optional;

import br.com.fiap.suitability.model.PerfilSuitability;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PerfilSuitabilityRepository extends JpaRepository<PerfilSuitability, Long> {
    Optional<PerfilSuitability> findByEmail(String email);
}
