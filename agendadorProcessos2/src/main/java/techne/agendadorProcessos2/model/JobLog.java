package techne.agendadorProcessos2.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class JobLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;

    private String jobName;

    @Enumerated(EnumType.STRING)
    private Status status;

    private String mensagem;

    private LocalDateTime dataHora;

    //construtores
    public JobLog(){}

    public JobLog(String jobName, Status status, String mensagem){
        this.jobName = jobName;
        this.status = status;
        this.mensagem = mensagem;
        this.dataHora = LocalDateTime.now();
    }

    //getters e setters

    public Long getId() {return id;}
    public String getJobName() { return jobName;}
    public void setJobName(String jobName) {this.jobName = jobName;}
    public Status getStatus() {return status;}
    public void setStatus(Status status) {this.status = status;}
    public String getMensagem() {return mensagem = mensagem;}
    public void setMensagem(String mensagem) {this.mensagem = mensagem;}
    public LocalDateTime getDataHora() {return dataHora;}
    public void setDataHora(LocalDateTime dataHora) {this.dataHora = dataHora;}

    public enum Status{
        PROCESSANDO,
        SUCESSO,
        ERRO
    }


}
