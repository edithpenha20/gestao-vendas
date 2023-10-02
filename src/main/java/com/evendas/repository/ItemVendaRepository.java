package com.evendas.repository;

import com.evendas.model.ItemVenda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ItemVendaRepository extends JpaRepository<ItemVenda, Long> {

    @Query("select new com.gvendas.gestaovendas.entidades.ItemVenda("
            + " iv.codigo, iv.quantidade, iv.precoVendido, iv.produto, iv.venda)"
            + "  from ItemVenda iv"
            + " where iv.venda.codigo = :codigoVenda")
    List<ItemVenda> findByVendaPorCodigo(Long codigoVenda);
}
