package com.bugbusters.backend.dto.regra;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Dados detalhados da regra cadastrada")
public record RegraResponse(
    @Schema(description = "Identificador único da regra", example = "1")
    Long id,

    @Schema(description = "Nome da regra", example = "Comissão Black Friday E-commerce")
    String nome,

    @Schema(description = "Canal de venda vinculado", example = "ECOMMERCE")
    String canal,

    @Schema(description = "Taxa decimal aplicada", example = "0.0500")
    BigDecimal taxa,

    @Schema(description = "Início da vigência", example = "2026-10-01")
    LocalDate dataInicio,

    @Schema(description = "Fim da vigência", example = "2026-10-31")
    LocalDate dataFim,

    @Schema(description = "Estado atual da regra", example = "ATIVA")
    StatusRegra status,

    @Schema(description = "Data e hora de cadastro", example = "2026-09-08T19:00:00Z")
    OffsetDateTime criadoEm
) {}
