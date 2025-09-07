package repositorio;

import java.util.List;
import java.util.Optional;

public interface Repositorio<T extends Identificavel> {
    void salvar(T entidade);
    List<T> listar();
    Optional<T> buscarPorId(java.util.UUID id);
    void atualizar(T entidade);
}
