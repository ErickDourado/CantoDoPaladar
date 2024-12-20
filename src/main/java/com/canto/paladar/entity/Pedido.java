package com.canto.paladar.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;

@Getter
@Setter
@ToString
@With
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tb_pedido")
public class Pedido {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private Double subtotal;
    private Double frete;
    private Double total;

    @ManyToOne(fetch = LAZY)
    private Pessoa pessoa;

    @ManyToOne(fetch = LAZY)
    private Endereco endereco;

    @ManyToMany(mappedBy = "pedidos")
    private Set<Produto> produtos = new HashSet<>();

}
