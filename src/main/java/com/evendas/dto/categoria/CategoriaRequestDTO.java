package com.evendas.dto.categoria;

import com.evendas.model.Categoria;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Requisição da Categoria - DTO",
        name = "Categoria Request")
public record CategoriaRequestDTO(

        @Schema(description = "Código gerado quando salvo no banco de dados",
                name = "codigo")
        Long codigo,

        @Schema(description = "Nome da categoria.",
                name = "nome")
        String nome
) {
//    public static CategoriaRequestDTO converterParaCategoriaDTO(Categoria categoria) {
//        return new CategoriaRequestDTO(categoria.getCodigo(), categoria.getNome());
//    }
}
