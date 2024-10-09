package com.canto.paladar.dto.pedido;

import com.canto.paladar.dto.endereco.EnderecoResponse;
import com.canto.paladar.dto.produto.ProdutoResponse;

import java.io.Serial;
import java.io.Serializable;
import java.util.Set;

public record PedidoResponse(
        Long id,
        Double subtotal,
        Double frete,
        Double total,
        EnderecoResponse endereco,
        Set<ProdutoResponse> produtos
) implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

}
