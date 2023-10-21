package com.evendas.service;

import com.evendas.client.EstadoClientService;
import com.evendas.client.ViaCepClientService;
import com.evendas.dto.cliente.EnderecoResponseDTO;
import com.evendas.dto.cliente.EstadoDTO;
import com.evendas.exception.RegraNegocioException;
import com.evendas.model.Cliente;
import com.evendas.repository.ClienteRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    EstadoClientService estadoClientService;

    @Autowired
    private ViaCepClientService viaCepClientService;

    @Autowired
    private ClienteRepository clienteRepository;

    public List<Cliente> listarTodos() {
        return clienteRepository.findAll();
    }

    public Optional<Cliente> buscarPorCodigo(Long codigo) {
        return clienteRepository.findById(codigo);
    }

    public Cliente salvar(Cliente cliente) {
        validarClienteDuplicado(cliente);
        return clienteRepository.save(cliente);
    }

    public Cliente atualizar(Long codigo, Cliente cliente) {
        Cliente clienteAtualizar = validarClienteExiste(codigo);
        validarClienteDuplicado(cliente);
        BeanUtils.copyProperties(cliente, clienteAtualizar, "codigo");
        return clienteRepository.save(clienteAtualizar);
    }

    public void deletar(Long codigo) {
        clienteRepository.deleteById(codigo);
    }

    public List<EstadoDTO> obterEstados() {
        List<EstadoDTO> estados = estadoClientService.getEstados();
        estados.sort((estado1, estado2) -> {
            if (estado1.nome().equals("São Paulo")) {
                return -1;
            } else if (estado1.nome().equals("Rio de Janeiro")) {
                if (estado2.nome().equals("São Paulo")) {
                    return 1;
                } else {
                    return -1;
                }
            }
            return estado1.nome().compareTo(estado2.nome());
        });

        return estados;
    }

    public EnderecoResponseDTO buscarEnderecoPorCep(String cep) {
        return viaCepClientService.recebeInfoCep(cep);
    }

    private Cliente validarClienteExiste(Long codigo) {
        Optional<Cliente> cliente = buscarPorCodigo(codigo);
        if(cliente.isEmpty()) {
            throw new EmptyResultDataAccessException(1);
        }
        return cliente.get();
    }

    private void validarClienteDuplicado(Cliente cliente) {
        Cliente clientePorNome = clienteRepository.findByNome(cliente.getNome());
        if (clientePorNome != null && clientePorNome.getCodigo() != cliente.getCodigo()) {
            throw new RegraNegocioException(
                    String.format("O cliente %s já está cadastrado", cliente.getNome().toUpperCase()));
        }
    }
}
