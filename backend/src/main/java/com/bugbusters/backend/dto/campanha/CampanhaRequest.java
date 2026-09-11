package com.bugbusters.backend.dto.campanha;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalDate;

@Schema(description = "Dados para cadastro ou edição de campanha com regra vinculada")
public record CampanhaRequest(
        @Schema(description = "Título descritivo da campanha", example = "Campanha Black Friday 2026")
        @NotBlank(message = "O título da campanha é obrigatório")
        String titulo,

        @Schema(description = "Texto original em linguagem natural que originou a proposta", example = "Comissão de 5% para vendas no e-commerce em dezembro")
        @NotBlank(message = "O texto original da campanha é obrigatório")
        String textoOriginal,

        @Schema(description = "Canal de aplicação da regra (dimensão própria)", example = "ECOMMERCE")
        @NotBlank(message = "O canal da regra é obrigatório")
        String canal,

        @Schema(description = "Taxa decimal de comissão (ex: 0.0500 = 5%)", example = "0.0500")
        @NotNull(message = "A taxa de comissão é obrigatória")
        @Positive(message = "A taxa de comissão deve ser maior que zero")
        BigDecimal taxa,

        @Schema(description = "Data de início da vigência. Se nula, assume a data atual.", example = "2026-10-01")
        LocalDate dataInicio,

        @Schema(description = "Data final da vigência. Se ausente, aplica-se automaticamente 30 dias a partir da data atual.", example = "2026-10-31")
        LocalDate dataFim
) {}