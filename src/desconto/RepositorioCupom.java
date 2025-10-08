package desconto;

import repositorio.RepositorioEmMemoria;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

public class RepositorioCupom {
    private final RepositorioEmMemoria<CupomDesconto> repo = new RepositorioEmMemoria<>();

    public void salvar(CupomDesconto entidade) {
        repo.salvar(entidade);
    }

    public List<CupomDesconto> listar() {
        return repo.listar();
    }

    public Optional<CupomDesconto> buscarPorId(UUID id) {
        return repo.buscarPorId(id);
    }

    public void atualizar(CupomDesconto entidade) {
        repo.atualizar(entidade);
    }

    public Optional<CupomDesconto> buscarPorCodigo(String codigo) {
        return repo.listar()
                .stream()
                .filter(c -> c.getCodigo().equalsIgnoreCase(codigo))
                .findFirst();
    }

    public List<CupomDesconto> listarValidos() {
        return repo.listar()
                .stream()
                .filter(CupomDesconto::isValido)
                .collect(Collectors.toList());
    }

    public boolean expirarPorCodigo(String codigo) {
        Optional<CupomDesconto> op = buscarPorCodigo(codigo);
        if (op.isPresent()) {
            CupomDesconto c = op.get();
            c.expirar();
            repo.atualizar(c);
            return true;
        }
        return false;
    }

    public boolean atualizarPorCodigo(String codigo, String novoCodigo, Double novoPercentual, LocalDate novaValidade, Boolean marcadoComoUtilizado) {
        Optional<CupomDesconto> op = buscarPorCodigo(codigo);
        if (op.isPresent()) {
            CupomDesconto c = op.get();
            if (novoCodigo != null && !novoCodigo.isBlank()) c.setCodigo(novoCodigo);
            if (novoPercentual != null) c.setPercentual(novoPercentual);
            if (novaValidade != null) c.setDataValidade(novaValidade);
            if (marcadoComoUtilizado != null) c.setUtilizado(marcadoComoUtilizado);
            repo.atualizar(c);
            return true;
        }
        return false;
    }
}
