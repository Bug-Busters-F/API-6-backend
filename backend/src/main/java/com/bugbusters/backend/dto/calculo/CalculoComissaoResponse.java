package com.bugbusters.backend.dto.calculo;

import java.math.BigDecimal;
import java.util.UUID;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Resultado do processamento da comissão")
public record CalculoComissaoResponse(
    @Schema(description = "UUID imutável gerado para auditoria do cálculo", example = "7b2e652a-9941-4770-9852-51322ab5e1f0")
    UUID protocoloCalculo,

    @Schema(description = "ID da venda avaliada", example = "VENDA-2026-9988")
    String idVenda,

    @Schema(description = "ID da regra que foi aplicada", example = "1")
    Long idRegraAplicada,

    @Schema(description = "Taxa decimal utilizada", example = "0.1000")
    BigDecimal taxaAplicada,

    @Schema(description = "Valor base da venda", example = "1000.00")
    BigDecimal valorOriginal,

    @Schema(description = "Valor apurado da comissão a pagar", example = "100.00")
    BigDecimal valorComissao
) {}
