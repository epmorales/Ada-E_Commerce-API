package model;

import repositorio.Identificavel;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Pedido implements Identificavel {
    private final java.util.UUID id;
    private final Cliente cliente;
    private final LocalDateTime dataCriacao;
    private StatusPedido status;
    private final List<ItemPedido> itens = new ArrayList<>();

    public Pedido(Cliente cliente) {
        this.id = java.util.UUID.randomUUID();
        this.cliente = cliente;
        this.dataCriacao = LocalDateTime.now();
        this.status = StatusPedido.ABERTO;
    }

    @Override
    public java.util.UUID getId() {
        return id;
    }

    public void adicionarItem(Produto produto, int quantidade, double precoVenda) {
        if (status != StatusPedido.ABERTO)
            throw new IllegalStateException("Não é possível adicionar itens, pedido não está aberto.");
        itens.add(new ItemPedido(produto, quantidade, precoVenda));
    }

    public void alterarQuantidade(Produto produto, int novaQuantidade) {
        if (status != StatusPedido.ABERTO)
            throw new IllegalStateException("Não é possível alterar itens, pedido não está aberto.");
        for (ItemPedido item : itens) {
            if (item.getProduto().equals(produto)) {
                item.setQuantidade(novaQuantidade);
                return;
            }
        }
    }

    public void removerItem(Produto produto) {
        if (status != StatusPedido.ABERTO)
            throw new IllegalStateException("Não é possível remover itens, pedido não está aberto.");
        itens.removeIf(i -> i.getProduto().equals(produto));
    }

    public double calcularTotal() {
        return itens.stream().mapToDouble(ItemPedido::getTotal).sum();
    }

    public void finalizarPedido() {
        if (itens.isEmpty() || calcularTotal() <= 0)
            throw new IllegalStateException("Pedido não pode ser finalizado sem itens ou valor inválido.");
        this.status = StatusPedido.AGUARDANDO_PAGAMENTO;
        cliente.getNotificacao().enviarMensagem("Seu pedido foi finalizado e está aguardando pagamento.");
    }

    public void pagarPedido() {
        if (status != StatusPedido.AGUARDANDO_PAGAMENTO)
            throw new IllegalStateException("O pedido não pode ser pago neste status.");
        this.status = StatusPedido.PAGO;
        cliente.getNotificacao().enviarMensagem("Pagamento recebido com sucesso!");
    }

    public void entregarPedido() {
        if (status != StatusPedido.PAGO)
            throw new IllegalStateException("O pedido só pode ser entregue após o pagamento.");
        this.status = StatusPedido.FINALIZADO;
        cliente.getNotificacao().enviarMensagem("Seu pedido foi entregue. Obrigado pela compra!");
    }

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
    }

    public Cliente getCliente() {
        return cliente;
    }

    public List<ItemPedido> getItens() {
        return itens;
    }
}
