package notificacao;

import java.util.concurrent.CompletableFuture;

public class EmailNotificacao implements Notificacao {
    private final String email;

    public EmailNotificacao(String email) {
        this.email = email;
    }

    @Override
    public void enviarMensagem(String mensagem) {
        CompletableFuture.runAsync(() -> {
            System.out.println(" 📧  [Notificação] Enviando e-mail para "
                    + email + ": " + mensagem);
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
    }
}
