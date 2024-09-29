package com.canto.paladar.dto.endereco;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.io.Serial;
import java.io.Serializable;

public record EnderecoRequest(
        @NotBlank(message = "Cep não pode ser vazio")
        @Size(message = "Cep deve conter 8 dígitos", min = 8, max = 8)
        String cep,

        @NotBlank(message = "Logradouro não pode ser vazio")
        @Size(message = "Logradouro deve conter entre 3 e 60 caracteres", min = 3, max = 60)
        String logradouro,

        @NotBlank(message = "Complemento não pode ser vazio")
        @Size(message = "Complemento deve conter entre 1 e 40 caracteres", min = 1, max = 40)
        String complemento,

        @NotBlank(message = "Número não pode ser vazio")
        @Size(message = "Número deve conter entre 1 e 10 dígitos", min = 1, max = 10)
        String numero,

        @NotBlank(message = "Bairro não pode ser vazio")
        @Size(message = "Bairro deve conter entre 3 e 40 caracteres", min = 3, max = 40)
        String bairro,

        @NotBlank(message = "Cidade não pode ser vazio")
        @Size(message = "Cidade deve conter entre 3 e 40 caracteres", min = 3, max = 40)
        String cidade,

        @NotBlank(message = "UF não pode ser vazio")
        @Size(message = "UF deve conter 2 caracteres", min = 2, max = 2)
        String uf
) implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

}
