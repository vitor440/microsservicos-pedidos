package com.historico_pedidos.kafka.consumer;

import com.historico_pedidos.kafka.consumer.dto.PedidoEvent;
import com.historico_pedidos.service.HistoricoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

@Service
@RequiredArgsConstructor
@Slf4j
public class HistoricoConsumer {

    private final ObjectMapper objectMapper;
    private final HistoricoService service;

    @KafkaListener(id = "estatistica-produto", topics = "estatistica-produto")
    public void salvarHistorico(String message) {

        log.info("registrando compra");
        PedidoEvent event = objectMapper.readValue(message, PedidoEvent.class);
        service.salvarHistoricos(event);
        log.info("compra registrada com sucesso!");
    }
}
