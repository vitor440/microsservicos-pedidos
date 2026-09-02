package com.pedido.proxy;

import com.pedido.configuration.FeingConfiguration;
import com.pedido.proxy.dto.ProdutoReservaRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "produtos-api", configuration = FeingConfiguration.class)
public interface ProdutoReservaClient {

    @PostMapping("/produtos-reserva/reserva")
    ResponseEntity<Void> create(@RequestBody List<ProdutoReservaRequest> requests);

    @PatchMapping("/produtos-reserva/confirmar")
    ResponseEntity<Void> confirmarReservas();

    @PatchMapping("/produtos-reserva/cancelar")
    ResponseEntity<Void> cancelarReservas();
}
