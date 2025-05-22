package br.com.fiap.suitability.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AvaliacaoRequest {

    @NotBlank(message = "O nome é obrigatório.")
    private String nome;

    @Email(message = "E-mail inválido.")
    @NotBlank(message = "O e-mail é obrigatório.")
    private String email;

    private int respostasConservadoras;
    private int respostasModeradas;
    private int respostasAgressivas;
}
