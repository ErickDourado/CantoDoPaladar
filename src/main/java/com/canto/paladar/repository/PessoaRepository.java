package com.canto.paladar.repository;

import com.canto.paladar.entity.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PessoaRepository extends JpaRepository<Pessoa, Long> {

    Optional<Pessoa> findByEmail(String email);
    Optional<Pessoa> findByCelular(String celular);

}
