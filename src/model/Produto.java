package model;

import repositorio.Identificavel;
import java.util.UUID;

public class Produto implements Identificavel {
    private final UUID id;
    private String nome;
    private double precoBase;

    public Produto(String nome, double precoBase) {
        this.id = UUID.randomUUID();
        this.nome = nome;
        this.precoBase = precoBase;
    }

    @Override
    public UUID getId() { return id; }

    public String getNome() { return nome; }

    public void setNome(String nome) { this.nome = nome; }

    public double getPrecoBase() { return precoBase; }

    public void setPrecoBase(double precoBase) { this.precoBase = precoBase; }
}