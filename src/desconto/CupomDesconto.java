package desconto;

import repositorio.Identificavel;

import java.time.LocalDate;
import java.util.UUID;

public class CupomDesconto implements Identificavel {
    private final UUID id;
    private String codigo;
    private double percentual;
    private LocalDate dataValidade;
    private boolean utilizado;

    public CupomDesconto(String codigo, double percentual, LocalDate dataValidade) {
        this.id = UUID.randomUUID();
        this.codigo = codigo;
        this.percentual = percentual;
        this.dataValidade = dataValidade;
        this.utilizado = false;
    }

    @Override
    public UUID getId() {
        return id;
    }

    // Getters
    public String getCodigo() { return codigo; }
    public double getPercentual() { return percentual; }
    public LocalDate getDataValidade() { return dataValidade; }
    public boolean isUtilizado() { return utilizado; }

    public void setCodigo(String codigo) { this.codigo = codigo; }
    public void setPercentual(double percentual) { this.percentual = percentual; }
    public void setDataValidade(LocalDate dataValidade) { this.dataValidade = dataValidade; }
    public void setUtilizado(boolean utilizado) { this.utilizado = utilizado; }

    public boolean isValido() {
        return !utilizado && (dataValidade != null) && !LocalDate.now().isAfter(dataValidade);
    }

    public void marcarComoUsado() { this.utilizado = true; }

    public void expirar() { this.dataValidade = LocalDate.now().minusDays(1); }

    @Override
    public String toString() {
        return String.format("Cupom[id=%s, codigo=%s, percentual=%.2f%%, validade=%s, usado=%s]",
                id, codigo, percentual, dataValidade, utilizado);
    }
}
