package com.evendas.dto.produto;

import com.evendas.dto.categoria.CategoriaResponseDTO;
import com.evendas.model.Produto;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

@Schema(description = "Resposta do Produto - DTO",
        name = "Produto Response")
public record ProdutoResponseDTO(
        @Schema(description = "Código gerado quando salvo no banco de dados",
                name = "codigo")
        Long codigo,

        @Schema(description = "Descrição do produto.",
                name = "descricao")
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
                name = "observacao")
        String observacao,

        @Schema(description = "Categoria a que pertence o produto",
                name = "categoria")
        CategoriaResponseDTO categoria
) {
        public static ProdutoResponseDTO converterparaProdutoDTO(Produto produto) {
                return new ProdutoResponseDTO(
                        produto.getCodigo(),
                        produto.getDescricao(),
                        produto.getQuantidade(),
                        produto.getPrecoCusto(),
                        produto.getPrecoVenda(),
                        produto.getObservacao(),
                        CategoriaResponseDTO.converterParaCategoriaDTO(produto.getCategoria())
                );
        }
}
