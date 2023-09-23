package com.evendas.controller;

import com.evendas.model.Categoria;
import com.evendas.service.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("/{codigo}")
    public ResponseEntity<Optional<Categoria>> buscarCategoriaPorId(@PathVariable Long codigo){
        Optional<Categoria> categoria = categoriaService.buscarCategoriaPorId(codigo);
        return categoria.isPresent() ? ResponseEntity.ok().body(categoria) : ResponseEntity.notFound().build();
    }
}
