package com.canto.paladar.dto.pedido;

import java.io.Serial;
import java.io.Serializable;

public record PedidoResponse(
        Long id,
        Double subtotal,
        Double frete,
        Double total
) implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

}
