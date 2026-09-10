package com.pedido.controller;

import com.pedido.dto.request.ItemRequest;
import com.pedido.dto.response.PedidoResponse;
import com.pedido.service.PedidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pedidos")
@RequiredArgsConstructor
public class PedidoController {

    private final PedidoService service;

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<PedidoResponse> create(@RequestBody List<ItemRequest> requests) {
        return ResponseEntity.ok(service.salvarPedido(requests));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PedidoResponse> getById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<PedidoResponse>> create(@RequestParam(value = "page", defaultValue = "0") Integer page,
                                                       @RequestParam(value = "size", defaultValue = "100") Integer size) {
        return ResponseEntity.ok(service.listar(page, size));
    }
}
