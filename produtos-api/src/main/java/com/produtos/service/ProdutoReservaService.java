package com.produtos.service;

import com.produtos.dto.request.ProdutoReservaRequest;
import com.produtos.dto.response.ProdutoReservaResponse;
import com.produtos.mapper.ProdutoReservaMapper;
import com.produtos.model.Produto;
import com.produtos.model.ProdutoReserva;
import com.produtos.repository.ProdutoReservaRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProdutoReservaService {

    private final ProdutoReservaMapper mapper;
    private final ProdutoReservaRepository repository;
    private final ProdutoService produtoService;

    @Transactional
    public ProdutoReservaResponse create(ProdutoReservaRequest request) {
        ProdutoReserva produtoReserva = mapper.toEntity(request);
        Produto produto = produtoService.getProduto(request.getProdutoId());
        produtoReserva.setProduto(produto);

        if (produto.getQuantidade() < request.getQuantidade()) throw new RuntimeException("quantidade insuficiente!");

        produto.setQuantidadeReserva(request.getQuantidade());
        produto.setQuantidade(produto.getQuantidade() - request.getQuantidade());

        return mapper.toDTO(repository.save(produtoReserva));
    }

    @Transactional
    public void reservarProdutos(List<ProdutoReservaRequest> requests) {
        for (ProdutoReservaRequest request : requests) {
            create(request);
        }
    }

    @Transactional
    public void deletaReservas() {
        List<ProdutoReserva> reservas = repository.findAll();

        for(ProdutoReserva reserva : reservas) {
            Produto produto = reserva.getProduto();
            produto.setQuantidadeReserva(produto.getQuantidadeReserva() - reserva.getQuantidade());
            produto.setQuantidade(produto.getQuantidade() + reserva.getQuantidade());
            repository.delete(reserva);
        }
    }

    public void confirmaReservas() {
        List<ProdutoReserva> reservas = repository.findAll();

        for(ProdutoReserva reserva : reservas) {
            Produto produto = reserva.getProduto();
            produto.setQuantidadeReserva(0);

            repository.delete(reserva);
        }
    }

}
