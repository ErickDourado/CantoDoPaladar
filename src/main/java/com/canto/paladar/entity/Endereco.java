package com.canto.paladar.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

import static jakarta.persistence.GenerationType.IDENTITY;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tb_endereco")
public class Endereco {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private String cep;
    private String logradouro;
    private String complemento;
    private String numero;
    private String bairro;
    private String cidade;
    private String uf;

    @ManyToMany(mappedBy = "enderecos")
    private Set<Pessoa> pessoas = new HashSet<>();

}
