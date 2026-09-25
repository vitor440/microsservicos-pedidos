package pagecontent

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import com.pedido.dto.response.PedidoResponse

@JsonIgnoreProperties(ignoreUnknown = true)
class PedidoPageContent {

    @JsonProperty(value = "content")
    private List<PedidoResponse> content;

    PedidoPageContent() {
    }

    List<PedidoResponse> getContent() {
        return content
    }

    void setContent(List<PedidoResponse> content) {
        this.content = content
    }
}
