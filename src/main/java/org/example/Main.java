package org.example;

import org.example.DAO.MaquinaDAO;
import org.example.Model.Maquina;
import org.example.DAO.TecnicoDAO;
import org.example.Model.Tecnico;

import java.sql.SQLException;
import java.util.Scanner;

public class

Main {
    private static final MaquinaDAO maquinaDAO = new MaquinaDAO();

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
            } catch (Exception e) {
                System.out.println("Ocorreu um erro inesperado: " + e.getMessage());
            }

            if (opcao != 0) {
                System.out.println("\nPressione Enter para continuar...");
                scanner.nextLine();
            }
        }
        scanner.close();
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
            case 3 -> cadastrarPecas(scanner);
            case 4, 5, 6 -> System.out.println("Funcionalidade ainda não implementada.");
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

        String statusInicial = "OPERACIONAL";
        Maquina novaMaquina = new Maquina(nome, setor, statusInicial);

        maquinaDAO.inserir(novaMaquina);

        System.out.println("Máquina cadastrada com sucesso!");
    }

    private static void cadastrarTecnico(Scanner scanner) throws SQLException{
        System.out.println("\n--- 2. Cadastrar Técnico ---");

        System.out.print("Nome do técnico: ");
        String nome = scanner.nextLine();

        System.out.print("Digite sua especialidade (Podendo ser em branco): ");
        String especialidade = scanner.nextLine();

        if (nome.trim().isEmpty()) {
            System.out.println("Erro: Nome é obrigatório.");
            return;
        }

        if (TecnicoDAO.existeTecnico(nome, especialidade)) {
            System.out.println("Erro: Já existe uma tecnico com este nome e esta especialidade cadastrado.");
            return;
        }

        Tecnico novoTecnico = new Tecnico(id, nome, especialidade);

        TecnicoDAO.inserir(novoTecnico);

        System.out.println("Tecnico cadastrado com sucesso!");

    }
}