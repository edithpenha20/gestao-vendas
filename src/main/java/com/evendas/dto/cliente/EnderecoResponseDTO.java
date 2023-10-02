package com.evendas.dto.cliente;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

@Schema(description = "Resposta do Endereço - DTO",
        name = "Endereço Response")
public record EnderecoResponseDTO(
        @Schema(description = "Logradouro",
                name = "logradouro")
        String logradouro,
        @Schema(description = "Número da redidência",
                name = "numero")
        Integer numero,
        @Schema(description = "Complemento.",
                name = "complemento")
        String complemento,
        @Schema(description = "Bairro",
                name = "bairro")
        String bairro,
        @Schema(description = "CEP",
                name = "cep")
        String cep,
        @Schema(description = "Cidade",
                name = "cidade")
        String cidade,
        @Schema(description = "Estado",
                name = "estado")
        String estado

) {
}
