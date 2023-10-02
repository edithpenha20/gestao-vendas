package com.evendas.controller;

import com.evendas.dto.categoria.CategoriaResponseDTO;
import com.evendas.dto.produto.ProdutoRequestDTO;
import com.evendas.dto.produto.ProdutoResponseDTO;
import com.evendas.model.Categoria;
import com.evendas.model.Produto;
import com.evendas.service.ProdutoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/{codigoCategoria}/produto")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    @GetMapping
    @Operation(description = "Lista todos os produtos por categoria.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Categoria encontrada",content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Categoria.class)),
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Produto.class))}),
            @ApiResponse(responseCode = "404", description = "Categoria não encontrada.", content = @Content)
    })
    public List<ProdutoResponseDTO> listarTodos(@PathVariable Long codigoCategoria) {
        return produtoService.listarTodos(codigoCategoria).stream()
                .map(ProdutoResponseDTO::converterparaProdutoDTO).collect(Collectors.toList());
    }

    @GetMapping("/{codigo}")
    @Operation(description = "Encontrar produto pelo código.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produto encontrado",content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Categoria.class)) }),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado.", content = @Content)
    })
    public ResponseEntity<ProdutoResponseDTO> buscarPorCodigo(@PathVariable Long codigoCategoria,
                                                              @PathVariable Long codigo) {
        Produto produto = produtoService.buscarPorCodigo(codigo, codigoCategoria);
        return ResponseEntity.ok(ProdutoResponseDTO.converterparaProdutoDTO(produto));
    }

    @PostMapping
    @Operation(summary = "Criar um produto por categoria", description = "Criar produto por categoria.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Produto criado", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Produto.class)) }),
            @ApiResponse(responseCode = "500", description = "Ocorreu um erro interno.", content = @Content) })
    public ResponseEntity<ProdutoResponseDTO> salvar(@PathVariable Long codigoCategoria, @Valid @RequestBody ProdutoRequestDTO produtoDTO) {
        Produto produtoSalvo = produtoService.salvar(codigoCategoria, produtoDTO.converterParaEntidade(codigoCategoria));
        return ResponseEntity.status(HttpStatus.CREATED).body(ProdutoResponseDTO.converterparaProdutoDTO(produtoSalvo));
    }

    @PutMapping("/{codigoProduto}")
    @Operation(summary = "Alterar um produto", description = "Alterar produto por categoria.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Produto alterado com sucesso", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Produto.class)) }),
            @ApiResponse(responseCode = "404", description = "Categoria não encontrada.", content = @Content),
            @ApiResponse(responseCode = "500", description = "Ocorreu um erro interno.", content = @Content) })
    public ResponseEntity<ProdutoResponseDTO> atualizar(@PathVariable Long codigoCategoria, @PathVariable Long codigoProduto,
                                                        @Valid @RequestBody ProdutoRequestDTO produtoDTO) {
        Produto produtoSalvo = produtoService.atualizar(codigoCategoria, codigoProduto, produtoDTO.converterParaEntidade(codigoCategoria, codigoProduto));
        return ResponseEntity.ok(ProdutoResponseDTO.converterparaProdutoDTO(produtoSalvo));
    }

    @DeleteMapping("/{codigoProduto}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Create a clone", description = "Excluir produto por categoria.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Produto excluído com sucesso", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Produto.class)) }),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado.", content = @Content),
            @ApiResponse(responseCode = "500", description = "Ocorreu um erro interno.", content = @Content) })
    public void deletar(@PathVariable Long codigoCategoria, @PathVariable Long codigoProduto) {
        produtoService.deletar(codigoCategoria, codigoProduto);
    }

}
