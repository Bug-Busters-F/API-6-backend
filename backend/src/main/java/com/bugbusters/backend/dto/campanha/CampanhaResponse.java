package com.bugbusters.backend.dto.campanha;

import com.bugbusters.backend.dto.regra.StatusRegra;
import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;

@Schema(description = "Representação detalhada da campanha e da regra vinculada")
public record CampanhaResponse(
        Long id,
        String titulo,
        String textoOriginal,
        String estado,
        LocalDate dataInicio,
        LocalDate dataFim,
        RegraVinculadaDTO regra,
        OffsetDateTime criadoEm,
        OffsetDateTime atualizadoEm
) {
    public record RegraVinculadaDTO(
            Long id,
            String nome,
            String canal,
            BigDecimal taxa,
            LocalDate dataInicio,
            LocalDate dataFim,
            StatusRegra status
    ) {}
}