package br.com.fiap.suitability.controller;

import br.com.fiap.suitability.dto.AvaliacaoRequest;
import br.com.fiap.suitability.model.PerfilSuitability;
import br.com.fiap.suitability.service.SuitabilityService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/suitability")
public class SuitabilityController {

    private static final Logger logger = LoggerFactory.getLogger(SuitabilityController.class);

    @Autowired
    private SuitabilityService service;

    @PostMapping
    public ResponseEntity<Map<String, Object>> avaliar(@RequestBody @Valid AvaliacaoRequest req) {
        logger.info("Recebida requisição de avaliação de perfil para: {}", req.getEmail());
        Map<String, Object> resultado = service.avaliarPerfil(req);
        logger.info("Perfil avaliado com sucesso para: {}", req.getEmail());
        return ResponseEntity.ok(resultado);
    }

    @GetMapping
    public List<PerfilSuitability> listar() {
        logger.info("Listando todos os perfis de suitability.");
        return service.listar();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PerfilSuitability> buscar(@PathVariable Long id) {
        logger.info("Buscando perfil de suitability com ID: {}", id);
        return service.buscarPorId(id)
                .map(perfil -> {
                    logger.info("Perfil encontrado: ID {}", id);
                    return ResponseEntity.ok(perfil);
                })
                .orElseGet(() -> {
                    logger.warn("Perfil não encontrado: ID {}", id);
                    return ResponseEntity.notFound().build();
                });
    }

    @GetMapping(params = "email")
    public ResponseEntity<List<PerfilSuitability>> buscarPorEmail(@RequestParam String email) {
        logger.info("Buscando perfis de suitability para o e-mail: {}", email);
        List<PerfilSuitability> perfis = service.buscarPorEmail(email);

        if (perfis.isEmpty()) {
            logger.warn("Nenhum perfil encontrado para o e-mail: {}", email);
            return ResponseEntity.notFound().build();
        }

        logger.info("Perfis encontrados para o e-mail: {}", email);
        return ResponseEntity.ok(perfis);
    }
}
