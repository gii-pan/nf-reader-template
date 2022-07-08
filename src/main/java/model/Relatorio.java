package model;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "relatorio")
public class Relatorio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String destinatario;

    @Column(name = "valor_total")
    private BigDecimal valorTotal;

    public Relatorio() {

    }

    public String getDestinatario() {
        return destinatario;
    }

    public void setDestinatario(String destinatario) {
        this.destinatario = destinatario;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }

    public Relatorio(String destinatario, BigDecimal valorTotal) {
        this.destinatario = destinatario;
        this.valorTotal = valorTotal;
    }
}
