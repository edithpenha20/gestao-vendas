package com.evendas.dto.venda;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

@Schema(description = "Response do Item Venda - DTO",
        name = "Cliente Response")
public record ItemVendaResponseDTO(
        @Schema(description = "Código do item venda",
                name = "codigo")
        Long codigo,
        @Schema(description = "Quantidade",
                name = "quantidade")
        Integer quantidade,
        @Schema(description = "Preço vendido",
                name = "precoVendido")
        BigDecimal precoVendido,
        @Schema(description = "Código do produto",
                name = "codigoProduto")
        Long codigoProduto,
        @Schema(description = "Descrição do produto",
                name = "produtoDescricao")
        String produtoDescricao
) {
}
