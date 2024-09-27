package com.canto.paladar.dto.produto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

import java.io.Serial;
import java.io.Serializable;

public record ProdutoRequest(
        @NotBlank(message = "Nome não pode ser vazio")
        @Size(message = "Nome deve conter entre 5 e 30 caracteres", min = 5, max = 30)
        String nome,

        @NotBlank(message = "Descrição não pode ser vazia")
        @Size(message = "Descrição deve conter entre 10 e 80 caracteres", min = 6, max = 50)
        String descricao,

        @PositiveOrZero(message = "Preço não pode ser negativo")
        @NotNull(message = "Preço não pode ser nulo")
        Double preco
) implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

}
