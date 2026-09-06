package com.historico_pedidos.service;

import com.historico_pedidos.dto.request.HistoricoRequest;
import com.historico_pedidos.mapper.HistoricoMapper;
import com.historico_pedidos.model.Historico;
import com.historico_pedidos.repository.HistoricoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HistoricoService {

    private final HistoricoRepository repository;
    private final HistoricoMapper mapper;

    public void salvarHistoricos(List<HistoricoRequest> requests) {
        List<Historico> list = requests.stream().map(request -> {
            Historico entity = mapper.toEntity(request);
            entity
        }).toList();
        repository.saveAll(list);
    }
}
