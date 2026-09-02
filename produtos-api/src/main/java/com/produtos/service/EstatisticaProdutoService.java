package com.produtos.service;

import com.produtos.dto.request.EstatisticaProdutoRequest;
import com.produtos.dto.response.EstatisticaProdutoResponse;
import com.produtos.mapper.EstatisticaProdutoMapper;
import com.produtos.model.EstatisticaProduto;
import com.produtos.model.Produto;
import com.produtos.repository.EstatisticaProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class EstatisticaProdutoService {

    private final EstatisticaProdutoRepository repository;
    private final EstatisticaProdutoMapper mapper;
    private final ProdutoService produtoService;

    public EstatisticaProdutoResponse create(EstatisticaProdutoRequest request) {
        EstatisticaProduto estatisticaProduto = mapper.toEntity(request);

        Produto produto = produtoService.getProduto(request.getProdutoId());

        estatisticaProduto.setProduto(produto);
        estatisticaProduto.setValorTotal(produto.getPreco().multiply(BigDecimal.valueOf(request.getQuantidade())));
        estatisticaProduto.setDataCompra(LocalDate.now());

        return mapper.toDto(repository.save(estatisticaProduto));
    }
}
