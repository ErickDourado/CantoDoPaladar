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
@Table(name = "tb_pedido")
public class Pedido {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private Double subtotal;
    private Double frete;
    private Double total;

    @ManyToOne(cascade = ALL)
    private Pessoa pessoa;

    @ManyToMany(mappedBy = "pedidos", cascade = ALL)
    private List<Produto> produtos = new ArrayList<>();

}
