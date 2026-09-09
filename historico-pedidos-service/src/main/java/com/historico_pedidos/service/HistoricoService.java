package com.historico_pedidos.service;

import com.historico_pedidos.kafka.consumer.dto.ItemDTO;
import com.historico_pedidos.kafka.consumer.dto.PedidoEvent;
import com.historico_pedidos.model.Historico;
import com.historico_pedidos.repository.HistoricoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HistoricoService {

    private final HistoricoRepository repository;

    public void salvarHistoricos(PedidoEvent event) {
        List<ItemDTO> itens = event.getItens();
        List<Historico> historicos = itens.stream().map(item -> {
            Historico historico = new Historico();
            historico.setProdutoId(item.getProdutoId());
            historico.setUsuarioId(event.getUsuarioId());
            historico.setQuantidade(item.getQuantidade());
            historico.setValorUnitario(item.getPrecoUnitario());
            historico.setValorTotal(item.getPrecoUnitario().add(BigDecimal.valueOf(item.getQuantidade())));
            historico.setDataCompra(LocalDate.now());

            return historico;
        }).toList();

        repository.saveAll(historicos);
    }
}
