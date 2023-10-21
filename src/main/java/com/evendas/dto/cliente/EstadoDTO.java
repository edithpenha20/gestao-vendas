package com.evendas.dto.cliente;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Requisição do Cliente - DTO",
        name = "Cliente Request")
public record EstadoDTO(
        @Schema(description = "Nome do cliente.",
                name = "nome",
                minLength = 3,
                maxLength = 50)
        @NotBlank(message = "nome")
        String nome
) {
}
