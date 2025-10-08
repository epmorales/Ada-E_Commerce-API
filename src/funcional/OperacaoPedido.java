package funcional;

import model.Pedido;

@FunctionalInterface
public interface OperacaoPedido {
    void executar(Pedido pedido);
}
