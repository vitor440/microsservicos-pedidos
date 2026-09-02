package com.produtos.kafka.consumer;

import com.produtos.dto.request.EstatisticaProdutoRequest;
import com.produtos.service.EstatisticaProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import java.util.List;

@Component
public class ProdutoConsumer {


    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private EstatisticaProdutoService service;

    @KafkaListener(topics = "produto-estatistica", groupId = "produto-estatistica")
    public void consumer(String mensagem) {
        System.out.println("Mensagem Recebida: " + mensagem);

        List<EstatisticaProdutoRequest> requests = objectMapper.readValue(mensagem, new TypeReference<List<EstatisticaProdutoRequest>>(){});

        for (EstatisticaProdutoRequest request : requests) {
            service.create(request);
        }
    }
}
