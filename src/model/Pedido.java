package model;

import desconto.CupomDesconto;
import repositorio.Identificavel;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class Pedido implements Identificavel {

    private final UUID id;
    private final Cliente cliente;
    private final LocalDateTime dataCriacao;

    private LocalDateTime dataPagamento;
    private LocalDateTime dataEntrega;
    private LocalDateTime dataCancelamento;
    private StatusPedido status;
    private final List<ItemPedido> itens = new ArrayList<>();
    private CupomDesconto cupomDesconto;

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    public Pedido(Cliente cliente) {
        this.id = UUID.randomUUID();
        this.cliente = cliente;
        this.dataCriacao = LocalDateTime.now();
        this.status = StatusPedido.ABERTO;
    }

<<<<<<< HEAD
=======
    @Override
    public java.util.UUID getId() {
        return id;
    }

>>>>>>> d38994804010dd055a15d6794fd9ac5631adecce
    public void adicionarItem(Produto produto, int quantidade, double precoVenda) {
        if (status != StatusPedido.ABERTO) {
            throw new IllegalStateException("Não é possível adicionar itens, pedido não está aberto. Status atual: " + status);
        }
        if (quantidade <= 0) {
            throw new IllegalArgumentException("A quantidade deve ser maior que zero.");
        }

        Optional<ItemPedido> itemExistente = itens.stream()
                .filter(item -> item.getProduto().equals(produto))
                .findFirst();

        if (itemExistente.isPresent()) {
            itemExistente.get().setQuantidade(itemExistente.get().getQuantidade() + quantidade);
        } else {
            this.itens.add(new ItemPedido(produto, quantidade, precoVenda));
        }
    }

    public void alterarQuantidade(Produto produto, int novaQuantidade) {
        if (status != StatusPedido.ABERTO) {
            throw new IllegalStateException("Não é possível alterar itens, pedido não está aberto. Status atual: " + status);
        }
        if (novaQuantidade <= 0) {
            removerItem(produto);
            return;
        }

        itens.stream()
                .filter(i -> i.getProduto().equals(produto))
                .findFirst()
                .ifPresentOrElse(
                        i -> i.setQuantidade(novaQuantidade),
                        () -> { throw new IllegalArgumentException("Produto não encontrado no pedido."); }
                );
    }

<<<<<<< HEAD
    public void removerItem(Produto produto) {
        if (status != StatusPedido.ABERTO) {
            throw new IllegalStateException("Não é possível remover itens, pedido não está aberto. Status atual: " + status);
        }
        boolean removido = itens.removeIf(i -> i.getProduto().equals(produto));
        if (!removido) {
            throw new IllegalArgumentException("Produto não encontrado no pedido.");
        }
=======
    public double calcularTotal() {
        return itens.stream().mapToDouble(ItemPedido::getTotal).sum();
>>>>>>> d38994804010dd055a15d6794fd9ac5631adecce
    }

    public void finalizarPedido() {
        if (this.status != StatusPedido.ABERTO) {
            throw new IllegalStateException("O pedido não pode ser finalizado. Status atual: " + this.status);
        }

        if (itens.isEmpty() || calcularTotal() <= 0) {
            throw new IllegalStateException("Pedido não pode ser finalizado sem itens ou valor inválido.");
        }

        this.status = StatusPedido.AGUARDANDO_PAGAMENTO;
        cliente.getNotificacao().enviarMensagem("Seu pedido foi finalizado e está aguardando pagamento.");
    }

    public void pagarPedido() {
        if (status != StatusPedido.AGUARDANDO_PAGAMENTO) {
            throw new IllegalStateException("O pedido não pode ser pago neste status. Status atual: " + status);
        }

        this.status = StatusPedido.PAGO;
        this.dataPagamento = LocalDateTime.now();

        if (this.cupomDesconto != null && this.cupomDesconto.isValido()) {
            this.cupomDesconto.marcarComoUsado();
        }

        cliente.getNotificacao().enviarMensagem("Pagamento recebido com sucesso!");
    }

    public void entregarPedido() {
        if (status != StatusPedido.PAGO) {
            throw new IllegalStateException("O pedido só pode ser entregue após o pagamento. Status atual: " + status);
        }

        this.status = StatusPedido.FINALIZADO;
        this.dataEntrega = LocalDateTime.now();
        cliente.getNotificacao().enviarMensagem("Seu pedido foi entregue. Obrigado pela compra!");
    }

<<<<<<< HEAD
    public void cancelarPedido() {
        if (status == StatusPedido.PAGO || status == StatusPedido.FINALIZADO) {
            throw new IllegalStateException("Não é possível cancelar um pedido neste status. Status atual: " + status);
        }

        this.status = StatusPedido.CANCELADO;
        this.dataCancelamento = LocalDateTime.now();
        cliente.getNotificacao().enviarMensagem("Seu pedido foi cancelado.");
=======
    // --- Novo método de cancelamento ---
    public void cancelarPedido() {
        if (status == StatusPedido.FINALIZADO) {
            throw new IllegalStateException("Não é possível cancelar um pedido já entregue.");
        }
        if (status == StatusPedido.CANCELADO) {
            throw new IllegalStateException("O pedido já foi cancelado anteriormente.");
        }
        this.status = StatusPedido.CANCELADO;
        cliente.getNotificacao().enviarMensagem("Seu pedido foi cancelado.");
    }

    // Getters
    public StatusPedido getStatus() {
        return status;
>>>>>>> d38994804010dd055a15d6794fd9ac5631adecce
    }

    public void aplicarCupom(CupomDesconto cupom) {
        if (this.status != StatusPedido.ABERTO && this.status != StatusPedido.AGUARDANDO_PAGAMENTO) {
            throw new IllegalStateException("Cupom só pode ser aplicado em pedidos ABERTOS ou AGUARDANDO PAGAMENTO. Status atual: " + this.status);
        }

        if (cupom.isValido()) {
            this.cupomDesconto = cupom;
            cliente.getNotificacao().enviarMensagem("Cupom aplicado com sucesso: " + cupom.getCodigo());
        } else {
            throw new IllegalStateException("Cupom inválido ou expirado.");
        }
    }

    public double calcularTotal() {
        double total = itens.stream().mapToDouble(ItemPedido::getTotal).sum();
        if (cupomDesconto != null && cupomDesconto.isValido()) {
            total = total - (total * cupomDesconto.getPercentual() / 100);
        }
        return total;
    }

    @Override
    public UUID getId() { return id; }
    public Cliente getCliente() { return cliente; }
    public StatusPedido getStatus() { return status; }
    public List<ItemPedido> getItens() { return itens; }
    public LocalDateTime getDataCriacao() { return dataCriacao; }
    public LocalDateTime getDataPagamento() { return dataPagamento; }
    public LocalDateTime getDataEntrega() { return dataEntrega; }
    public LocalDateTime getDataCancelamento() { return dataCancelamento; }
    public CupomDesconto getCupomDesconto() { return cupomDesconto; }

    public String formatarData(LocalDateTime data) {
        return data != null ? data.format(FORMATTER) : "-";
    }
}