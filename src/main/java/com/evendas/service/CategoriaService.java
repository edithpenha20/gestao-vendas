package com.evendas.service;

import com.evendas.exception.RegraNegocioException;
import com.evendas.model.Categoria;
import com.evendas.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    public List<Categoria> listarTodasCategorias(){
        return categoriaRepository.findAll();
    }

    public Optional<Categoria> buscarCategoriaPorId(Long codigo) {
        return categoriaRepository.findById(codigo);
    }

    public Categoria salvarCategoria(Categoria categoria){
        validarCategoriaDuplicada(categoria);
        return  categoriaRepository.save(categoria);
    }

    public Categoria atualizarCategoria(Long codigo, Categoria categoria){
        Optional<Categoria> encontrada = buscarCategoriaPorId(codigo);
        validarCategoriaDuplicada(categoria);
        if (encontrada.isEmpty()){
            throw new EmptyResultDataAccessException(1);
        }
        encontrada.get().setNome(categoria.getNome());
        return categoriaRepository.save(encontrada.get());
    }

    public void deletarCategoria(Long codigo) {
        categoriaRepository.deleteById(codigo);
    }

    private void validarCategoriaDuplicada(Categoria categoria) {
        Categoria categoriaEncontrada = categoriaRepository.findByNome(categoria.getNome());
        if (categoriaEncontrada != null && categoriaEncontrada.getCodigo() != categoria.getCodigo()) {
            throw new RegraNegocioException(
                    String.format("A categoria %s já esta cadastrada", categoria.getNome().toUpperCase()));
        }
    }
}
