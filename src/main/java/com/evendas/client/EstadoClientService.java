package com.evendas.client;

import com.evendas.dto.cliente.EnderecoResponseDTO;
import com.evendas.dto.cliente.EstadoDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(url="https://viacep.com.br/ws/", name = "viacep")
public interface EstadoClientService {

    @GetMapping("/estados")
    List<EstadoDTO> getEstados();
}
