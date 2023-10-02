package com.evendas.dto.venda;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;
import java.util.List;

@Schema(description = "Resposta da Venda - DTO",
        name = "Venda Response")
public record VendaResponseDTO(
        @Schema(description = "Código da venda",
                name = "codigo")
        Long codigo,
        @Schema(description = "Data",
                name = "data")
        LocalDate data,
        @Schema(description = "Itens venda",
                name = "itemVendaDTOs")
        List<ItemVendaResponseDTO> itemVendaDTOs
) {
}
