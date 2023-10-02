package com.evendas.dto.venda;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "Resposta Cliente Venda - DTO",
        name = "Cliente Venda Response")
public record ClienteVendaResponseDTO(
        @Schema(description = "Nome",
                name = "nome")
        String nome,
        @Schema(description = "Lista de vendas",
                name = "vendaResponseDTOs")
        List<VendaResponseDTO> vendaResponseDTOs
) {
}
