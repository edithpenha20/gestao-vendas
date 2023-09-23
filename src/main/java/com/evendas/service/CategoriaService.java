package com.evendas.service;

import com.evendas.exception.RegraNegocioException;
import com.evendas.model.Categoria;
import com.evendas.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
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
        return  categoriaRepository.save(categoria);
    }

    public Categoria atualizarCategoria(Long codigo, Categoria categoria){
        Optional<Categoria> categoriaEncontrada = buscarCategoriaPorId(codigo);
        if (categoriaEncontrada.isEmpty()){
            throw new EmptyResultDataAccessException(1);
        } else if (categoriaEncontrada != null && categoriaEncontrada.get().getCodigo() != categoria.getCodigo()) {
            throw new RegraNegocioException(
                    String.format("A categoria %s já esta cadastrada", categoria.getNome().toUpperCase()));
        }
        categoriaEncontrada.get().setNome(categoria.getNome());
        return categoriaRepository.save(categoriaEncontrada.get());
    }

    public void deletarCategoria(Long codigo) {
        categoriaRepository.deleteById(codigo);
    }
}
