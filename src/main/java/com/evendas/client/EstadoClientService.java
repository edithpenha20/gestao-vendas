package com.evendas.client;

import com.evendas.dto.cliente.EstadoDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(url="https://servicodados.ibge.gov.br/api/v1/localidades", name = "ibge")
public interface EstadoClientService {

    @GetMapping("/estados")
    List<EstadoDTO> getEstados();
}
