package com.evendas.dto.categoria;

import com.evendas.model.Categoria;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Resposta da Categoria - DTO",
        name = "Categoria Response")
public record CategoriaResponseDTO(

        @Schema(description = "Código gerado quando salvo no banco de dados",
                name = "codigo")
        Long codigo,

        @Schema(description = "Nome da categoria.",
                name = "nome")
        String nome
) {
    public static CategoriaResponseDTO converterParaCategoriaDTO(Categoria categoria) {
        return new CategoriaResponseDTO(categoria.getCodigo(), categoria.getNome());
    }
}
