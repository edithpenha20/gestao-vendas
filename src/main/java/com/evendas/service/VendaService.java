package com.evendas.service;

import com.evendas.dto.venda.ClienteVendaResponseDTO;
import com.evendas.dto.venda.ItemVendaRequestDTO;
import com.evendas.dto.venda.VendaRequestDTO;
import com.evendas.dto.venda.VendaResponseDTO;
import com.evendas.exception.RegraNegocioException;
import com.evendas.model.Cliente;
import com.evendas.model.ItemVenda;
import com.evendas.model.Produto;
import com.evendas.model.Venda;
import com.evendas.repository.ItemVendaRepository;
import com.evendas.repository.VendaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class VendaService extends AbstractVendaService {

    @Autowired
    private VendaRepository vendaRepository;
    @Autowired
    private ItemVendaRepository itemVendaRepository;
    @Autowired
    private ClienteService clienteService;
    @Autowired
    private ProdutoService produtoService;

    public ClienteVendaResponseDTO listaVendaPorCliente(Long codigoCliente) {
        Cliente cliente = validarClienteVendaExiste(codigoCliente);
        List<VendaResponseDTO> vendaResponseDtoList = vendaRepository.findByClienteCodigo(codigoCliente).stream()
                .map(venda -> criandoVendaResponseDTO(venda, itemVendaRepository.findByVendaPorCodigo(venda.getCodigo())))
                .collect(Collectors.toList());
        return new ClienteVendaResponseDTO(cliente.getNome(), vendaResponseDtoList);
    }

    public ClienteVendaResponseDTO listarVendaPorCodigo(Long codigoVenda) {
        Venda venda = validarVendaExiste(codigoVenda);
        List<ItemVenda> itensVendaList = itemVendaRepository.findByVendaPorCodigo(venda.getCodigo());
        return retornandoClienteVendaResponseDTO(venda, itensVendaList);
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
    public ClienteVendaResponseDTO salvar(Long codigoCliente, VendaRequestDTO vendaDto) {
        Cliente cliente = validarClienteVendaExiste(codigoCliente);
        validarProdutoExisteEAtualizarQuantidade(vendaDto.itensVendaDto());
        Venda vendaSalva = salvarVenda(cliente, vendaDto);
        return retornandoClienteVendaResponseDTO(vendaSalva, itemVendaRepository.findByVendaPorCodigo(vendaSalva.getCodigo()));
    }

    public ClienteVendaResponseDTO atualizar(Long codigoVenda, Long codigoCliente, VendaRequestDTO vendaDto) {
        validarVendaExiste(codigoVenda);
        Cliente cliente = validarClienteVendaExiste(codigoCliente);
        List<ItemVenda> itensVendaList = itemVendaRepository.findByVendaPorCodigo(codigoVenda);
        validarProdutoExisteEDevolverEstoque(itensVendaList);
        validarProdutoExisteEAtualizarQuantidade(vendaDto.itensVendaDto());
        itemVendaRepository.deleteAll(itensVendaList);
        Venda vendaAtualizada = atualizarVenda(codigoVenda, cliente, vendaDto);
        return retornandoClienteVendaResponseDTO(vendaAtualizada, itemVendaRepository.findByVendaPorCodigo(vendaAtualizada.getCodigo()));
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
    public void deletar(Long codigoVenda) {
        validarVendaExiste(codigoVenda);
        List<ItemVenda> itensVenda = itemVendaRepository.findByVendaPorCodigo(codigoVenda);
        validarProdutoExisteEDevolverEstoque(itensVenda);
        itemVendaRepository.deleteAll(itensVenda);
        vendaRepository.deleteById(codigoVenda);
    }

    private void validarProdutoExisteEDevolverEstoque(List<ItemVenda> itensVenda) {
        itensVenda.forEach(item -> {
            Produto produto = produtoService.validarProdutoExiste(item.getProduto().getCodigo());
            produto.setQuantidade(produto.getQuantidade() + item.getQuantidade());
            produtoService.atualizarQuantidadeEmEstoque(produto);
        });
    }

    private Venda salvarVenda(Cliente cliente, VendaRequestDTO vendaDto) {
        Venda vendaSalva = vendaRepository.save(new Venda(vendaDto.data(), cliente));
        vendaDto.itensVendaDto().stream().map(itemVendaDto -> criandoItemVenda(itemVendaDto, vendaSalva))
                .forEach(itemVendaRepository::save);
        return vendaSalva;
    }

    private Venda atualizarVenda(Long codigoVenda, Cliente cliente, VendaRequestDTO vendaDto) {
        Venda vendaSalva = vendaRepository.save(new Venda(codigoVenda, vendaDto.data(), cliente));
        vendaDto.itensVendaDto().stream().map(itemVendaDto -> criandoItemVenda(itemVendaDto, vendaSalva))
                .forEach(itemVendaRepository::save);
        return vendaSalva;
    }

    private void validarProdutoExisteEAtualizarQuantidade(List<ItemVendaRequestDTO> itensVendaDto) {
        itensVendaDto.forEach(item -> {
            Produto produto = produtoService.validarProdutoExiste(item.codigoProduto());
            validarQuantidadeProdutoExiste(produto, item.quantidade());
            produto.setQuantidade(produto.getQuantidade() - item.quantidade());
            produtoService.atualizarQuantidadeEmEstoque(produto);
        });
    }

    private void validarQuantidadeProdutoExiste(Produto produto, Integer qtdeVendaDto) {
        if(!(produto.getQuantidade() >= qtdeVendaDto)) {
            throw new RegraNegocioException(String.format("A quantidade %s informada para o produto %s não está disponível em estoque",
                    qtdeVendaDto, produto.getDescricao()));
        }
    }

    private Venda validarVendaExiste(Long codigoVenda) {
        Optional<Venda> venda = vendaRepository.findById(codigoVenda);
        if (venda.isEmpty()) {
            throw new RegraNegocioException(String.format("Venda de código %s não encontrada.", codigoVenda));
        }
        return venda.get();
    }

    private Cliente validarClienteVendaExiste(Long codigoCliente) {
        Optional<Cliente> cliente = clienteService.buscarPorCodigo(codigoCliente);
        if (cliente.isEmpty()) {
            throw new RegraNegocioException(
                    String.format("O Cliente de código %s informado não existe no cadastro.", codigoCliente));
        }
        return cliente.get();
    }
}
