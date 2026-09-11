package com.bugbusters.backend.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "tb_log_calculo_imutavel")
public class LogCalculoImutavel {

    @Id
    private UUID id;

    @Column(nullable = false)
    private UUID protocolo;

    @Column(name = "id_venda", nullable = false, length = 100)
    private String idVenda;

    @Column(name = "id_regra", nullable = false)
    private Long idRegra;

    @Column(name = "valor_original", nullable = false, precision = 15, scale = 2)
    private BigDecimal valorOriginal;

    @Column(name = "taxa_aplicada", nullable = false, precision = 6, scale = 4)
    private BigDecimal taxaAplicada;

    @Column(name = "valor_comissao", nullable = false, precision = 15, scale = 2)
    private BigDecimal valorComissao;

    @Column(nullable = false, length = 100)
    private String canal;

    @Column(name = "origem_execucao", nullable = false, length = 50)
    private String origemExecucao;

    @Column(name = "usuario_executor", length = 100)
    private String usuarioExecutor;

    @Column(name = "executado_em", nullable = false, updatable = false)
    private OffsetDateTime executadoEm;

    public LogCalculoImutavel() {}

    @PrePersist
    public void prePersist() {
        if (this.id == null) {
            this.id = UUID.randomUUID();
        }
        if (this.executadoEm == null) {
            this.executadoEm = OffsetDateTime.now();
        }
    }

    public LogCalculoImutavel(UUID protocolo, String idVenda, Long idRegra, BigDecimal valorOriginal,
                              BigDecimal taxaAplicada, BigDecimal valorComissao, String canal, String origemExecucao) {
        this.id = UUID.randomUUID();
        this.protocolo = protocolo;
        this.idVenda = idVenda;
        this.idRegra = idRegra;
        this.valorOriginal = valorOriginal;
        this.taxaAplicada = taxaAplicada;
        this.valorComissao = valorComissao;
        this.canal = canal;
        this.origemExecucao = origemExecucao;
        this.usuarioExecutor = "SISTEMA";
        this.executadoEm = OffsetDateTime.now();
    }

    // Getters
    public UUID getId() { return id; }

    public UUID getProtocolo() { return protocolo; }

    public String getIdVenda() { return idVenda; }
    
    public Long getIdRegra() { return idRegra; }

    public BigDecimal getValorOriginal() { return valorOriginal; }

    public BigDecimal getTaxaAplicada() { return taxaAplicada; }

    public BigDecimal getValorComissao() { return valorComissao; }

    public String getCanal() { return canal; }

    public String getOrigemExecucao() { return origemExecucao; }

    public String getUsuarioExecutor() { return usuarioExecutor; }
    
    public OffsetDateTime getExecutadoEm() { return executadoEm; }
}