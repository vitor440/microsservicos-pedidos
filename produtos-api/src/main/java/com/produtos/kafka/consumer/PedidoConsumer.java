package com.produtos.kafka.consumer;

import com.produtos.kafka.dto.ItemDTO;
import com.produtos.kafka.dto.PedidoEvent;
import com.produtos.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

import java.util.List;

@Component
public class PedidoConsumer {


    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ProdutoService service;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @KafkaListener(topics = "pedido-criado", groupId = "pedido-criado")
    public void produtoCriadoHandle(String mensagem) {
        System.out.println("Mensagem Recebida: " + mensagem);

        PedidoEvent event = objectMapper.readValue(mensagem, PedidoEvent.class);
        List<ItemDTO> itens = event.getItens();

        for (ItemDTO item : itens) {
            try {
                service.decrementar(item.getProdutoId(), item.getQuantidade());

            }
            catch (Exception e) {
                // kafka failure
                kafkaTemplate.send("pedido-erro", event.getPedidoId().toString());
                throw new RuntimeException("Erro ao decrementar estoque de produtos");
            }


        }

        // kafka success
        kafkaTemplate.send("pedido-sucesso", event.getPedidoId().toString());
        kafkaTemplate.send("estatistica-produto", mensagem);
    }
}
