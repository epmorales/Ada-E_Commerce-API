package notificacao;

public class EmailNotificacao implements Notificacao {
    private final String email;

    public EmailNotificacao(String email) { this.email = email; }

    @Override
    public void enviarMensagem(String mensagem) {
        System.out.println("📧 Enviando e-mail para " + email + ": " + mensagem);
    }
}
