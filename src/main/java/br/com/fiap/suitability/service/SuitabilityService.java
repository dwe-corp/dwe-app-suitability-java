package br.com.fiap.suitability.service;

import br.com.fiap.suitability.dto.AvaliacaoRequest;
import br.com.fiap.suitability.model.PerfilSuitability;
import br.com.fiap.suitability.model.Risco;
import br.com.fiap.suitability.repository.PerfilSuitabilityRepository;
import br.com.fiap.suitability.util.AvaliadorPerfilRisco;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SuitabilityService {

    @Autowired
    private PerfilSuitabilityRepository repository;

    public Map<String, Object> avaliarPerfil(AvaliacaoRequest req) {
        int c = req.getRespostasConservadoras();
        int m = req.getRespostasModeradas();
        int a = req.getRespostasAgressivas();

        Risco risco = AvaliadorPerfilRisco.avaliar(c, m, a);

        PerfilSuitability perfil = new PerfilSuitability();
        perfil.setNome(req.getNome());
        perfil.setEmail(req.getEmail());
        perfil.setRisco(risco);

        repository.save(perfil);

        Map<String, Object> response = new HashMap<>();
        response.put("cliente", perfil);
        response.put("recomendacao", AvaliadorPerfilRisco.recomendarCarteira(risco));
        return response;
    }

    public List<PerfilSuitability> listar() {
        return repository.findAll();
    }

    public Optional<PerfilSuitability> buscarPorId(Long id) {
        return repository.findById(id);
    }
}
