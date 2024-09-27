package com.canto.paladar.repository;

import com.canto.paladar.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    Optional<Produto> findByNome(String nome);
    Optional<Produto> findByDescricao(String descricao);

}
