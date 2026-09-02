package com.pedido.controller;

import com.pedido.dto.request.ItemRequest;
import com.pedido.dto.response.PedidoResponse;
import com.pedido.service.PedidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
