package com.produtos.mapper;

import com.produtos.dto.request.EstatisticaProdutoRequest;
import com.produtos.dto.response.EstatisticaProdutoResponse;
import com.produtos.model.EstatisticaProduto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EstatisticaProdutoMapper {

    EstatisticaProduto toEntity(EstatisticaProdutoRequest request);

    @Mapping(target = "produtoId", expression = "java( estatisticaProduto.getProduto().getId() )")
    @Mapping(target = "nome", expression = "java( estatisticaProduto.getProduto().getNome() )")
    EstatisticaProdutoResponse toDto(EstatisticaProduto estatisticaProduto);
}
