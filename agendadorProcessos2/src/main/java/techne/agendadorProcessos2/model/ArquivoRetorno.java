package techne.agendadorProcessos2.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "arquivo_retorno")
public class ArquivoRetorno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "job_id", nullable = false)
    @com.fasterxml.jackson.annotation.JsonIgnore  // 👈 evita loop
    private Job job;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "execucao_id")
    @com.fasterxml.jackson.annotation.JsonIgnore  // 👈 evita loop
    private JobExecucao execucao;

    @Column(nullable = false, length = 200)
    private String nomeArquivo;

    @Column(name = "caminho_arquivo", nullable = false, length = 500)
    private String caminhoArquivo;

    @Column(nullable = false, length = 20)
    private String status;

    private Long tamanhoBytes;

    @Column(length = 500)
    private String mensagem;

    @Column(nullable = false)
    private LocalDateTime criadoEm;

    private LocalDateTime processadoEm;

    // getters/setters
    public Long getId() { return id; }

    public Job getJob() { return job; }
    public void setJob(Job job) { this.job = job; }

    public JobExecucao getExecucao() { return execucao; }
    public void setExecucao(JobExecucao execucao) { this.execucao = execucao; }

    public String getNomeArquivo() { return nomeArquivo; }
    public void setNomeArquivo(String nomeArquivo) { this.nomeArquivo = nomeArquivo; }

    public String getCaminhoArquivo() { return caminhoArquivo; }
    public void setCaminhoArquivo(String caminhoArquivo) { this.caminhoArquivo = caminhoArquivo; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Long getTamanhoBytes() { return tamanhoBytes; }
    public void setTamanhoBytes(Long tamanhoBytes) { this.tamanhoBytes = tamanhoBytes; }

    public String getMensagem() { return mensagem; }
    public void setMensagem(String mensagem) { this.mensagem = mensagem; }

    public LocalDateTime getCriadoEm() { return criadoEm; }
    public void setCriadoEm(LocalDateTime criadoEm) { this.criadoEm = criadoEm; }

    public LocalDateTime getProcessadoEm() { return processadoEm; }
    public void setProcessadoEm(LocalDateTime processadoEm) { this.processadoEm = processadoEm; }
}
