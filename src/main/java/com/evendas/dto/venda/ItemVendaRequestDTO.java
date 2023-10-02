package com.evendas.dto.venda;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

@Schema(description = "Requisição de Item de venda - DTO",
        name = "Item de venda Request")
public record ItemVendaRequestDTO(

        @Schema(description = "Código do produto",
                name = "codigoProduto")
        @NotNull(message = "Código do produto")
        Long codigoProduto,
        @Schema(description = "Quantidade",
                name = "quantidade",
                minLength = 1)
        @NotNull(message = "Quantidade")
        Integer quantidade,
        @Schema(description = "Preço vendido",
                name = "precoVendido")
        @NotNull(message = "Preço vendido")
        BigDecimal precoVendido
) {
}
