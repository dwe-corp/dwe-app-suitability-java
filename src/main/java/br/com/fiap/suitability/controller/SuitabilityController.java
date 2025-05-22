package br.com.fiap.suitability.controller;

import br.com.fiap.suitability.dto.AvaliacaoRequest;
import br.com.fiap.suitability.model.PerfilSuitability;
import br.com.fiap.suitability.service.SuitabilityService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/suitability")
public class SuitabilityController {

    @Autowired
    private SuitabilityService service;

    @PostMapping
    public ResponseEntity<Map<String, Object>> avaliar(@RequestBody @Valid AvaliacaoRequest req) {
        return ResponseEntity.ok(service.avaliarPerfil(req));
    }

    @GetMapping
    public List<PerfilSuitability> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PerfilSuitability> buscar(@PathVariable Long id) {
        return service.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
