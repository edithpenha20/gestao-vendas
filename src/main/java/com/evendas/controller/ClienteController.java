package com.evendas.controller;

import com.evendas.client.ViaCepClientService;
import com.evendas.dto.cliente.ClienteRequestDTO;
import com.evendas.dto.cliente.ClienteResponseDTO;
import com.evendas.dto.cliente.EnderecoResponseDTO;
import com.evendas.dto.cliente.EstadoDTO;
import com.evendas.model.Categoria;
import com.evendas.model.Cliente;
import com.evendas.model.Produto;
import com.evendas.service.ClienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/cliente")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @Operation(description = "Lista todos os clientes.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Clientes encontrados",content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Cliente.class))}),
            @ApiResponse(responseCode = "404", description = "Clientes não encontrados.", content = @Content)
    })
    @GetMapping
    public List<ClienteResponseDTO> listarTodas() {
        return clienteService.listarTodos().stream().map(ClienteResponseDTO::converterParaClienteDTO)
                .collect(Collectors.toList());
    }

    @Operation(description = "Encontrar cliente pelo código.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente encontrado",content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Cliente.class)) }),
            @ApiResponse(responseCode = "404", description = "Cliente não encontrado.", content = @Content)
    })
    @GetMapping("/{codigo}")
    public ResponseEntity<ClienteResponseDTO> buscarPorId(@PathVariable Long codigo) {
        Optional<Cliente> cliente = clienteService.buscarPorCodigo(codigo);
        return cliente.map(value -> ResponseEntity.ok(ClienteResponseDTO.converterParaClienteDTO(value))).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(description = "Consultar endereço pelo CEP.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Endereço encontrado",content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Cliente.class)) }),
            @ApiResponse(responseCode = "404", description = "Endereço não encontrado.", content = @Content)
    })
    @GetMapping("{cep}/json")
    public ResponseEntity<EnderecoResponseDTO> buscarEnderecoPorCep(@PathVariable String cep) {

        EnderecoResponseDTO responseCep = clienteService.buscarEnderecoPorCep(cep);

        return responseCep != null ? ResponseEntity.ok().body(responseCep) : ResponseEntity.notFound().build();
    }

    @Operation(description = "Consultar lista de Estados.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Estado encontrado",content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Cliente.class)) }),
            @ApiResponse(responseCode = "404", description = "Estado não encontrado.", content = @Content)
    })
    @GetMapping("/estados")
    public List<EstadoDTO> getEstado() {
        return clienteService.obterEstados();
    }

    @Operation(summary = "Cadastrar um cliente", description = "Cadastrar um cliente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Cliente cadastrado", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Cliente.class)) }),
            @ApiResponse(responseCode = "500", description = "Ocorreu um erro interno.", content = @Content) })
    @PostMapping
    public ResponseEntity<ClienteResponseDTO> salvar(@Valid @RequestBody ClienteRequestDTO clienteDto) {
        Cliente clienteSalvo = clienteService.salvar(clienteDto.converterParaEntidade());
        return ResponseEntity.status(HttpStatus.CREATED).body(ClienteResponseDTO.converterParaClienteDTO(clienteSalvo));
    }

    @Operation(summary = "Atualizar dados do cliente", description = "Atualizar dados do cliente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Dados do cliente atualizados com sucesso", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Cliente.class)) }),
            @ApiResponse(responseCode = "404", description = "Cliente não encontrado.", content = @Content),
            @ApiResponse(responseCode = "500", description = "Ocorreu um erro interno.", content = @Content) })
    @PutMapping("/{codigo}")
    public ResponseEntity<ClienteResponseDTO> atualizar(@PathVariable Long codigo, @Valid @RequestBody ClienteRequestDTO clienteDto){
        Cliente clienteAtualizado = clienteService.atualizar(codigo, clienteDto.converterParaEntidade(codigo));
        return ResponseEntity.ok(ClienteResponseDTO.converterParaClienteDTO(clienteAtualizado));
    }

    @Operation(summary = "Excluir um cliente", description = "Excluir um cliente.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Cliente excluído com sucesso", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Cliente.class)) }),
            @ApiResponse(responseCode = "404", description = "Cliente não encontrado.", content = @Content),
            @ApiResponse(responseCode = "500", description = "Ocorreu um erro interno.", content = @Content) })
    @DeleteMapping("/{codigo}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(@PathVariable Long codigo) {
        clienteService.deletar(codigo);
    }
}
