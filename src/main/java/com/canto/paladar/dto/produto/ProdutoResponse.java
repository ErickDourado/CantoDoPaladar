package com.canto.paladar.dto.produto;

import java.io.Serial;
import java.io.Serializable;

public record ProdutoResponse(
        Long id,
        String nome,
        String descricao,
        Double preco
) implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

}
