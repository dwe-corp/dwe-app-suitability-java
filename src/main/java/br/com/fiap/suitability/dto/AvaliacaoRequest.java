package br.com.fiap.suitability.dto;

import lombok.Data;

@Data
public class AvaliacaoRequest {

    private int respostasConservadoras;
    private int respostasModeradas;
    private int respostasAgressivas;

}
