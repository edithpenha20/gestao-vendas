package com.evendas.client;

import com.evendas.dto.cliente.EnderecoResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(url="https://viacep.com.br/ws/", name = "viacep")
public interface ViaCepClientService {
    @GetMapping("{cep}/json")
    EnderecoResponseDTO recebeInfoCep(@PathVariable("cep") String cep);
}
