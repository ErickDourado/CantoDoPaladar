package com.canto.paladar.entity;

import com.canto.paladar.enums.Categoria;
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
@Table(name = "tb_produto")
public class Produto {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(unique = true)
    private String nome;

    @Column(columnDefinition = "LONGBLOB")
    private String foto;

    @Column(unique = true)
    private String descricao;

    private Double preco;

    @Enumerated(EnumType.STRING)
    private Categoria categoria;

    @ManyToMany
    @JoinTable(
            name = "tb_produto_pedido",
            joinColumns = @JoinColumn(name = "produto_id"),
            inverseJoinColumns = @JoinColumn(name = "pedido_id")
    )
    private Set<Pedido> pedidos = new HashSet<>();

}
