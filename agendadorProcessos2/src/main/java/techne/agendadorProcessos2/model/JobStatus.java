package techne.agendadorProcessos2.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "job_status")
public class JobStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "job_id", nullable = false)
    private Job job;

    // status atual
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Status status;

    // msg de erro/sucesso
    @Column(length = 500)
    private String mensagem;

    // quando o status foi registrado
    @Column(nullable = false)
    private LocalDateTime dataRegistro;

    // construtor vazio exigido pelo JPA
    public JobStatus() {}

    public JobStatus(Job job, Status status, String mensagem) {
        this.job = job;
        this.status = status;
        this.mensagem = mensagem;
        this.dataRegistro = LocalDateTime.now();
    }

    // getters e setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Job getJob() { return job; }
    public void setJob(Job job) { this.job = job; }

    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }

    public String getMensagem() { return mensagem; }
    public void setMensagem(String mensagem) { this.mensagem = mensagem; }

    public LocalDateTime getDataRegistro() { return dataRegistro; }
    public void setDataRegistro(LocalDateTime dataRegistro) { this.dataRegistro = dataRegistro; }
}
