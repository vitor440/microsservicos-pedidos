package com.pedido.proxy;

import com.pedido.configuration.FeingConfiguration;
import com.pedido.proxy.dto.ProdutoResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "produtos-api", configuration = FeingConfiguration.class)
public interface ProdutoReservaClient {

    @GetMapping("/produtos/listaProdutos")
    List<ProdutoResponse> getProdutos(@RequestParam(value = "ids") List<Long> ids);
}
