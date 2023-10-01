package com.evendas.service;

import com.evendas.exception.RegraNegocioException;
import com.evendas.model.Produto;
import com.evendas.repository.ProdutoRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private CategoriaService categoriaService;
    public List<Produto> listarTodos(Long codigoCategoria) {
        return produtoRepository.findByCategoriaCodigo(codigoCategoria);
    }

    public Produto buscarPorCodigo(Long codigo, Long codigoCategoria) {
        Optional<Produto> produto = produtoRepository.buscarPorCodigo(codigo, codigoCategoria);
        return produto.orElseThrow(() -> new EmptyResultDataAccessException(1));
    }

    public Produto salvar(Long codigoCategoria, Produto produto) {
        validarCategoriaDoProdutoExiste(codigoCategoria);
        validarProdutoDuplicado(produto);
        return produtoRepository.save(produto);
    }

    public Produto atualizar(Long codigoCategoria, Long codigoProduto, Produto produto) {
        Produto produtoSalvar = validarProdutoExiste(codigoProduto, codigoCategoria);
        validarCategoriaDoProdutoExiste(codigoCategoria);
        validarProdutoDuplicado(produto);
        BeanUtils.copyProperties(produto, produtoSalvar, "codigo");
        return produtoRepository.save(produtoSalvar);
    }

    public void deletar(Long codigoCategoria, Long codigoProduto) {
        Produto produto = validarProdutoExiste(codigoProduto, codigoCategoria);
        produtoRepository.delete(produto);
    }

    protected void atualizarQuantidadeEmEstoque(Produto produto) {
        produtoRepository.save(produto);
    }

    protected Produto validarProdutoExiste(Long codigoProduto) {
        Optional<Produto> produto = produtoRepository.findById(codigoProduto);
        if(produto.isEmpty()) {
            throw new RegraNegocioException(String.format("Produto de código %s não encontrado", codigoProduto));
        }
        return produto.get();
    }

    private Produto validarProdutoExiste(Long codigoProduto, Long codigoCategoria) {
        Produto produto = buscarPorCodigo(codigoProduto, codigoCategoria);
        if(produto == null) {
            throw new EmptyResultDataAccessException(1);
        }
        return produto;
    }


    private void validarProdutoDuplicado(Produto produto) {
        Optional<Produto> produtoPorDescricao = produtoRepository.findByCategoriaCodigoAndDescricao(produto.getCategoria().getCodigo(), produto.getDescricao());
        if(produtoPorDescricao.isPresent() && produtoPorDescricao.get().getCodigo() != produto.getCodigo()) {
            throw new RegraNegocioException(String.format("O produto %s já está cadastrado", produto.getDescricao()));
        }
    }

    private void validarCategoriaDoProdutoExiste(Long codigoCategoria) {
        if(codigoCategoria == null) {
            throw new RegraNegocioException("A categoria não pode ser nula");
        }

        if(categoriaService.buscarCategoriaPorId(codigoCategoria).isEmpty()) {
            throw new RegraNegocioException(String.format("A categoria de código %s informada não existe no cadastro", codigoCategoria));
        }
    }
}
