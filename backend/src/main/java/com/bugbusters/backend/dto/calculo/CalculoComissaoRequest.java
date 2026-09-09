package com.bugbusters.backend.dto.calculo;

import java.math.BigDecimal;
import java.time.LocalDate;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Schema(description = "Entrada para apuração de comissão sobre uma venda")
public record CalculoComissaoRequest(
    @Schema(description = "Identificador externo da venda", example = "VENDA-2026-9988")
    @NotBlank(message = "O ID da venda é obrigatório")
    String idVenda,

    @Schema(description = "Valor bruto transacionado", example = "1000.00")
    @NotNull(message = "O valor da venda é obrigatório")
    @Positive(message = "O valor da venda deve ser positivo")
    BigDecimal valorVenda,

    @Schema(description = "Canal onde a venda ocorreu", example = "ECOMMERCE")
    @NotBlank(message = "O canal da venda é obrigatório")
    String canal,

    @Schema(description = "Data de ocorrência da venda", example = "2026-10-05")
    @NotNull(message = "A data da venda é obrigatória")
    LocalDate dataVenda
) {}