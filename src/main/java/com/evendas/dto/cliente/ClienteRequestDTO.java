package com.evendas.dto.cliente;

import com.evendas.model.Cliente;
import com.evendas.model.Endereco;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

@Schema(description = "Requisição do Cliente - DTO",
        name = "Cliente Request")
public record ClienteRequestDTO(

        @Schema(description = "Nome do cliente.",
                name = "nome",
                minLength = 3,
                maxLength = 50)
        @NotBlank(message = "nome")
        String nome,
        @Schema(description = "Telefone do cliente.",
                name = "telefone")
        @NotBlank(message = "telefone")
        @Pattern(regexp = "\\([\\d]{2}\\)[\\d]{5}[- .][\\d]{4}", message = "Telefone")
        String telefone,
        @Schema(description = "Status do cliente.",
                name = "ativo")
        @NotNull(message = "Ativo")
        Boolean ativo,
        @Schema(description = "Endereço do cliente.",
                name = "endereco")
        @NotNull(message = "Endereço")
        EnderecoRequestDTO enderecoDto
) {
    public Cliente converterParaEntidade() {
        Endereco endereco = new Endereco(enderecoDto.logradouro(), enderecoDto.numero(),
                enderecoDto.complemento(), enderecoDto.bairro(), enderecoDto.cep(), enderecoDto.cidade(),
                enderecoDto.estado());
        return new Cliente(nome, telefone, ativo, endereco);
    }

    public Cliente converterParaEntidade(Long codigo) {
        Endereco endereco = new Endereco(enderecoDto.logradouro(), enderecoDto.numero(),
                enderecoDto.complemento(), enderecoDto.bairro(), enderecoDto.cep(), enderecoDto.cidade(),
                enderecoDto.estado());
        return new Cliente(codigo, nome, telefone, ativo, endereco);
    }
}
