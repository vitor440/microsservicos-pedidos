package com.pedido.kafka.consumer;

import com.pedido.model.Pedido;
import com.pedido.service.PedidoService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class ProdutoConsumer {

    @Autowired
    private PedidoService service;

    @KafkaListener(id = "pedido-sucesso", topics = "pedido-sucesso")
    @Transactional
    public void pedidoSuccessHandle(String message) {
        Long id = Long.valueOf(message);
        Pedido pedido = service.getPedido(id);
        pedido.setStatus("FINALIZADO");

    }

    @KafkaListener(id = "pedido-erro", topics = "pedido-erro")
    @Transactional
    public void pedidoErrorHandle(String message) {
        Long id = Long.valueOf(message);
        Pedido pedido = service.getPedido(id);
        pedido.setStatus("CANCELADO");

    }
}
