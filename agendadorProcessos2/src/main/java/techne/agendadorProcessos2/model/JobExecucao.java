package techne.agendadorProcessos2.model;

import jakarta.persistence.*;

import javax.annotation.processing.Generated;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "job_execucao")
public class JobExecucao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "job_id", nullable = false)
    @com.fasterxml.jackson.annotation.JsonIgnore
    private Job job;

    private LocalDateTime inicio;
    private LocalDateTime fim;

    @Enumerated(EnumType.STRING)
    private Status status;

    private String mensagem;

    @OneToMany(mappedBy = "execucao", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @com.fasterxml.jackson.annotation.JsonIgnore
    private List<ArquivoRetorno> arquivos;

    // getters e setter

    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}

    public Job getJob() { return job; }
    public void setJob(Job job) { this.job = job; }

    public LocalDateTime getInicio() { return inicio; }
    public void setInicio(LocalDateTime inicio) { this.inicio = inicio; }

    public LocalDateTime getFim() { return fim; }
    public void setFim(LocalDateTime fim) { this.fim = fim; }

    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }

    public String getMensagem() { return mensagem; }
    public void setMensagem(String mensagem) { this.mensagem = mensagem; }
}