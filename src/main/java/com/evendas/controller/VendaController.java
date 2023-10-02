package com.evendas.controller;

import com.evendas.dto.venda.ClienteVendaResponseDTO;
import com.evendas.dto.venda.VendaRequestDTO;
import com.evendas.model.Cliente;
import com.evendas.model.Venda;
import com.evendas.service.VendaService;
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

@RestController
@RequestMapping("/venda")
public class VendaController {

    @Autowired
    private VendaService vendaService;

    @Operation(description = "Lista vendas por clientes.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Vendas encontradas",content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Cliente.class))}),
            @ApiResponse(responseCode = "404", description = "Vendas não encontradas.", content = @Content)
    })
    @GetMapping("/cliente/{codigoCliente}")
    public ResponseEntity<ClienteVendaResponseDTO> listarVendaPorCliente(@PathVariable Long codigoCliente){
        return ResponseEntity.ok(vendaService.listaVendaPorCliente(codigoCliente));
    }

    @Operation(description = "Encontrar vendas pelo código.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Venda encontrada",content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Venda.class)) }),
            @ApiResponse(responseCode = "404", description = "Venda não encontrada.", content = @Content)
    })
    @GetMapping("/{codigoVenda}")
    public ResponseEntity<ClienteVendaResponseDTO> listarVendaPorCodigo(@PathVariable Long codigoVenda){
        return ResponseEntity.ok(vendaService.listarVendaPorCodigo(codigoVenda));
    }

    @Operation(summary = "Cadastrar uma venda", description = "Cadastrar uma venda por cliente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Cliente cadastrado", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Venda.class)) }),
            @ApiResponse(responseCode = "500", description = "Ocorreu um erro interno.", content = @Content) })
    @PostMapping("/cliente/{codigoCliente}")
    public ResponseEntity<ClienteVendaResponseDTO> salvar(@PathVariable Long codigoCliente, @Valid @RequestBody VendaRequestDTO vendaDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(vendaService.salvar(codigoCliente, vendaDto));
    }

    @Operation(summary = "Atualizar uma venda", description = "Atualizar venda por cliente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Venda atualizada com sucesso", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Venda.class)) }),
            @ApiResponse(responseCode = "404", description = "Venda não encontrada.", content = @Content),
            @ApiResponse(responseCode = "500", description = "Ocorreu um erro interno.", content = @Content) })
    @PutMapping("/{codigoVenda}/cliente/{codigoCliente}")
    public ResponseEntity<ClienteVendaResponseDTO> atualizar(@PathVariable Long codigoVenda, @PathVariable Long codigoCliente, @Valid @RequestBody VendaRequestDTO vendaDto){
        return ResponseEntity.ok(vendaService.atualizar(codigoVenda, codigoCliente, vendaDto));
    }

    @Operation(summary = "Excluir uma venda", description = "Excluir uma venda.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Venda excluída com sucesso", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Cliente.class)) }),
            @ApiResponse(responseCode = "404", description = "Venda não encontrada.", content = @Content),
            @ApiResponse(responseCode = "500", description = "Ocorreu um erro interno.", content = @Content) })
    @DeleteMapping("/{codigoVenda}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long codigoVenda) {
        vendaService.deletar(codigoVenda);
    }
}
