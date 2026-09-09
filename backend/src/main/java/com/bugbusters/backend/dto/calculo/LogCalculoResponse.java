package com.bugbusters.backend.dto.calculo;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Log imutável de auditoria de cálculo financeiro")
public record LogCalculoResponse(
    UUID idLog,
    String idVenda,
    Long idRegra,
    BigDecimal valorOriginal,
    BigDecimal valorComissao,
    BigDecimal taxaAplicada,
    OffsetDateTime executadoEm
) {}
