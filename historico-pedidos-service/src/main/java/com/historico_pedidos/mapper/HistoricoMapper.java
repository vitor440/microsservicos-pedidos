package com.historico_pedidos.mapper;

import com.historico_pedidos.dto.request.HistoricoRequest;
import com.historico_pedidos.dto.response.HistoricoResponse;
import com.historico_pedidos.model.Historico;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface HistoricoMapper {

    Historico toEntity(HistoricoRequest request);

    HistoricoResponse toDTO (Historico entity);
}
