package repositorio;

import java.util.*;

public class RepositorioEmMemoria<T extends Identificavel> implements Repositorio<T> {
    private final Map<UUID, T> banco = new HashMap<>();

    @Override
    public void salvar(T entidade) { banco.put(entidade.getId(), entidade); }

    @Override
    public List<T> listar() { return new ArrayList<>(banco.values()); }

    @Override
    public Optional<T> buscarPorId(UUID id) { return Optional.ofNullable(banco.get(id)); }

    @Override
    public void atualizar(T entidade) { banco.put(entidade.getId(), entidade); }
}
