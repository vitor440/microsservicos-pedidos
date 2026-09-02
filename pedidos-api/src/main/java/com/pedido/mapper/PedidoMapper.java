package com.pedido.mapper;

import com.pedido.dto.response.PedidoResponse;
import com.pedido.model.Pedido;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = PedidoMapper.class)
public interface PedidoMapper {

    PedidoResponse toDTO(Pedido pedido);
}
