package com.produtos.controller;

import com.produtos.dto.request.ProdutoRequest;
import com.produtos.dto.response.ProdutoResponse;
import com.produtos.service.ProdutoService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/produtos")
@RequiredArgsConstructor
public class ProdutoController {

    private final ProdutoService service;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProdutoResponse> create(@RequestBody ProdutoRequest request) {
        ProdutoResponse response = service.create(request);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri()
                .path("/{id}")
                .buildAndExpand(response.getId())
                .toUri();

        return ResponseEntity.created(uri).body(response);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<ProdutoResponse> getById(@PathVariable("id") Long id) {

        return ResponseEntity.ok(service.getById(id));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<Page<ProdutoResponse>> list() {

        return ResponseEntity.ok(service.list());
    }

    @PatchMapping("/{id}/decrementar")
    public ResponseEntity<Void> decrementar(@PathVariable("id") Long id, @RequestParam("valor") Integer valor) {
        service.decrementar(id, valor);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/acrescentar")
    public ResponseEntity<Void> acrescentar(@PathVariable("id") Long id, @RequestParam("valor") Integer valor) {
        service.acrescentar(id, valor);
        return ResponseEntity.noContent().build();
    }

}
