package com.historico_pedidos.kafka.consumer;

import com.historico_pedidos.dto.request.HistoricoRequest;
import com.historico_pedidos.repository.HistoricoRepository;
import com.historico_pedidos.service.HistoricoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class HistoricoConsumer {

    private final ObjectMapper objectMapper;
    private final HistoricoService service;

    @KafkaListener(id = "produto-estatistica", topics = "produto-estatistica")
    public void salvarHistorico(String message) {

        log.info("salvando historicos");
        List<HistoricoRequest> requests = objectMapper.convertValue(message, new TypeReference<List<HistoricoRequest>>() {});

        service.salvarHistoricos(requests);
        log.info("historicos registrados com sucesso!");
    }
}
