package com.evendas.dto.cliente;

import com.evendas.model.Cliente;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record ClienteResponseDTO(
        @Schema(description = "Código do cliente.",
                name = "codigo")
        Long codigo,
        @Schema(description = "Nome do cliente.",
                name = "nome")
        String nome,
        @Schema(description = "Telefone do cliente.",
                name = "telefone")
        String telefone,
        @Schema(description = "Status do cliente.",
                name = "ativo")
        Boolean ativo,
        @Schema(description = "Endereço do cliente.",
                name = "endereco")
        EnderecoResponseDTO enderecoDto
) {

    public static ClienteResponseDTO converterParaClienteDTO(Cliente cliente) {
        var enderecoResponseDTO = new EnderecoResponseDTO(cliente.getEndereco().getLogradouro(),
                cliente.getEndereco().getNumero(), cliente.getEndereco().getComplemento(),
                cliente.getEndereco().getBairro(), cliente.getEndereco().getCep(), cliente.getEndereco().getCidade(),
                cliente.getEndereco().getEstado());
        return new ClienteResponseDTO(cliente.getCodigo(), cliente.getNome(), cliente.getTelefone(), cliente.getAtivo(),
                enderecoResponseDTO);
    }
}
