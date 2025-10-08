package repositorio;

import model.Cliente;
import java.util.*;

public class RepositorioEmMemoria<T extends Identificavel> implements Repositorio<T> {
    private final Map<UUID, T> banco = new HashMap<>();

    @Override
    public void salvar(T entidade) {
        if (entidade instanceof Cliente) {
            Cliente novoCliente = (Cliente) entidade;

            boolean documentoJaExiste = banco.values().stream()
                    .filter(e -> e instanceof Cliente)
                    .map(e -> (Cliente) e)
                    .anyMatch(c -> c.getDocumento().equals(novoCliente.getDocumento())
                            && !c.getId().equals(novoCliente.getId()));

            if (documentoJaExiste) {
                throw new IllegalArgumentException("Erro ao cadastrar: Já existe um cliente com o documento " + novoCliente.getDocumento() + ".");
            }
        }

        banco.put(entidade.getId(), entidade);
    }

    @Override
    public List<T> listar() {
        return new ArrayList<>(banco.values());
    }

    @Override
    public Optional<T> buscarPorId(UUID id) {
        return Optional.ofNullable(banco.get(id));
    }

    @Override
    public void atualizar(T entidade) {
        banco.put(entidade.getId(), entidade);
    }
}