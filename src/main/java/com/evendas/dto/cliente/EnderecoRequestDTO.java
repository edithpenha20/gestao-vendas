package com.evendas.dto.cliente;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

@Schema(description = "Requisição do Endereço - DTO",
        name = "Endereço Request")
public record EnderecoRequestDTO(
        @Schema(description = "Logradouro",
                name = "logradouro",
                minLength = 3,
                maxLength = 50)
        @NotBlank(message = "Logradouro")
        String logradouro,
        @Schema(description = "Número da redidência",
                name = "numero")
        @NotNull(message = "Núemro")
        Integer numero,
        @Schema(description = "Complemento.",
                name = "complemento",
                maxLength = 50)
        String complemento,
        @Schema(description = "Bairro",
                name = "bairro",
                minLength = 3,
                maxLength = 30)
        @NotBlank(message = "Bairro")
        String bairro,
        @Schema(description = "CEP",
                name = "cep")
        @NotBlank(message = "Cep")
        @Pattern(regexp = "[\\d]{5}-[\\d]{3}", message = "Cep")
        String cep,
        @Schema(description = "Cidade",
                name = "cidade",
                minLength = 3,
                maxLength = 30)
        @NotBlank(message = "Cidade")
        String cidade,
        @Schema(description = "Estado",
                name = "estado",
                minLength = 3,
                maxLength = 50)
        @NotBlank(message = "Estado")
        String estado

) {
}
