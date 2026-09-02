package com.produtos.mapper;

import com.produtos.dto.request.ProdutoReservaRequest;
import com.produtos.dto.response.ProdutoReservaResponse;
import com.produtos.model.ProdutoReserva;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProdutoReservaMapper {

    ProdutoReserva toEntity(ProdutoReservaRequest request);

    @Mapping(target = "produtoId", expression = "java( entity.getProduto().getId() )")
    ProdutoReservaResponse toDTO(ProdutoReserva entity);
}
