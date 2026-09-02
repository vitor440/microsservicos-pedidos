package com.pedido.mapper;

import com.pedido.dto.request.ItemRequest;
import com.pedido.dto.response.ItemResponse;
import com.pedido.model.Item;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ItemMapper {

    Item toEntity(ItemRequest request);

    @Mapping(target = "pedidoId", expression = "java( item.getPedido().getId() )")
    ItemResponse toDTO(Item item);
}
