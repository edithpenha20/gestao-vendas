package com.evendas.dto.produto;

import com.evendas.dto.categoria.CategoriaResponseDTO;
import com.evendas.model.Categoria;
import com.evendas.model.Produto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;

@Schema(description = "Requisição do Produto - DTO",
        name = "Produto Request")
public record ProdutoRequestDTO(
       @Schema(description = "Descrição do produto.",
                name = "descricao",
                minLength = 3,
                maxLength = 100)
       @NotBlank(message = "Descrição")
        String descricao,

        @Schema(description = "Quantidade do produto.",
                name = "quantidade")
        Integer quantidade,

        @Schema(description = "Preço unitário do produto.",
                name = "precoCusto")
        BigDecimal precoCusto,

        @Schema(description = "Preço da venda do produto.",
                name = "precoVenda")
        BigDecimal precoVenda,

        @Schema(description = "Observação",
                name = "observacao",
                maxLength = 500)
        String observacao
) {
    public Produto converterParaEntidade(Long codigoCategoria) {
        return new Produto(descricao, quantidade, precoCusto, precoVenda, observacao, new Categoria(codigoCategoria));
    }

    public Produto converterParaEntidade(Long codigoCategoria, Long codigoProduto) {
        return new Produto(codigoProduto, descricao, quantidade, precoCusto, precoVenda, observacao, new Categoria(codigoCategoria));
    }
}
