package com.produtos.mapper;

import com.produtos.dto.request.ProdutoRequest;
import com.produtos.dto.response.ProdutoResponse;
import com.produtos.model.Produto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProdutoMapper {

    Produto toEntity(ProdutoRequest request);

    ProdutoResponse toDTO(Produto produto);
}
