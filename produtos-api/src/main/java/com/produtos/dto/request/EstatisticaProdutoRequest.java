package com.produtos.dto.request;

import com.produtos.model.Produto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EstatisticaProdutoRequest {


    private Long produtoId;

    private Integer quantidade;

}
