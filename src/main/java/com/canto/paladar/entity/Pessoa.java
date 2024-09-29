package com.canto.paladar.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.GenerationType.IDENTITY;

@Getter
@Setter
@ToString
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

    @ManyToMany(cascade = ALL)
    @JoinTable(
            name = "tb_pessoa_endereco",
            joinColumns = @JoinColumn(name = "pessoa_id"),
            inverseJoinColumns = @JoinColumn(name = "endereco_id")
    )
    private Set<Endereco> enderecos = new HashSet<>();

}
