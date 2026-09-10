package com.bugbusters.backend.controller;

import com.bugbusters.backend.dto.importacao.RelatorioImportacaoResponse;
import com.bugbusters.backend.dto.importacao.RelatorioImportacaoResponse.ItemInconsistenciaDTO;
import com.bugbusters.backend.dto.importacao.SeveridadeInconsistencia;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.OffsetDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/importacoes")
@Tag(name = "3. Ingestão de Dados", description = "Upload e validação estrutural de planilhas/CSVs (RH, Vendas, Histórico)")
public class ImportacaoController {

    @Operation(summary = "Upload de base de dados com validação", description = "Processa o arquivo CSV, identifica falhas impeditivas ou avisos e rejeita integralmente caso ocorra duplicidade ou ausência de campos chave.")
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<RelatorioImportacaoResponse> uploadBase(
            @RequestParam("tipoBase") @Parameter(description = "Tipo: VENDAS, RH ou COMISSOES") String tipoBase,
            @RequestPart("arquivo") MultipartFile arquivo) {

        RelatorioImportacaoResponse mockResponse = new RelatorioImportacaoResponse(
                arquivo.getOriginalFilename(),
                tipoBase,
                "PROCESSADO_COM_AVISOS",
                100,
                99,
                false,
                List.of(new ItemInconsistenciaDTO(12, "canal", "Canal não preenchido; atribuído canal padrão.", SeveridadeInconsistencia.AVISO)),
                OffsetDateTime.now()
        );
        return ResponseEntity.ok(mockResponse);
    }
}