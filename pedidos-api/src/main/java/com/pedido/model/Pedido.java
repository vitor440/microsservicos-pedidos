package com.pedido.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "pedidos")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "usuario_id")
    private String usuarioId;

    @Column(name = "valor_total")
    private BigDecimal valorTotal;

    private String status;

    @Column(name = "data_compra")
    private LocalDate dataCompra;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL)
    List<Item> itens;
}
