package com.evendas.service;

import com.evendas.dto.venda.ClienteVendaResponseDTO;
import com.evendas.dto.venda.ItemVendaRequestDTO;
import com.evendas.dto.venda.ItemVendaResponseDTO;
import com.evendas.dto.venda.VendaResponseDTO;
import com.evendas.model.ItemVenda;
import com.evendas.model.Produto;
import com.evendas.model.Venda;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public abstract class AbstractVendaService {

    protected ClienteVendaResponseDTO retornandoClienteVendaResponseDTO(Venda venda, List<ItemVenda> itensVendaList) {
        return new ClienteVendaResponseDTO(venda.getCliente().getNome(), Arrays.asList(
                criandoVendaResponseDTO(venda, itensVendaList)));
    }

    protected VendaResponseDTO criandoVendaResponseDTO(Venda venda, List<ItemVenda> itensVendaList) {
        List<ItemVendaResponseDTO> itensVendaResponseDto = itensVendaList.stream()
                .map(this::criandoItensVendaResponseDto).collect(Collectors.toList());
        return new VendaResponseDTO(venda.getCodigo(), venda.getData(), itensVendaResponseDto);

    }

    protected ItemVendaResponseDTO criandoItensVendaResponseDto(ItemVenda itemVenda) {
        return new ItemVendaResponseDTO(itemVenda.getCodigo(), itemVenda.getQuantidade(), itemVenda.getPrecoVendido(),
                itemVenda.getProduto().getCodigo(), itemVenda.getProduto().getDescricao());
    }

    protected ItemVenda criandoItemVenda(ItemVendaRequestDTO itemVendaDto, Venda venda) {
        return new ItemVenda(itemVendaDto.quantidade(), itemVendaDto.precoVendido(),
                new Produto(itemVendaDto.codigoProduto()), venda);
    }
}
