package techne.agendadorProcessos2.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "JOB")
public class Job {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String nome;

    @Column(name = "cronExpression", nullable = false, length = 50)
    private String cronExpression;

    @Enumerated(EnumType.STRING)   // 👈 agora usa enum
    @Column(nullable = false, length = 50)
    private Status status;

    private LocalDateTime ultimaExecucao;
    private LocalDateTime proximaExecucao;

    @OneToMany(mappedBy = "job", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @com.fasterxml.jackson.annotation.JsonIgnore
    private List<JobExecucao> execucoes;

    @OneToMany(mappedBy = "job", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @com.fasterxml.jackson.annotation.JsonIgnore
    private List<ArquivoRetorno> arquivos;

    // Getters e setters
    public Long getId(){ return id; }
    public void setId(Long id){ this.id = id; }

    public String getNome(){ return nome; }
    public void setNome(String nome){ this.nome = nome; }

    public String getCronExpression(){ return cronExpression; }
    public void setCronExpression(String cronExpression){ this.cronExpression = cronExpression; }

    public Status getStatus(){ return status; }
    public void setStatus(Status status){ this.status = status; }

    public LocalDateTime getUltimaExecucao(){ return ultimaExecucao; }
    public void setUltimaExecucao(LocalDateTime ultimaExecucao) { this.ultimaExecucao = ultimaExecucao; }

    public LocalDateTime getProximaExecucao() { return proximaExecucao; }
    public void setProximaExecucao(LocalDateTime proximaExecucao) { this.proximaExecucao = proximaExecucao; }

    public List<ArquivoRetorno> getArquivos() { return arquivos; }
    public void setArquivos(List<ArquivoRetorno> arquivos) { this.arquivos = arquivos; }
}
