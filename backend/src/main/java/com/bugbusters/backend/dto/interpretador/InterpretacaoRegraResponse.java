package com.bugbusters.backend.dto.interpretador;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "JSON estruturado retornado pelo serviço de IA para pré-visualização")
public record InterpretacaoRegraResponse(
    @Schema(description = "Canal identificado", example = "ecommerce")
    String canal,

    @Schema(description = "Taxa decimal deduzida (ex: 0.05 para 5%", example = "0.0500")
    BigDecimal taxa,

    @Schema(description = "Data de início inferida", example = "2026-12-01")
    LocalDate dataInicio,

    @Schema(description = "Data de término inferida", example = "2026-12-31")
    LocalDate dataFim,

    @Schema(description = "Nível de confiança da extração (0.0 a 1.0)", example = "0.95")
    BigDecimal confianca,

    @Schema(description = "Pendências ou ambiguidades detectadas pela IA que exigem confirmação do usuário")
    List<String> pendencias
) {}
