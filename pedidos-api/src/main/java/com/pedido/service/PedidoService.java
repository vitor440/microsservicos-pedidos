package com.pedido.service;

import com.pedido.dto.request.ItemRequest;
import com.pedido.dto.response.PedidoResponse;
import com.pedido.mapper.PedidoMapper;
import com.pedido.model.Item;
import com.pedido.model.Pedido;
import com.pedido.proxy.ProdutoReservaClient;
import com.pedido.proxy.dto.ProdutoResponse;
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
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PedidoService {

    private final PedidoRepository repository;
    private final PedidoMapper pedidoMapper;
    private final ProdutoReservaClient client;
    private final KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Transactional
    public PedidoResponse salvarPedido(List<ItemRequest> requests) {
        Pedido pedido = new Pedido();
        List<Item> itens = new ArrayList<>();
        BigDecimal valorTotal = BigDecimal.ZERO;
        List<Long> ids = requests.stream().map(request -> request.getProdutoId()).toList();
        List<ProdutoResponse> produtos = client.getProdutos(ids);

        for (ItemRequest request : requests) {
            ProdutoResponse produto = produtos.stream().findFirst().filter(p -> p.getId() == request.getProdutoId()).get();
            Item item = new Item();
            item.setProdutoId(request.getProdutoId());
            item.setQuantidade(request.getQuantidade());
            item.setPrecoUnitario(produto.getPreco());
            item.setPrecoTotal(produto.getPreco().multiply(BigDecimal.valueOf(request.getQuantidade())));
            item.setPedido(pedido);

            itens.add(item);
            valorTotal = valorTotal.add(produto.getPreco().multiply(BigDecimal.valueOf(request.getQuantidade())));
        }

        pedido.setItens(itens);
        pedido.setValorTotal(valorTotal);
        pedido.setDataCompra(LocalDate.now());
        pedido.setUsuarioId(SecurityContextHolder.getContext().getAuthentication().getName());
        pedido.setStatus("PENDENTE");

        PedidoResponse response = pedidoMapper.toDTO(repository.save(pedido));
        String message = objectMapper.writeValueAsString(pedido.convertToPedidoEvent());
        kafkaTemplate.send("pedido-criado", message);
        kafkaTemplate.send("estatistica-produto", message);
        return response;
    }

    public Pedido getPedido(Long id) {
        return repository.findById(id).orElse(null);
    }
}

