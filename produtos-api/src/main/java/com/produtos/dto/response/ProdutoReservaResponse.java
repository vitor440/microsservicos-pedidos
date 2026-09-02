package com.produtos.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProdutoReservaResponse {

    private Long id;

    private Long produtoId;

    private Integer quantidade;
}
