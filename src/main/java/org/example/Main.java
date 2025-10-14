package org.example;

import org.example.DAO.*;
import org.example.Model.*;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final MaquinaDAO maquinaDAO = new MaquinaDAO();
    private static final TecnicoDAO tecnicoDAO = new TecnicoDAO();
    private static final PecaDAO pecaDAO = new PecaDAO();
    private static final OrdemManutencaoDAO ordemManutencaoDAO = new OrdemManutencaoDAO();
    private static final OrdemPecaDAO ordemPecaDAO = new OrdemPecaDAO();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int opcao = -1;

        while (opcao != 0) {
            exibirMenu();
            try {
                opcao = Integer.parseInt(scanner.nextLine());
                roteador(opcao, scanner);
            } catch (NumberFormatException e) {
                System.out.println("Erro: Entrada inválida. Por favor, digite um número.");
            } catch (SQLException e) {
                System.out.println("Erro de banco de dados: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Ocorreu um erro inesperado: " + e.getMessage());
            }

            if (opcao != 0) {
                System.out.println("\nPressione Enter para continuar...");
                scanner.nextLine();
            }
        }
        scanner.close();
        System.out.println("Sistema encerrado.");
    }

    private static void exibirMenu() {
        System.out.println("\n===== Sistema de Manutenção Industrial =====");
        System.out.println("1 - Cadastrar Máquina");
        System.out.println("2 - Cadastrar Técnico");
        System.out.println("3 - Cadastrar Peça");
        System.out.println("4 - Criar Ordem de Manutenção");
        System.out.println("5 - Associar Peças à Ordem");
        System.out.println("6 - Executar Manutenção");
        System.out.println("0 - Sair");
        System.out.print("==========================================\nEscolha uma opção: ");
    }

    private static void roteador(int opcao, Scanner scanner) throws SQLException {
        switch (opcao) {
            case 1 -> cadastrarMaquina(scanner);
            case 2 -> cadastrarTecnico(scanner);
            case 3 -> cadastrarPeca(scanner);
            case 4 -> criarOrdemManutencao(scanner);
            case 5 -> associarPecasOrdem(scanner);
            case 6 -> executarManutencao(scanner);
            case 0 -> System.out.println("Saindo do sistema...");
            default -> System.out.println("Opção inválida. Tente novamente.");
        }
    }

    private static void cadastrarMaquina(Scanner scanner) throws SQLException {
        System.out.println("\n--- 1. Cadastrar Máquina ---");
        System.out.print("Nome da máquina: ");
        String nome = scanner.nextLine();
        System.out.print("Setor da máquina: ");
        String setor = scanner.nextLine();

        if (nome.trim().isEmpty() || setor.trim().isEmpty()) {
            System.out.println("Erro: Nome e setor são obrigatórios.");
            return;
        }

        if (maquinaDAO.existeMaquina(nome, setor)) {
            System.out.println("Erro: Já existe uma máquina com este nome no setor informado.");
            return;
        }

        Maquina novaMaquina = new Maquina(nome, setor, "OPERACIONAL");
        maquinaDAO.inserir(novaMaquina);
        System.out.println("Máquina cadastrada com sucesso!");
    }

    private static void cadastrarTecnico(Scanner scanner) throws SQLException {
        System.out.println("\n--- 2. Cadastrar Técnico ---");
        System.out.print("Nome do técnico: ");
        String nome = scanner.nextLine();
        System.out.print("Digite a especialidade: ");
        String especialidade = scanner.nextLine();

        if (nome.trim().isEmpty()) {
            System.out.println("Erro: Nome é obrigatório.");
            return;
        }

        if (tecnicoDAO.existeTecnico(nome, especialidade)) {
            System.out.println("Erro: Já existe um técnico com este nome e especialidade.");
            return;
        }

        Tecnico novoTecnico = new Tecnico(nome, especialidade);
        tecnicoDAO.inserir(novoTecnico);
        System.out.println("Técnico cadastrado com sucesso!");
    }

    private static void cadastrarPeca(Scanner scanner) throws SQLException {
        System.out.println("\n--- 3. Cadastrar Peça ---");
        System.out.print("Nome da peça: ");
        String nome = scanner.nextLine();

        if (nome.trim().isEmpty()) {
            System.out.println("Erro: Nome é obrigatório.");
            return;
        }
        if(pecaDAO.existePeca(nome)){
            System.out.println("Erro: Peça já cadastrada.");
            return;
        }

        System.out.print("Quantidade em estoque: ");
        double estoque = Double.parseDouble(scanner.nextLine());

        if (estoque < 0) {
            System.out.println("Erro: O estoque não pode ser negativo.");
            return;
        }

        Peca novaPeca = new Peca(nome, estoque);
        pecaDAO.inserir(novaPeca);
        System.out.println("Peça cadastrada com sucesso!");
    }

    private static void criarOrdemManutencao(Scanner scanner) throws SQLException {
        System.out.println("\n--- 4. Criar Ordem de Manutenção ---");

        List<Maquina> maquinas = maquinaDAO.listarOperacionais();
        if (maquinas.isEmpty()) {
            System.out.println("Nenhuma máquina operacional disponível para manutenção.");
            return;
        }
        System.out.println("Selecione uma máquina:");
        for (int i = 0; i < maquinas.size(); i++) {
            System.out.println((i + 1) + " - " + maquinas.get(i));
        }
        int escolhaMaquina = Integer.parseInt(scanner.nextLine()) - 1;
        if (escolhaMaquina < 0 || escolhaMaquina >= maquinas.size()) {
            System.out.println("Opção inválida.");
            return;
        }
        Maquina maquinaSelecionada = maquinas.get(escolhaMaquina);

        List<Tecnico> tecnicos = tecnicoDAO.listarTodos();
        if (tecnicos.isEmpty()) {
            System.out.println("Nenhum técnico cadastrado.");
            return;
        }
        System.out.println("Selecione um técnico:");
        for (int i = 0; i < tecnicos.size(); i++) {
            System.out.println((i + 1) + " - " + tecnicos.get(i));
        }
        int escolhaTecnico = Integer.parseInt(scanner.nextLine()) - 1;
        if (escolhaTecnico < 0 || escolhaTecnico >= tecnicos.size()) {
            System.out.println("Opção inválida.");
            return;
        }
        Tecnico tecnicoSelecionado = tecnicos.get(escolhaTecnico);

        OrdemManutencao novaOrdem = new OrdemManutencao(
                maquinaSelecionada.getId(),
                tecnicoSelecionado.getId(),
                LocalDate.now(),
                "PENDENTE"
        );

        ordemManutencaoDAO.inserir(novaOrdem);
        maquinaDAO.atualizarStatus(maquinaSelecionada.getId(), "EM_MANUTENCAO");

        System.out.println("Ordem de manutenção criada com sucesso!");
    }

    private static void associarPecasOrdem(Scanner scanner) throws SQLException {
        System.out.println("\n--- 5. Associar Peças à Ordem ---");
        List<OrdemManutencao> ordens = ordemManutencaoDAO.listarPendentes();
        if (ordens.isEmpty()) {
            System.out.println("Nenhuma ordem pendente encontrada.");
            return;
        }

        System.out.println("Selecione uma ordem pendente:");
        for (int i = 0; i < ordens.size(); i++) {
            System.out.println((i + 1) + " - " + ordens.get(i));
        }
        int escolhaOrdem = Integer.parseInt(scanner.nextLine()) - 1;
        if (escolhaOrdem < 0 || escolhaOrdem >= ordens.size()) {
            System.out.println("Opção inválida.");
            return;
        }
        OrdemManutencao ordemSelecionada = ordens.get(escolhaOrdem);

        String continuar = "s";
        while(continuar.equalsIgnoreCase("s")){
            List<Peca> pecas = pecaDAO.listarTodas();
            System.out.println("Selecione a peça para adicionar:");
            for (int i = 0; i < pecas.size(); i++) {
                System.out.println((i + 1) + " - " + pecas.get(i));
            }
            int escolhaPeca = Integer.parseInt(scanner.nextLine()) - 1;
            if (escolhaPeca < 0 || escolhaPeca >= pecas.size()) {
                System.out.println("Opção inválida.");
                continue;
            }
            Peca pecaSelecionada = pecas.get(escolhaPeca);

            System.out.print("Quantidade necessária: ");
            double quantidade = Double.parseDouble(scanner.nextLine());
            if (quantidade <= 0) {
                System.out.println("Quantidade deve ser positiva.");
                continue;
            }

            OrdemPeca novaOrdemPeca = new OrdemPeca(ordemSelecionada.getId(), pecaSelecionada.getId(), quantidade);
            ordemPecaDAO.inserir(novaOrdemPeca);
            System.out.println("Peça associada com sucesso!");

            System.out.print("Deseja adicionar outra peça? (s/n): ");
            continuar = scanner.nextLine();
        }
    }

    private static void executarManutencao(Scanner scanner) throws SQLException {
        System.out.println("\n--- 6. Executar Manutenção ---");
        List<OrdemManutencao> ordens = ordemManutencaoDAO.listarPendentes();
        if (ordens.isEmpty()) {
            System.out.println("Nenhuma ordem pendente para executar.");
            return;
        }
        System.out.println("Selecione a ordem para executar:");
        for (int i = 0; i < ordens.size(); i++) {
            System.out.println((i + 1) + " - " + ordens.get(i));
        }
        int escolhaOrdem = Integer.parseInt(scanner.nextLine()) - 1;
        if (escolhaOrdem < 0 || escolhaOrdem >= ordens.size()) {
            System.out.println("Opção inválida.");
            return;
        }
        OrdemManutencao ordemSelecionada = ordens.get(escolhaOrdem);

        List<OrdemPeca> pecasNecessarias = ordemPecaDAO.listarPecasPorOrdem(ordemSelecionada.getId());
        List<Peca> pecasEmEstoque = pecaDAO.listarTodas();
        boolean estoqueSuficiente = true;

        for (OrdemPeca necessaria : pecasNecessarias) {
            for (Peca emEstoque : pecasEmEstoque) {
                if (necessaria.getIdPeca() == emEstoque.getId()) {
                    if (emEstoque.getEstoque() < necessaria.getQuantidade()) {
                        System.out.println("Erro: Estoque insuficiente para a peça " + emEstoque.getNome());
                        estoqueSuficiente = false;
                    }
                    break;
                }
            }
        }

        if (!estoqueSuficiente) {
            System.out.println("Execução abortada por falta de peças.");
            return;
        }

        for (OrdemPeca necessaria : pecasNecessarias) {
            for (Peca emEstoque : pecasEmEstoque) {
                if (necessaria.getIdPeca() == emEstoque.getId()) {
                    double novoEstoque = emEstoque.getEstoque() - necessaria.getQuantidade();
                    pecaDAO.atualizarEstoque(emEstoque.getId(), novoEstoque);
                    break;
                }
            }
        }

        ordemManutencaoDAO.atualizarStatus(ordemSelecionada.getId(), "EXECUTADA");
        maquinaDAO.atualizarStatus(ordemSelecionada.getIdMaquina(), "OPERACIONAL");

        System.out.println("Manutenção executada com sucesso!");
    }
}