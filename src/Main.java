import model.Cliente;
import model.Produto;
import model.Pedido;
import model.StatusPedido;
import repositorio.Repositorio;
import repositorio.RepositorioEmMemoria;

import java.util.UUID;
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final Repositorio<Cliente> repoClientes = new RepositorioEmMemoria<>();
    private static final Repositorio<Produto> repoProdutos = new RepositorioEmMemoria<>();
    private static final Repositorio<Pedido> repoPedidos = new RepositorioEmMemoria<>();

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_WHITE = "\u001B[37m";

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
        System.out.println(ANSI_GREEN + "|" + centralizarTexto("Ada E-Commerce", largura) + "|" + ANSI_RESET);
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
        System.out.println(ANSI_GREEN + "|" + " ".repeat(largura) + "|" + ANSI_RESET);

        System.out.println(ANSI_GREEN + "|" + ANSI_WHITE + formatarItemMenu("0 - Sair", largura) + ANSI_GREEN + "|" + ANSI_RESET);
        System.out.println(ANSI_GREEN + " " + "-".repeat(largura) + ANSI_RESET);
    }

    private static String formatarLinha(String texto, int largura) {
        if (texto.length() > largura) {
            return texto.substring(0, largura);
        }
        return texto + " ".repeat(largura - texto.length());
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
        System.out.println(ANSI_GREEN + "Cliente cadastrado! ID: " + c.getId() + ANSI_RESET);
    }

    private static void listarClientes() {
        System.out.println("=== Clientes ===");
        for (Cliente c : repoClientes.listar()) {
            System.out.println(c.getId() + " | " + c.getNome() + " | " + c.getDocumento());
        }
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
            System.out.println(ANSI_GREEN + "Cliente atualizado!" + ANSI_RESET);
        }, () -> System.out.println("Cliente não encontrado."));
    }

    private static void cadastrarProduto() {
        System.out.print("Nome do produto: ");
        String nome = scanner.nextLine().trim();
        System.out.print("Preço (ex: 199.90): ");
        double preco = Double.parseDouble(scanner.nextLine().trim());
        Produto p = new Produto(nome, preco);
        repoProdutos.salvar(p);
        System.out.println(ANSI_GREEN + "Produto cadastrado! ID: " + p.getId() + ANSI_RESET);
    }

    private static void listarProdutos() {
        System.out.println("=== Produtos ===");
        for (Produto p : repoProdutos.listar()) {
            System.out.println(p.getId() + " | " + p.getNome() + " | R$ " + p.getPrecoBase());
        }
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
            System.out.println(ANSI_GREEN + "Produto atualizado!" + ANSI_RESET);
        }, () -> System.out.println("Produto não encontrado."));
    }

    private static void criarPedido() {
        listarClientes();
        System.out.print("ID do cliente: ");
        UUID clienteId = UUID.fromString(scanner.nextLine().trim());
        Cliente cliente = repoClientes.buscarPorId(clienteId).orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado."));
        Pedido pedido = new Pedido(cliente);
        repoPedidos.salvar(pedido);
        System.out.println(ANSI_GREEN + "Pedido criado! ID: " + pedido.getId() + ANSI_RESET);
    }

    private static Pedido selecionarPedido() {
        listarPedidos();
        System.out.print("ID do pedido: ");
        UUID id = UUID.fromString(scanner.nextLine().trim());
        return repoPedidos.buscarPorId(id).orElseThrow(() -> new IllegalArgumentException("Pedido não encontrado."));
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
        System.out.println(ANSI_GREEN + "Item adicionado!" + ANSI_RESET);
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
        System.out.println(ANSI_GREEN + "Item removido!" + ANSI_RESET);
    }

    private static void finalizarPedido() {
        Pedido pedido = selecionarPedido();
        pedido.finalizarPedido();
        repoPedidos.atualizar(pedido);
        System.out.println(ANSI_GREEN + "Pedido finalizado (AGUARDANDO_PAGAMENTO)!" + ANSI_RESET);
    }

    private static void pagarPedido() {
        Pedido pedido = selecionarPedido();
        pedido.pagarPedido();
        repoPedidos.atualizar(pedido);
        System.out.println(ANSI_GREEN + "Pedido marcado como PAGO!" + ANSI_RESET);
    }

    private static void entregarPedido() {
        Pedido pedido = selecionarPedido();
        pedido.entregarPedido();
        repoPedidos.atualizar(pedido);
        System.out.println(ANSI_GREEN + "Pedido entregue (FINALIZADO)!" + ANSI_RESET);
    }

    private static void listarPedidos() {
        System.out.println("=== Pedidos ===");
        for (Pedido p : repoPedidos.listar()) {
            System.out.println(p.getId() + " | Cliente: " + p.getCliente().getNome() + " | Status: " + p.getStatus() + " | Total: R$ " + String.format("%.2f", p.calcularTotal()));
        }
    }
}
