package com.bugbusters.backend.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ActiveProfiles("test")
class ControllerRoutesTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    // ==========================================
    // 1. Regras Controller
    // ==========================================
    @Test
    @DisplayName("POST /api/v1/regras - Deve criar regra válida com sucesso (201)")
    void deveCriarRegraValida() throws Exception {
        String payload = """
            {
                "nome": "Comissão Black Friday",
                "canal": "ECOMMERCE",
                "taxa": 0.0500,
                "dataInicio": "2026-11-01",
                "dataFim": "2026-11-30"
            }
            """;

        mockMvc.perform(post("/api/v1/regras")
                .contentType(MediaType.APPLICATION_JSON)
                .content(payload))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nome").value("Comissão Black Friday"))
                .andExpect(jsonPath("$.canal").value("ECOMMERCE"))
                .andExpect(jsonPath("$.taxa").value(0.0500))
                .andExpect(jsonPath("$.status").value("ATIVA"))
                .andExpect(jsonPath("$.dataFim").value("2026-11-30"));
    }

    @Test
    @DisplayName("POST /api/v1/regras - Deve calcular dataFim (+30 dias) quando omitida")
    void deveCalcularDataFimQuandoOmitida() throws Exception {
        String payload = """
            {
                "nome": "Regra Sem Fim",
                "canal": "LOJA_FISICA",
                "taxa": 0.0800,
                "dataInicio": "2026-10-01"
            }
            """;

        mockMvc.perform(post("/api/v1/regras")
                .contentType(MediaType.APPLICATION_JSON)
                .content(payload))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.dataInicio").value("2026-10-01"))
                .andExpect(jsonPath("$.dataFim").value("2026-10-31"));
    }

    @Test
    @DisplayName("POST /api/v1/regras - Deve rejeitar payload inválido com 400 e lista de validações")
    void deveRejeitarRegraInvalida() throws Exception {
        String payload = """
            {
                "nome": "",
                "taxa": -0.05
            }
            """;

        mockMvc.perform(post("/api/v1/regras")
                .contentType(MediaType.APPLICATION_JSON)
                .content(payload))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.message").value("Dados de entrada inválidos."))
                .andExpect(jsonPath("$.validacoes", hasSize(greaterThan(0))));
    }

    @Test
    @DisplayName("GET /api/v1/regras - Deve listar regras com 200 OK")
    void deveListarRegras() throws Exception {
        mockMvc.perform(get("/api/v1/regras"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(greaterThan(0))))
                .andExpect(jsonPath("$[0].canal").value("ECOMMERCE"));
    }

    @Test
    @DisplayName("GET /api/v1/regras/{id} - Deve buscar regra por ID com 200 OK")
    void deveBuscarRegraPorId() throws Exception {
        mockMvc.perform(get("/api/v1/regras/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.status").value("ATIVA"));
    }

    @Test
    @DisplayName("DELETE /api/v1/regras/{id} - Deve desativar regra com 204 No Content")
    void deveDesativarRegra() throws Exception {
        mockMvc.perform(delete("/api/v1/regras/1"))
                .andExpect(status().isNoContent());
    }

    // ==========================================
    // 2. Calculo Controller
    // ==========================================
    @Test
    @DisplayName("POST /api/v1/comissoes/calcular - Deve processar cálculo com 200 OK")
    void deveCalcularComissao() throws Exception {
        String payload = """
            {
                "idVenda": "VENDA-1234",
                "valorVenda": 1000.00,
                "canal": "ECOMMERCE",
                "dataVenda": "2026-10-05"
            }
            """;

        mockMvc.perform(post("/api/v1/comissoes/calcular")
                .contentType(MediaType.APPLICATION_JSON)
                .content(payload))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.protocoloCalculo").isNotEmpty())
                .andExpect(jsonPath("$.idVenda").value("VENDA-1234"))
                .andExpect(jsonPath("$.valorOriginal").value(1000.00))
                .andExpect(jsonPath("$.valorComissao").value(100.00));
    }

    @Test
    @DisplayName("GET /api/v1/logs-calculo - Deve listar logs com 200 OK")
    void deveListarLogs() throws Exception {
        mockMvc.perform(get("/api/v1/logs-calculo"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(greaterThan(0))))
                .andExpect(jsonPath("$[0].idVenda").value("V1001"));
    }

    // ==========================================
    // 3. Interpretador Controller
    // ==========================================
    @Test
    @DisplayName("POST /api/v1/interpretador/extrair-regra - Deve extrair parâmetros com 200 OK")
    void deveInterpretarRegra() throws Exception {
        String payload = """
            {
                "textoLivre": "comissão de 5% no ecommerce para dezembro"
            }
            """;

        mockMvc.perform(post("/api/v1/interpretador/extrair-regra")
                .contentType(MediaType.APPLICATION_JSON)
                .content(payload))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.canal").value("ecommerce"))
                .andExpect(jsonPath("$.taxa").value(0.0500))
                .andExpect(jsonPath("$.dataInicio").value("2026-12-01"))
                .andExpect(jsonPath("$.dataFim").value("2026-12-31"))
                .andExpect(jsonPath("$.confianca").value(0.98));
    }

    @Test
    @DisplayName("POST /api/v1/interpretador/extrair-regra - Deve rejeitar texto vazio com 400")
    void deveRejeitarTextoLivreVazio() throws Exception {
        String payload = """
            {
                "textoLivre": "   "
            }
            """;

        mockMvc.perform(post("/api/v1/interpretador/extrair-regra")
                .contentType(MediaType.APPLICATION_JSON)
                .content(payload))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.validacoes[0].campo").value("textoLivre"));
    }

    // ==========================================
    // 4. Importacao Controller
    // ==========================================
    @Test
    @DisplayName("POST /api/v1/importacoes/upload - Deve realizar upload multipart com 200 OK")
    void deveRealizarUploadMultipart() throws Exception {
        MockMultipartFile file = new MockMultipartFile(
                "arquivo",
                "vendas_outubro.csv",
                "text/csv",
                "idVenda,valor,canal\nV1,500,ECOMMERCE".getBytes()
        );

        mockMvc.perform(multipart("/api/v1/importacoes/upload")
                .file(file)
                .param("tipoBase", "VENDAS"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nomeArquivo").value("vendas_outubro.csv"))
                .andExpect(jsonPath("$.tipoBase").value("VENDAS"))
                .andExpect(jsonPath("$.status").value("PROCESSADO_COM_AVISOS"))
                .andExpect(jsonPath("$.inconsistencias[0].campo").value("canal"))
                .andExpect(jsonPath("$.inconsistencias[0].motivo").value("Canal não preenchido; atribuído canal padrão."))
                .andExpect(jsonPath("$.inconsistencias[0].severidade").value("AVISO"));
    }
}