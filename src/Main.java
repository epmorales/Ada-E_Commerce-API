package application;

import model.Cliente;
import model.Produto;
import model.Pedido;
import model.StatusPedido;
import model.ItemPedido;
import desconto.CupomDesconto;
import desconto.RepositorioCupom;
import notificacao.EmailNotificacao;
import notificacao.Notificacao;
import persistencia.GerenciadorDeArquivos;
import repositorio.Repositorio;
import repositorio.RepositorioEmMemoria;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final Repositorio<Cliente> repoClientes = new RepositorioEmMemoria<>();
    private static final Repositorio<Produto> repoProdutos = new RepositorioEmMemoria<>();
    private static final Repositorio<Pedido> repoPedidos = new RepositorioEmMemoria<>();
    private static final RepositorioCupom repoCupons = new RepositorioCupom();
    private static final GerenciadorDeArquivos gerenciador = new GerenciadorDeArquivos();

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_WHITE = "\u001B[37m";
    private static final DateTimeFormatter DATA_PATTERN = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static void main(String[] args) {
        boolean rodando = true;
        while (rodando) {
            exibirMenu();
            System.out.print("Escolha uma opção: ");
            String linha = scanner.nextLine();
            if (linha.isBlank()) continue;
            int opcao;
            try {
                opcao = Integer.parseInt(linha.trim());
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Digite um número.");
                continue;
            }
            try {
                switch (opcao) {
                    case 1 -> cadastrarCliente();
                    case 2 -> listarClientes();
                    case 3 -> atualizarCliente();
                    case 4 -> cadastrarProduto();
                    case 5 -> listarProdutos();
                    case 6 -> atualizarProduto();
                    case 7 -> criarPedido();
                    case 8 -> adicionarItemPedido();
                    case 9 -> alterarQuantidadeItem();
                    case 10 -> removerItemPedido();
                    case 11 -> finalizarPedido();
                    case 12 -> pagarPedido();
                    case 13 -> entregarPedido();
                    case 14 -> listarPedidos();
                    case 15 -> cancelarPedido();
                    case 16 -> criarCupom();
                    case 17 -> listarCupons();
                    case 18 -> aplicarCupomEmPedido();
                    case 19 -> atualizarCupom();
                    case 20 -> expirarCupom();
                    case 0 -> {
                        rodando = false;
                        System.out.println(ANSI_GREEN + "Saindo... Até a próxima!" + ANSI_RESET);
                    }
                    default -> System.out.println("Opção inválida!");
                }
            } catch (Exception e) {
                System.out.println("Erro: " + e.getMessage());
            }
        }
    }

    private static void exibirMenu() {
        int largura = 42;
        System.out.println(ANSI_GREEN + " " + "-".repeat(largura) + ANSI_RESET);
        System.out.println(ANSI_GREEN + "|" + centralizarTexto("Ada E-Commerce (Módulo 2)", largura) + "|" + ANSI_RESET);
        System.out.println(ANSI_GREEN + "|" + " ".repeat(largura) + "|" + ANSI_RESET);
        System.out.println(ANSI_GREEN + "|" + centralizarTexto("CLIENTES", largura) + "|" + ANSI_RESET);
        System.out.println(ANSI_GREEN + "|" + ANSI_WHITE + formatarItemMenu("1 - Cadastrar cliente", largura) + ANSI_GREEN + "|" + ANSI_RESET);
        System.out.println(ANSI_GREEN + "|" + ANSI_WHITE + formatarItemMenu("2 - Listar clientes", largura) + ANSI_GREEN + "|" + ANSI_RESET);
        System.out.println(ANSI_GREEN + "|" + ANSI_WHITE + formatarItemMenu("3 - Atualizar cliente", largura) + ANSI_GREEN + "|" + ANSI_RESET);
        System.out.println(ANSI_GREEN + "|" + " ".repeat(largura) + "|" + ANSI_RESET);
        System.out.println(ANSI_GREEN + "|" + centralizarTexto("PRODUTOS", largura) + "|" + ANSI_RESET);
        System.out.println(ANSI_GREEN + "|" + ANSI_WHITE + formatarItemMenu("4 - Cadastrar produto", largura) + ANSI_GREEN + "|" + ANSI_RESET);
        System.out.println(ANSI_GREEN + "|" + ANSI_WHITE + formatarItemMenu("5 - Listar produtos", largura) + ANSI_GREEN + "|" + ANSI_RESET);
        System.out.println(ANSI_GREEN + "|" + ANSI_WHITE + formatarItemMenu("6 - Atualizar produto", largura) + ANSI_GREEN + "|" + ANSI_RESET);
        System.out.println(ANSI_GREEN + "|" + " ".repeat(largura) + "|" + ANSI_RESET);
        System.out.println(ANSI_GREEN + "|" + centralizarTexto("PEDIDOS", largura) + "|" + ANSI_RESET);
        System.out.println(ANSI_GREEN + "|" + ANSI_WHITE + formatarItemMenu("7 - Criar pedido", largura) + ANSI_GREEN + "|" + ANSI_RESET);
        System.out.println(ANSI_GREEN + "|" + ANSI_WHITE + formatarItemMenu("8 - Adicionar item ao pedido", largura) + ANSI_GREEN + "|" + ANSI_RESET);
        System.out.println(ANSI_GREEN + "|" + ANSI_WHITE + formatarItemMenu("9 - Alterar quantidade de item", largura) + ANSI_GREEN + "|" + ANSI_RESET);
        System.out.println(ANSI_GREEN + "|" + ANSI_WHITE + formatarItemMenu("10 - Remover item do pedido", largura) + ANSI_GREEN + "|" + ANSI_RESET);
        System.out.println(ANSI_GREEN + "|" + ANSI_WHITE + formatarItemMenu("11 - Finalizar pedido", largura) + ANSI_GREEN + "|" + ANSI_RESET);
        System.out.println(ANSI_GREEN + "|" + ANSI_WHITE + formatarItemMenu("12 - Pagar pedido", largura) + ANSI_GREEN + "|" + ANSI_RESET);
        System.out.println(ANSI_GREEN + "|" + ANSI_WHITE + formatarItemMenu("13 - Entregar pedido", largura) + ANSI_GREEN + "|" + ANSI_RESET);
        System.out.println(ANSI_GREEN + "|" + ANSI_WHITE + formatarItemMenu("14 - Listar pedidos", largura) + ANSI_GREEN + "|" + ANSI_RESET);
        System.out.println(ANSI_GREEN + "|" + ANSI_WHITE + formatarItemMenu("15 - Cancelar pedido", largura) + ANSI_GREEN + "|" + ANSI_RESET);
        System.out.println(ANSI_GREEN + "|" + " ".repeat(largura) + "|" + ANSI_RESET);
        System.out.println(ANSI_GREEN + "|" + centralizarTexto("CUPONS", largura) + "|" + ANSI_RESET);
        System.out.println(ANSI_GREEN + "|" + ANSI_WHITE + formatarItemMenu("16 - Criar cupom de desconto", largura) + ANSI_GREEN + "|" + ANSI_RESET);
        System.out.println(ANSI_GREEN + "|" + ANSI_WHITE + formatarItemMenu("17 - Listar cupons", largura) + ANSI_GREEN + "|" + ANSI_RESET);
        System.out.println(ANSI_GREEN + "|" + ANSI_WHITE + formatarItemMenu("18 - Aplicar cupom ao pedido", largura) + ANSI_GREEN + "|" + ANSI_RESET);
        System.out.println(ANSI_GREEN + "|" + ANSI_WHITE + formatarItemMenu("19 - Atualizar cupom", largura) + ANSI_GREEN + "|" + ANSI_RESET);
        System.out.println(ANSI_GREEN + "|" + ANSI_WHITE + formatarItemMenu("20 - Expirar cupom", largura) + ANSI_GREEN + "|" + ANSI_RESET);
        System.out.println(ANSI_GREEN + "|" + " ".repeat(largura) + "|" + ANSI_RESET);
        System.out.println(ANSI_GREEN + "|" + ANSI_WHITE + formatarItemMenu("0 - Sair", largura) + ANSI_GREEN + "|" + ANSI_RESET);
        System.out.println(ANSI_GREEN + " " + "-".repeat(largura) + ANSI_RESET);
    }

    private static String formatarItemMenu(String texto, int largura) {
        String recuo = "  ";
        texto = recuo + texto;
        if (texto.length() > largura) {
            return texto.substring(0, largura);
        }
        return texto + " ".repeat(largura - texto.length());
    }

    private static String centralizarTexto(String texto, int largura) {
        if (texto.length() >= largura) return texto.substring(0, largura);
        int espacos = largura - texto.length();
        int esquerda = espacos / 2;
        int direita = espacos - esquerda;
        return " ".repeat(esquerda) + texto + " ".repeat(direita);
    }

    private static void cadastrarCliente() {
        System.out.print("Nome: ");
        String nome = scanner.nextLine().trim();
        System.out.print("Documento: ");
        String documento = scanner.nextLine().trim();
        System.out.print("E-mail: ");
        String email = scanner.nextLine().trim();
        Cliente c = new Cliente(nome, documento, email);
        repoClientes.salvar(c);

        System.out.println(ANSI_GREEN + "Cliente cadastrado com sucesso! ID: " + c.getId() + ANSI_RESET);
    }

    private static void listarClientes() {
        System.out.println("=== Clientes (ID | Nome | Documento) ===");
        if (repoClientes.listar().isEmpty()) {
            System.out.println("Nenhum cliente cadastrado.");
            return;
        }

        repoClientes.listar().stream()
                .sorted(Comparator.comparing(Cliente::getNome))
                .forEach(c -> System.out.println(c.getId() + " | " + c.getNome() + " | " + c.getDocumento()));
    }

    private static void atualizarCliente() {
        listarClientes();
        System.out.print("ID do cliente para atualizar: ");
        UUID id = UUID.fromString(scanner.nextLine().trim());
        repoClientes.buscarPorId(id).ifPresentOrElse(cliente -> {
            System.out.print("Novo nome (enter para manter): ");
            String nome = scanner.nextLine();
            if (!nome.isBlank()) cliente.setNome(nome);
            System.out.print("Novo documento (enter para manter): ");
            String doc = scanner.nextLine();
            if (!doc.isBlank()) cliente.setDocumento(doc);
            repoClientes.atualizar(cliente);
            System.out.println(ANSI_GREEN + "Cliente atualizado com sucesso!" + ANSI_RESET);
        }, () -> System.out.println("Cliente não encontrado."));
    }

    private static void cadastrarProduto() {
        System.out.print("Nome do produto: ");
        String nome = scanner.nextLine().trim();
        System.out.print("Preço (ex: 199.90): ");
        double preco = Double.parseDouble(scanner.nextLine().trim());
        Produto p = new Produto(nome, preco);
        repoProdutos.salvar(p);

        System.out.println(ANSI_GREEN + "Produto cadastrado com sucesso! ID: " + p.getId() + ANSI_RESET);
    }

    private static void listarProdutos() {
        System.out.println("=== Produtos (ID | Nome | Preço) ===");
        if (repoProdutos.listar().isEmpty()) {
            System.out.println("Nenhum produto cadastrado.");
            return;
        }
        repoProdutos.listar().forEach(p -> System.out.println(p.getId() + " | " + p.getNome() + " | R$ " + String.format("%.2f", p.getPrecoBase())));
    }

    private static void atualizarProduto() {
        listarProdutos();
        System.out.print("ID do produto para atualizar: ");
        UUID id = UUID.fromString(scanner.nextLine().trim());
        repoProdutos.buscarPorId(id).ifPresentOrElse(produto -> {
            System.out.print("Novo nome (enter para manter): ");
            String nome = scanner.nextLine();
            if (!nome.isBlank()) produto.setNome(nome);
            System.out.print("Novo preço (enter para manter): ");
            String precoLinha = scanner.nextLine();
            if (!precoLinha.isBlank()) produto.setPrecoBase(Double.parseDouble(precoLinha));
            repoProdutos.atualizar(produto);
            System.out.println(ANSI_GREEN + "Produto atualizado com sucesso!" + ANSI_RESET);
        }, () -> System.out.println("Produto não encontrado."));
    }

    private static void criarPedido() {
        listarClientes();
        System.out.print("ID do cliente: ");
        UUID clienteId = UUID.fromString(scanner.nextLine().trim());
        Cliente cliente = repoClientes.buscarPorId(clienteId).orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado."));
        Pedido pedido = new Pedido(cliente);
        repoPedidos.salvar(pedido);
        System.out.println(ANSI_GREEN + "Pedido criado com sucesso! ID: " + pedido.getId() + ANSI_RESET);
    }

    private static Pedido selecionarPedido() {
        listarPedidos();
        System.out.print("Digite o ID (completo) ou os primeiros caracteres do ID do pedido: ");
        String linha = scanner.nextLine().trim();

        if (linha.isBlank()) {
            throw new IllegalArgumentException("O ID do pedido não pode ser vazio.");
        }

        Optional<Pedido> pedidoEncontrado = repoPedidos.listar().stream()
                .filter(p -> p.getId().toString().startsWith(linha))
                .findFirst();

        return pedidoEncontrado.orElseThrow(() -> new IllegalArgumentException("Pedido não encontrado ou ID incompleto/inválido."));
    }

    private static Produto selecionarProduto() {
        listarProdutos();
        System.out.print("ID do produto: ");
        UUID id = UUID.fromString(scanner.nextLine().trim());
        return repoProdutos.buscarPorId(id).orElseThrow(() -> new IllegalArgumentException("Produto não encontrado."));
    }

    private static void adicionarItemPedido() {
        Pedido pedido = selecionarPedido();
        if (pedido.getStatus() != StatusPedido.ABERTO) {
            System.out.println("Só é possível adicionar itens em pedidos ABERTOS.");
            return;
        }
        Produto produto = selecionarProduto();
        System.out.print("Quantidade: ");
        int qtd = Integer.parseInt(scanner.nextLine().trim());
        System.out.print("Preço de venda: ");
        double preco = Double.parseDouble(scanner.nextLine().trim());
        pedido.adicionarItem(produto, qtd, preco);
        repoPedidos.atualizar(pedido);
        System.out.println(ANSI_GREEN + "Item adicionado ao pedido!" + ANSI_RESET);
    }

    private static void alterarQuantidadeItem() {
        Pedido pedido = selecionarPedido();
        if (pedido.getStatus() != StatusPedido.ABERTO) {
            System.out.println("Só é possível alterar itens em pedidos ABERTOS.");
            return;
        }
        Produto produto = selecionarProduto();
        System.out.print("Nova quantidade: ");
        int qtd = Integer.parseInt(scanner.nextLine().trim());
        pedido.alterarQuantidade(produto, qtd);
        repoPedidos.atualizar(pedido);
        System.out.println(ANSI_GREEN + "Quantidade alterada!" + ANSI_RESET);
    }

    private static void removerItemPedido() {
        Pedido pedido = selecionarPedido();
        if (pedido.getStatus() != StatusPedido.ABERTO) {
            System.out.println("Só é possível remover itens em pedidos ABERTOS.");
            return;
        }
        Produto produto = selecionarProduto();
        pedido.removerItem(produto);
        repoPedidos.atualizar(pedido);
        System.out.println(ANSI_GREEN + "Item removido do pedido!" + ANSI_RESET);
    }

    private static void finalizarPedido() {
        Pedido pedido = selecionarPedido();
        try {
            pedido.finalizarPedido();
            repoPedidos.atualizar(pedido);
            System.out.println(ANSI_GREEN + "Pedido finalizado! Status: AGUARDANDO PAGAMENTO." + ANSI_RESET);
        } catch (IllegalStateException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void pagarPedido() {
        Pedido pedido = selecionarPedido();
        try {
            pedido.pagarPedido();
            repoPedidos.atualizar(pedido);
            System.out.println(ANSI_GREEN + "Pedido pago com sucesso! Status: PAGO." + ANSI_RESET);
        } catch (IllegalStateException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void entregarPedido() {
        Pedido pedido = selecionarPedido();
        try {
            pedido.entregarPedido();
            repoPedidos.atualizar(pedido);
            System.out.println(ANSI_GREEN + "Pedido entregue com sucesso! Status: FINALIZADO." + ANSI_RESET);
        } catch (IllegalStateException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void cancelarPedido() {
        Pedido pedido = selecionarPedido();
        try {
            pedido.cancelarPedido();
            repoPedidos.atualizar(pedido);
            System.out.println(ANSI_GREEN + "Pedido cancelado com sucesso!" + ANSI_RESET);
        } catch (IllegalStateException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void listarPedidos() {
        System.out.println("=== Pedidos (ID Curto | Cliente | Status | Total | Criação) ===");
        if (repoPedidos.listar().isEmpty()) {
            System.out.println("Nenhum pedido encontrado.");
            return;
        }

        repoPedidos.listar().stream()
                .sorted(Comparator.comparing(Pedido::getDataCriacao).reversed())
                .forEach(p -> {
                    String idCurto = p.getId().toString().substring(0, 8);
                    System.out.println(idCurto
                            + " | Cliente: " + p.getCliente().getNome()
                            + " | Status: " + p.getStatus()
                            + " | Total: R$ " + String.format("%.2f", p.calcularTotal())
                            + " | Criado: " + p.formatarData(p.getDataCriacao()));
                });
    }

    private static void criarCupom() {
        System.out.print("Código do cupom: ");
        String codigo = scanner.nextLine().trim();
        System.out.print("Percentual desconto (ex: 10 para 10%): ");
        double percentual = Double.parseDouble(scanner.nextLine().trim());
        System.out.print("Data de validade (YYYY-MM-DD): ");
        String dataTxt = scanner.nextLine().trim();
        LocalDate validade;
        try {
            validade = LocalDate.parse(dataTxt, DATA_PATTERN);
        } catch (Exception ex) {
            System.out.println("Data inválida. Use o formato YYYY-MM-DD.");
            return;
        }
        CupomDesconto cupom = new CupomDesconto(codigo, percentual, validade);
        repoCupons.salvar(cupom);

        String mensagem = String.format(
                "Cupom criado! Código: %s | Desconto: %.2f%% | Validade: %s",
                cupom.getCodigo(), cupom.getPercentual(), cupom.getDataValidade().format(DATA_PATTERN)
        );
        System.out.println(ANSI_GREEN + mensagem + ANSI_RESET);
    }

    private static void listarCupons() {
        System.out.println("=== Cupons (Código | Desconto | Validade | Usado) ===");
        if (repoCupons.listar().isEmpty()) {
            System.out.println("Nenhum cupom cadastrado.");
            return;
        }
        repoCupons.listar().forEach(c -> {
            String utilizadoStatus = c.isUtilizado() ? "Sim" : "Não";
            String linha = String.format(
                    "%s | %.2f%% | %s | %s",
                    c.getCodigo(),
                    c.getPercentual(),
                    c.getDataValidade().format(DATA_PATTERN),
                    utilizadoStatus
            );
            System.out.println(linha);
        });
    }

    private static void aplicarCupomEmPedido() {
        Pedido pedido = selecionarPedido();
        System.out.print("Código do cupom: ");
        String codigo = scanner.nextLine().trim();

        repoCupons.buscarPorCodigo(codigo).ifPresentOrElse(cupom -> {
            try {
                pedido.aplicarCupom(cupom);
                repoPedidos.atualizar(pedido);
                repoCupons.atualizar(cupom);
                System.out.println(ANSI_GREEN + "Cupom aplicado com sucesso!" + ANSI_RESET);
            } catch (IllegalStateException e) {
                System.out.println(e.getMessage());
            }
        }, () -> System.out.println("Cupom não encontrado."));
    }

    private static void atualizarCupom() {
        listarCupons();
        System.out.print("Código do cupom para atualizar: ");
        String codigo = scanner.nextLine().trim();

        System.out.print("Novo código (enter para manter): ");
        String novoCodigo = scanner.nextLine();
        if (novoCodigo.isBlank()) novoCodigo = null;

        System.out.print("Novo percentual (enter para manter): ");
        Double novoPercentual = null;
        String percentualLinha = scanner.nextLine();
        if (!percentualLinha.isBlank()) {
            try {
                novoPercentual = Double.parseDouble(percentualLinha);
            } catch (NumberFormatException e) {
                System.out.println("Percentual inválido. Operação cancelada.");
                return;
            }
        }

        System.out.print("Nova data de validade (YYYY-MM-DD, enter para manter): ");
        LocalDate novaValidade = null;
        String validadeLinha = scanner.nextLine();
        if (!validadeLinha.isBlank()) {
            try {
                novaValidade = LocalDate.parse(validadeLinha, DATA_PATTERN);
            } catch (Exception ex) {
                System.out.println("Data de validade inválida. Operação cancelada.");
                return;
            }
        }

        System.out.print("Marcar como utilizado? (S/N, enter para manter): ");
        Boolean marcadoComoUtilizado = null;
        String utilizadoLinha = scanner.nextLine().toUpperCase();
        if (utilizadoLinha.equals("S")) {
            marcadoComoUtilizado = true;
        } else if (utilizadoLinha.equals("N")) {
            marcadoComoUtilizado = false;
        }

        boolean sucesso = repoCupons.atualizarPorCodigo(codigo, novoCodigo, novoPercentual, novaValidade, marcadoComoUtilizado);
        if (sucesso) {
            System.out.println(ANSI_GREEN + "Cupom atualizado com sucesso!" + ANSI_RESET);
        } else {
            System.out.println("Cupom com código " + codigo + " não encontrado.");
        }
    }

    private static void expirarCupom() {
        listarCupons();
        System.out.print("Código do cupom para expirar: ");
        String codigo = scanner.nextLine().trim();

        boolean sucesso = repoCupons.expirarPorCodigo(codigo);
        if (sucesso) {
            System.out.println(ANSI_GREEN + "Cupom " + codigo + " expirado com sucesso!" + ANSI_RESET);
        } else {
            System.out.println("Cupom com código " + codigo + " não encontrado.");
        }
    }

    private static List<Cliente> buscarClientePorNome(String parteNome) {
        return repoClientes.listar().stream()
                .filter(c -> c.getNome().toLowerCase().contains(parteNome.toLowerCase()))
                .collect(Collectors.toList());
    }
}