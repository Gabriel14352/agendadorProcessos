package techne.agendadorProcessos2.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "boletos_processados")

public class BoletoProcessado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // relacionamento com execucao do job
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "execucao_id", nullable = false)
    private JobExecucao execucao;

    @Column(nullable = false, length = 20)
    private String tipo;

    @Column(nullable = false)
    private Double valor;

    @Column(nullable = false)
    private LocalDate dataPagamento;

    @Column(length = 200)
    private String descricao;

    @Column(length = 50)
    private String status;

    // getters e setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public JobExecucao getExecucao() { return execucao; }
    public void setExecucao(JobExecucao execucao) { this.execucao = execucao; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public Double getValor() { return valor; }
    public void setValor(Double valor) { this.valor = valor; }

    public LocalDate getDataPagamento() { return dataPagamento; }
    public void setDataPagamento(LocalDate dataPagamento) { this.dataPagamento = dataPagamento; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

}
