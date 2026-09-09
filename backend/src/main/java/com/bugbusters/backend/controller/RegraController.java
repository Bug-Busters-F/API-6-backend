package com.bugbusters.backend.controller;

import java.time.LocalDate;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bugbusters.backend.dto.error.ApiErrorResponse;
import com.bugbusters.backend.dto.regra.RegraRequest;
import com.bugbusters.backend.dto.regra.RegraResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController 
@RequestMapping("/api/v1/regras")
@Tag(name = "1. Regras e campanhas", description = "Endpoints de gerenciamento manual do ciclo de vida das regras") 
public class RegraController {
    @Operation(summary = "Cadastrar nova regra", description = "Cria uma regra de comissão manual. Se dataFim for omitida, o sistema atribuirá 30 dias a partir da dataInicio.")
    @ApiResponse(responseCode = "201", description = "Regra criada com sucesso")
    @ApiResponse(responseCode = "400", description = "Dados inválidos", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    @PostMapping 
    public ResponseEntity<RegraResponse> criarRegra(@Valid @RequestBody RegraRequest request) {
        LocalDate fimCalculado = request.dataFim() != null ? request.dataFim() : // Continuar aqui
    }
