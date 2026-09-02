package com.pedido.service;

import com.pedido.dto.request.ItemRequest;
import com.pedido.dto.response.PedidoResponse;
import com.pedido.mapper.ItemMapper;
import com.pedido.mapper.PedidoMapper;
import com.pedido.model.Item;
import com.pedido.model.Pedido;
import com.pedido.proxy.ProdutoReservaClient;
import com.pedido.proxy.dto.ProdutoReservaRequest;
import com.pedido.repository.PedidoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PedidoService {

    private final PedidoRepository repository;
    private final PedidoMapper pedidoMapper;
    private final ItemMapper itemMapper;
    private final ProdutoReservaClient client;
    private final KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Transactional
    public PedidoResponse salvarPedido(List<ItemRequest> requests) {

        Double valorTotal = 0.0;
        Pedido pedido = new Pedido();
        String usuarioId = SecurityContextHolder.getContext().getAuthentication().getName();

        pedido.setStatus("PENDENTE");
        pedido.setDataCompra(LocalDate.now());
        pedido.setUsuarioId(usuarioId);

        List<Item> itens = requests.stream().map(itemMapper::toEntity).toList();
        itens.forEach(item1 -> {
            item1.setPedido(pedido);
            item1.setPrecoUnitario(BigDecimal.valueOf(1000.00));
            item1.setProdutoId(1L);
            item1.setPrecoTotal(BigDecimal.valueOf(2000.00));
        });
//        itens.forEach(item -> {valorTotal += valorTotal.add(item.getPrecoTotal()));

//        for(Item item : itens) {
//            valorTotal += item.getPrecoTotal().doubleValue();
//        }

        pedido.setValorTotal(BigDecimal.valueOf(2000.00));
        pedido.setItens(itens);

        PedidoResponse response = pedidoMapper.toDTO(repository.save(pedido));

        List<ProdutoReservaRequest> produtosReserva = requests.stream()
                .map(request -> new ProdutoReservaRequest(request.getProdutoId(), request.getQuantidade()))
                .toList();

        try{
            client.create(produtosReserva);

            client.confirmarReservas();
        }
        catch (Exception e) {
            try {
                client.cancelarReservas();

            }
            catch (Exception exception) {
                throw new RuntimeException(exception.getMessage());
            }
            throw new RuntimeException(e.getMessage());
        }

        String mensagem = objectMapper.writeValueAsString(requests);
        kafkaTemplate.send("produto-estatistica", mensagem);
        return response;
    }
}
