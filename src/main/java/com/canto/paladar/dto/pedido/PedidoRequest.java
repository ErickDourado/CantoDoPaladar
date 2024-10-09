package com.canto.paladar.dto.pedido;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

public record PedidoRequest(
        @PositiveOrZero(message = "Subtotal não pode ser negativo")
        @NotNull(message = "Subtotal não pode ser nulo")
        Double subtotal,

        @PositiveOrZero(message = "Frete não pode ser negativo")
        @NotNull(message = "Frete não pode ser nulo")
        Double frete,

        @PositiveOrZero(message = "Total não pode ser negativo")
        @NotNull(message = "Total não pode ser nulo")
        Double total,

        @Positive(message = "Id do endereço deve ser maior que zero")
        @NotNull(message = "Id do endereço não pode ser nulo")
        Long enderecoId,

        @NotEmpty(message = "Lista de produtos não pode ser vazia")
        @NotNull(message = "Lista de produtos não pode ser nula")
        List<Long> produtosId
) implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

}
