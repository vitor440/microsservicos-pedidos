package com.pedido.pagecontent;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.pedido.dto.response.PedidoResponse;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PedidoPageContent {

    @JsonProperty(value = "content")
    private List<PedidoResponse> content;

    public PedidoPageContent() {
    }

    public List<PedidoResponse> getContent() {
        return content;
    }

    public void setContent(List<PedidoResponse> content) {
        this.content = content;
    }
}
