package br.com.fiap.suitability.service;

import br.com.fiap.suitability.dto.AvaliacaoRequest;
import br.com.fiap.suitability.model.PerfilSuitability;
import br.com.fiap.suitability.model.Risco;
import br.com.fiap.suitability.repository.PerfilSuitabilityRepository;
import br.com.fiap.suitability.util.AvaliadorPerfilRisco;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SuitabilityService {

    private static final Logger logger = LoggerFactory.getLogger(SuitabilityService.class);

    @Autowired
    private PerfilSuitabilityRepository repository;

    public Map<String, Object> avaliarPerfil(AvaliacaoRequest req) {
        logger.info("Iniciando avaliação de perfil para: {}", req.getEmail());

        int c = req.getRespostasConservadoras();
        int m = req.getRespostasModeradas();
        int a = req.getRespostasAgressivas();

        logger.debug("Respostas recebidas - Conservador: {}, Moderado: {}, Agressivo: {}", c, m, a);

        Risco risco = AvaliadorPerfilRisco.avaliar(c, m, a);
        logger.info("Perfil de risco avaliado: {}", risco);

        Optional<PerfilSuitability> existente = repository.findByEmail(req.getEmail());
        PerfilSuitability perfil;

        if (existente.isPresent()) {
            perfil = existente.get();
            perfil.setNome(req.getNome());
            perfil.setRisco(risco);
            logger.info("Perfil existente encontrado para {}. Atualizando dados...", req.getEmail());
        } else {
            perfil = new PerfilSuitability();
            perfil.setNome(req.getNome());
            perfil.setEmail(req.getEmail());
            perfil.setRisco(risco);
            logger.info("Criando novo perfil para {}", req.getEmail());
        }

        repository.save(perfil);
        logger.info("Perfil salvo com sucesso para: {}", req.getEmail());

        Map<String, Object> response = new HashMap<>();
        response.put("cliente", perfil);
        response.put("recomendacao", AvaliadorPerfilRisco.recomendarCarteira(risco));

        return response;
    }

    public List<PerfilSuitability> listar() {
        logger.info("Listando todos os perfis de suitability.");
        return repository.findAll();
    }

    public Optional<PerfilSuitability> buscarPorId(Long id) {
        logger.info("Buscando perfil de suitability por ID: {}", id);
        return repository.findById(id);
    }

    public List<PerfilSuitability> buscarPorEmail(String email) {
        logger.info("Buscando perfis de suitability por e-mail: {}", email);
        return repository.findAllByEmail(email);
    }
}
