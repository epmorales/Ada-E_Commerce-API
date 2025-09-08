package model;

public class ItemPedido {
    private Produto produto;
    private int quantidade;
    private double precoVenda;

    public ItemPedido(Produto produto, int quantidade, double precoVenda) {
        this.produto = produto;
        this.quantidade = quantidade;
        this.precoVenda = precoVenda;
    }

    public double getTotal() { return quantidade * precoVenda; }

    public Produto getProduto() { return produto; }

    public int getQuantidade() { return quantidade; }

    public void setQuantidade(int quantidade) { this.quantidade = quantidade; }
}