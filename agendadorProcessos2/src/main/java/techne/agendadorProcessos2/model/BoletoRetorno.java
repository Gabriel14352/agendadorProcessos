package techne.agendadorProcessos2.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "boleto_retorno")
public class BoletoRetorno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 20, nullable = false)
    private String numeroBoleto;


    private String descricao;

    @Column(precision = 15, scale = 2, nullable = false)
    private BigDecimal valor;

    @Column(nullable = false)
    private LocalDate dataPagamento;

    @Column(length = 20, nullable = false)
    private String status;

    // getters e setterss

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }


    public String getNumeroBoleto() { return numeroBoleto; }
    public void setNumeroBoleto(String numeroBoleto) { this.numeroBoleto = numeroBoleto; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public BigDecimal getValor() { return valor; }
    public void setValor(BigDecimal valor) { this.valor = valor; }

    public LocalDate getDataPagamento() { return dataPagamento; }
    public void setDataPagamento(LocalDate dataPagamento) { this.dataPagamento = dataPagamento; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
