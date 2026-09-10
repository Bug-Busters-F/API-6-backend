package com.bugbusters.backend.controller;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bugbusters.backend.dto.error.ApiErrorResponse;
import com.bugbusters.backend.dto.interpretador.InterpretacaoRegraRequest;
import com.bugbusters.backend.dto.interpretador.InterpretacaoRegraResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.web.bind.annotation.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController 
@RequestMapping("/api/v1/interpretador")
@Tag(name = "4. Assistência por IA (NLP)", description = "Contrato de comunicação com o orquestrador de IA/Python")
public class InterpretadorController {
    @Operation(summary = "Interpretar regra em texto livre", description = "Recebe um comando textual e devolve os parâmetros da regra estruturados em JSON para confirmação.")
    @ApiResponse(responseCode = "200", description = "Texto interpretado com sucesso")
    @ApiResponse(responseCode = "400", description = "Comando incompreensível ou ambíguo", content = @Content (schema = @Schema(implementation = ApiErrorResponse.class)))
    @PostMapping ("/extrair-regra")
    public ResponseEntity<InterpretacaoRegraResponse> interpretarRegra(@Valid @RequestBody InterpretacaoRegraRequest request) {
        return ResponseEntity.ok(new InterpretacaoRegraResponse(
            "ecommerce",
            new BigDecimal("0.0500"),
            LocalDate.of(2026, 12, 1),
            LocalDate.of(2026, 12, 31),
            new BigDecimal("0.98"),
            Collections.emptyList()
        ));
    }
}
