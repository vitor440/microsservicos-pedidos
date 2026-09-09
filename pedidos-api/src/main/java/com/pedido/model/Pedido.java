package com.pedido.model;

import com.pedido.dto.request.ItemRequest;
import com.pedido.kafka.consumer.dto.ItemDTO;
import com.pedido.kafka.consumer.dto.PedidoEvent;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.context.SecurityContextHolder;

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


    public PedidoEvent convertToPedidoEvent() {
        String id = SecurityContextHolder.getContext().getAuthentication().getName();
        List<ItemDTO> itemDTO = this.itens.stream().map(item -> new ItemDTO(item.getProdutoId(), item.getPrecoUnitario(), item.getPrecoTotal(), item.getQuantidade())).toList();
        PedidoEvent pedidoEvent = new PedidoEvent(this.id, id, itemDTO);
        return pedidoEvent;
    }
}
