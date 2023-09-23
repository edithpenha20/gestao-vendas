package com.evendas.controller;

import com.evendas.model.Categoria;
import com.evendas.service.CategoriaService;
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

@RestController
@RequestMapping("/categorias")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    @GetMapping
    public List<Categoria> listarTodasCategorias(){
        return categoriaService.listarTodasCategorias();
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Categoria encontrada",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Categoria.class))}),
            @ApiResponse(responseCode = "400", description = "Código inválido", content = @Content),
            @ApiResponse(responseCode = "404", description = "Categoria não encontrada", content = @Content)})
    @GetMapping("/{codigo}")
    public ResponseEntity<Optional<Categoria>> buscarCategoriaPorId(@PathVariable Long codigo){
        Optional<Categoria> categoria = categoriaService.buscarCategoriaPorId(codigo);
        return categoria.isPresent() ? ResponseEntity.ok().body(categoria) : ResponseEntity.notFound().build();
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Categoria criada",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Categoria.class))}),
            @ApiResponse(responseCode = "400", description = "Categoria já cadastrada.", content = @Content),
            @ApiResponse(responseCode = "404", description = "Categoria não encontrada", content = @Content)})
    @PostMapping
    public ResponseEntity<Categoria> salvarCategoria(@Valid @RequestBody Categoria categoria){
        Categoria catergoria = categoriaService.salvarCategoria(categoria);
        return ResponseEntity.status(HttpStatus.CREATED).body(categoria);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Categoria atualizada",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Categoria.class))}),
            @ApiResponse(responseCode = "400", description = "Categoria já cadastrada.", content = @Content),
            @ApiResponse(responseCode = "404", description = "Categoria não encontrada.", content = @Content)})
    @PutMapping("/{codigo}")
    public ResponseEntity<Categoria> atualizarCategoria(@PathVariable Long codigo, @RequestBody Categoria categoria){
        Categoria catergoria = categoriaService.atualizarCategoria(codigo, categoria);
        return ResponseEntity.ok().body(categoria);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Categoria deletada",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Categoria.class))}),
            @ApiResponse(responseCode = "400", description = "Categoria inválida", content = @Content),
            @ApiResponse(responseCode = "404", description = "Categoria não encontrada", content = @Content)})
    @DeleteMapping("/{codigo}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long codigo) {
        categoriaService.deletarCategoria(codigo);
    }
}
