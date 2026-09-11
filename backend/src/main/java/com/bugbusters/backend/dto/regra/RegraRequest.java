package com.bugbusters.backend.dto.regra;

import java.math.BigDecimal;
import java.time.LocalDate;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Schema(description = "Payload para criação ou atualização manual de regra de comissão")
public record RegraRequest(
    @Schema(description = "Nome de identificação da regra", example = "Comissão Black Friday E-commerce")
    @NotBlank(message = "O nome da regra é obrigatório")
    String nome,

    @Schema(description = "Canal de venda (dimensão independente de loja/marca", example = "ECOMMERCE")
    @NotBlank(message = "O canal é obrigatório")
    String canal,

    @Schema(description = "Taxa de comissão em formato decimal (0.05 = 5%)", example = "0.0500")
    @NotNull(message = "A taxa de comissão é obrigatória")
    @Positive(message = "A taxa deve ser maior que zero")
    BigDecimal taxa,

    @Schema(description = "Data de início de vigência (YYYY-MM-DD)", example = "2026-10-01")
    @NotNull(message = "A data de início é obrigatória")
    LocalDate dataInicio,

    @Schema(description = "Data de fim da vigência. Se ausente, aplicam-se 30 dias automaticamente.", example = "2026-10-31")
    LocalDate dataFim
) {}