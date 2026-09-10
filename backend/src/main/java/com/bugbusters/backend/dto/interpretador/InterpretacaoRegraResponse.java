package com.bugbusters.backend.dto.interpretador;

import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Schema(description = "Proposta estruturada extraída pela IA com pendências apontadas")
public record InterpretacaoRegraResponse(
        @Schema(description = "Canal padronizado identificado", example = "ECOMMERCE")
        String canal,

        @Schema(description = "Taxa percentual em formato decimal (0.0500 = 5%)", example = "0.0500")
        BigDecimal taxa,

        @Schema(description = "Data de início da vigência inferida", example = "2026-12-01")
        LocalDate dataInicio,

        @Schema(description = "Data de fim da vigência inferida", example = "2026-12-31")
        LocalDate dataFim,

        @Schema(description = "Score de confiança do modelo (0.00 a 1.00)", example = "0.95")
        BigDecimal confianca,

        @Schema(description = "Lista de pendências, campos faltantes ou inconsistências que exigem revisão humana")
        List<String> pendencias
) {}