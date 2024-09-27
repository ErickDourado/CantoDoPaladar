package com.canto.paladar.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.io.Serial;
import java.io.Serializable;

public record PessoaRequest(
        @NotBlank(message = "Nome não pode ser vazio")
        @Size(message = "Nome deve conter entre 5 e 30 caracteres", min = 5, max = 30)
        String nome,

        @NotBlank(message = "Email não pode ser vazio")
        @Size(message = "Email deve conter entre 6 e 50 caracteres", min = 6, max = 50)
        @Email(message = "Email inválido")
        String email,

        @NotBlank(message = "Celular não pode ser vazio")
        @Size(message = "Celular deve conter 11 caracteres", min = 11, max = 11)
        String celular,

        @NotBlank(message = "Senha não pode ser vazia")
        @Size(message = "Senha deve conter entre 8 e 30 caracteres", min = 8, max = 30)
        String senha
) implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

}
