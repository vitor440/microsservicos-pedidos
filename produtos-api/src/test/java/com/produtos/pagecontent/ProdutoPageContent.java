package com.produtos.pagecontent;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.produtos.dto.response.ProdutoResponse;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ProdutoPageContent {

    @JsonProperty(value = "content")
    private List<ProdutoResponse> content;

    public ProdutoPageContent() {
    }

    public List<ProdutoResponse> getContent() {
        return content;
    }

    public void setContent(List<ProdutoResponse> content) {
        this.content = content;
    }
}
