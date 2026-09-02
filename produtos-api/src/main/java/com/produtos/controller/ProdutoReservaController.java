package com.produtos.controller;

import com.produtos.dto.request.ProdutoReservaRequest;
import com.produtos.dto.response.ProdutoReservaResponse;
import com.produtos.service.ProdutoReservaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/produtos-reserva")
@RequiredArgsConstructor
public class ProdutoReservaController {

    private final ProdutoReservaService service;

    @PostMapping("/reserva")
    public ResponseEntity<Void> create(@RequestBody List<ProdutoReservaRequest> requests) {
        service.reservarProdutos(requests);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/confirmar")
    public ResponseEntity<Void> confirmarReservas() {
        service.confirmaReservas();
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/cancelar")
    public ResponseEntity<Void> cancelarReservas() {
        service.deletaReservas();
        return ResponseEntity.noContent().build();
    }
}
