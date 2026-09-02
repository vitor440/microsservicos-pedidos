package com.produtos.controller;

import com.produtos.dto.request.EstatisticaProdutoRequest;
import com.produtos.dto.response.EstatisticaProdutoResponse;
import com.produtos.service.EstatisticaProdutoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/estatisticas")
@RequiredArgsConstructor
public class EstatisticaProdutoController implements GenericController{

    private final EstatisticaProdutoService service;

    @PostMapping
    public ResponseEntity<EstatisticaProdutoResponse> create(@RequestBody EstatisticaProdutoRequest request) {
        EstatisticaProdutoResponse response = service.create(request);
        URI uri = generateUri(response.getId());
        return ResponseEntity.created(uri).body(response);
    }
}
