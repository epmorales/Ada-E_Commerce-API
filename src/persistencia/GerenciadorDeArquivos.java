package persistencia;

import model.Pedido;
import model.ItemPedido;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.*;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

public class GerenciadorDeArquivos { // NOVO

    private static final DateTimeFormatter DTF = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public void exportarPedidosCsv(List<Pedido> pedidos, String caminhoArquivo) throws IOException {
        Path path = Paths.get(caminhoArquivo);
        if (path.getParent() != null) {
            Files.createDirectories(path.getParent());
        }

        try (BufferedWriter writer = Files.newBufferedWriter(path, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING)) {
            writer.write("pedidoId;cliente;status;total;dataCriacao;dataPagamento;dataEntrega;itens");
            writer.newLine();

            for (Pedido p : pedidos) {
                String itens = p.getItens()
                        .stream()
                        .map(i -> i.getProduto().getNome() + "x" + i.getQuantidade() + "@R$" + i.getTotal())
                        .collect(Collectors.joining("|"));

                String linha = String.join(";",
                        p.getId().toString(),
                        p.getCliente().getNome(),
                        p.getStatus().name(),
                        String.format("%.2f", p.calcularTotal()),
                        p.getDataCriacao().format(DTF),
                        p.getDataPagamento() != null ? p.getDataPagamento().format(DTF) : "",
                        p.getDataEntrega() != null ? p.getDataEntrega().format(DTF) : "",
                        itens.replace(";", ",")
                );

                writer.write(linha);
                writer.newLine();
            }
        }
    }
}
