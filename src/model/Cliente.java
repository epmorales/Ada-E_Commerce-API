package model;

import notificacao.Notificacao;
import notificacao.EmailNotificacao;
import repositorio.Identificavel;
import java.util.UUID;

public class Cliente implements Identificavel {
    private final UUID id;
    private String nome;
    private String documento;
    private Notificacao notificacao;

    public Cliente(String nome, String documento, String email) {
        this.id = UUID.randomUUID();
        this.nome = nome;
        this.documento = documento;
        this.notificacao = new EmailNotificacao(email);
    }

    @Override
    public UUID getId() { return id; }

    public String getNome() { return nome; }

    public void setNome(String nome) { this.nome = nome; }

    public String getDocumento() { return documento; }

    public void setDocumento(String documento) { this.documento = documento; }

    public Notificacao getNotificacao() { return notificacao; }
}
