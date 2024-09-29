package com.canto.paladar.dto.endereco;

import java.io.Serial;
import java.io.Serializable;

public record EnderecoResponse(
        Long id,
        String cep,
        String logradouro,
        String complemento,
        String numero,
        String bairro,
        String cidade,
        String uf
) implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

}
