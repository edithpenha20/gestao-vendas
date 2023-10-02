package com.evendas.dto.venda;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;

@Schema(description = "Requisição de Venda - DTO",
        name = "Venda Request")
public record VendaRequestDTO(

        @Schema(description = "Data",
                name = "data")
        @NotNull(message = "Data")
        LocalDate data,
        @Schema(description = "Itens de Venda",
                name = "itensVendaDto")
        @NotNull(message = "Itens de Venda")
        List<ItemVendaRequestDTO> itensVendaDto
) {
}
