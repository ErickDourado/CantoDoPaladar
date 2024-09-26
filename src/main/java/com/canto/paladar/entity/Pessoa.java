package com.canto.paladar.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.GenerationType.IDENTITY;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tb_pessoa")
public class Pessoa {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private String nome;

    @Column(unique = true)
    private String email;

    @Column(unique = true)
    private String celular;

    private String senha;

    @Column(name = "admin")
    private boolean isAdmin;

    @OneToMany(mappedBy = "pessoa", cascade = ALL)
    private List<Pedido> pedidos = new ArrayList<>();

    @ManyToMany(mappedBy = "pessoas", cascade = ALL)
    private List<Endereco> enderecos = new ArrayList<>();

}
