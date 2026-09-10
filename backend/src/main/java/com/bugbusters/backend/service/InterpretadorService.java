package com.bugbusters.backend.service;

import com.bugbusters.backend.dto.interpretador.InterpretacaoRegraRequest;
import com.bugbusters.backend.dto.interpretador.InterpretacaoRegraResponse;
import com.bugbusters.backend.service.client.AiServiceClient;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class InterpretadorService {

    private final AiServiceClient aiClient;

    public InterpretadorService(AiServiceClient aiClient) {
        this.aiClient = aiClient;
    }

    public InterpretacaoRegraResponse processarInterpretacao(InterpretacaoRegraRequest request) {
        // 1. Chamar o serviço de IA em Python
        InterpretacaoRegraResponse respostaBruta = aiClient.chamarServicoPython(request);

        // 2. Validação defensiva no Spring Boot (zero-trust)
        List<String> pendencias = new ArrayList<>();
        if (respostaBruta.pendencias() != null) {
            pendencias.addAll(respostaBruta.pendencias());
        }

        // Validação de Canal
        String canalSanitizado = respostaBruta.canal() != null ? respostaBruta.canal().trim().toUpperCase() : null;
        if (canalSanitizado == null || canalSanitizado.isBlank()) {
            pendencias.add("Canal de vendas não identificado no texto. Favor selecionar manualmente.");
        }

        // Validação de taxa decimal
        BigDecimal taxa = respostaBruta.taxa();
        if (taxa == null) {
            pendencias.add("Percentual de comissão não identificado.");
        } else if (taxa.compareTo(BigDecimal.ZERO) <= 0 || taxa.compareTo(new BigDecimal("1.0000")) > 0) {
            pendencias.add("A taxa inferida (" + taxa + ") é inconsistente. Deve estar entre 0.0001 (0.01%) e 1.0000 (100%).");
            taxa = null;
        }

        // Validação de datas
        LocalDate inicio = respostaBruta.dataInicio();
        LocalDate fim = respostaBruta.dataFim();

        if (inicio != null && fim != null && fim.isBefore(inicio)) {
            pendencias.add("A data final inferida (" + fim + ") é anterior à data inicial (" + inicio + ").");
            fim = null;
        }

        if (inicio == null) {
            pendencias.add("Data de início não identificada; será atribuída a data atual se não informada.");
        }

        if (fim == null) {
            pendencias.add("Data final omitida; serão aplicados 30 dias de vigência padrão na confirmação.");
        }

        // 3. Devolver proposta mapeada sem salvar nem ativar no banco de dados
        return new InterpretacaoRegraResponse(
                canalSanitizado,
                taxa,
                inicio,
                fim,
                respostaBruta.confianca() != null ? respostaBruta.confianca() : BigDecimal.ZERO,
                pendencias
        );
    }
}