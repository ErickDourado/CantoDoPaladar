package com.canto.paladar.dto.pedido;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.io.Serial;
import java.io.Serializable;

public record PedidoRequest(
        @PositiveOrZero(message = "Subtotal não pode ser negativo")
        @NotNull(message = "Subtotal não pode ser nulo")
        Double subtotal,

        @PositiveOrZero(message = "Frete não pode ser negativo")
        @NotNull(message = "Frete não pode ser nulo")
        Double frete,

        @PositiveOrZero(message = "Total não pode ser negativo")
        @NotNull(message = "Total não pode ser nulo")
        Double total
) implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

}
