package com.produtos.service;

import com.produtos.dto.request.ProdutoRequest;
import com.produtos.dto.response.ProdutoResponse;
import com.produtos.mapper.ProdutoMapper;
import com.produtos.model.Produto;
import com.produtos.repository.ProdutoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProdutoService {

    private final ProdutoRepository repository;
    private final ProdutoMapper mapper;

    public ProdutoResponse create(ProdutoRequest request) {
        Produto produto = mapper.toEntity(request);
        produto.setQuantidadeReserva(0);
        return mapper.toDTO(repository.save(produto));
    }

    public ProdutoResponse getById(Long id) {
        Produto produto = getProduto(id);
        return mapper.toDTO(produto);
    }

    // Recebe uma lista de id's e retorna uma lista de produtos com os id's equivalentes.
    public List<ProdutoResponse> getProdutos(List<Long> ids) {
        List<Produto> produtos = new ArrayList<>();

        for(Long id : ids) {
            Produto produto = getProduto(id);
            produtos.add(produto);
        }

        return produtos.stream().map(mapper::toDTO).toList();
    }

    @Transactional
    public void decrementar(Long id, int valor) {
        Produto produto = getProduto(id);

        if(produto.getQuantidade().equals(0)) throw new RuntimeException("Produto sem estoque!");

        produto.setQuantidade(produto.getQuantidade() - valor);
    }

    @Transactional
    public void acrescentar(Long id, int valor) {
        Produto produto = getProduto(id);
        produto.setQuantidade(produto.getQuantidade() + valor);
    }

    public Page<ProdutoResponse> list() {
        Pageable pageable = PageRequest.of(0, 20);
        Page<Produto> produtos = repository.findAll(pageable);

        return produtos.map(mapper::toDTO);
    }

    public Produto getProduto(Long id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("erro"));
    }
}
