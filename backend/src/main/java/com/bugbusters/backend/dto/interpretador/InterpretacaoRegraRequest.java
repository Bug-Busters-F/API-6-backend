package com.bugbusters.backend.dto.interpretador;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Comando em linguagem natural para geração de regra")
public record InterpretacaoRegraRequest(
    @Schema(description = "Instrução em texto livre", example = "pagar 5% no ecommerce de dezembro")
    @NotBlank(message = "O texto do comando não pode estar vazio")
    String textoLivre
) {}
