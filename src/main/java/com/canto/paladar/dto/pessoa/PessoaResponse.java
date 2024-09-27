package com.canto.paladar.dto.pessoa;

import java.io.Serial;
import java.io.Serializable;

public record PessoaResponse(
        Long id,
        String nome,
        String email,
        String celular,
        String senha
) implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

}
