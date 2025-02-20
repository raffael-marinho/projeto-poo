package aplicativo	;

import exception.ContaInativaException;
import exception.SaldoInsuficienteException;
import exception.ValorInvalidoException;
import model.Cliente;
import model.Conta;
import model.ContaCorrente;
import model.ContaPoupanca;
import model.Transacao;
import persistence.Persistencia;

import java.math.BigDecimal;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Programa {

    public static void main(String[] args) {
        Persistencia p = new Persistencia();
        Scanner sc = new Scanner(System.in);

        try {
            boolean sair = false;
            while (!sair) {
                System.out.println("\n\nBem-vindo ao sistema bancário!");
                System.out.println("Escolha uma opção:");
                System.out.println("1 - Cadastrar cliente");
                System.out.println("2 - Listar clientes");
                System.out.println("3 - Consultar cliente por CPF");
                System.out.println("4 - Menu do cliente");
                System.out.println("5 - Remover cliente");
                System.out.println("6 - Sair");

                try {
                    int opcao = sc.nextInt();
                    sc.nextLine(); // Limpar o buffer do scanner

                    switch (opcao) {
                        case 1:
                            cadastrarCliente(p, sc);
                            break;
                        case 2:
                            listarClientes(p);
                            break;
                        case 3:
                            consultarClientePorCpf(p, sc);
                            break;
                        case 4:
                            menuCliente(p, sc);
                            break;
                        case 5:
                            removerCliente(p, sc);
                            break;
                        case 6:
                            sair = true;
                            System.out.println("Saindo...");
                            break;
                        default:
                            System.out.println("Opção inválida!");
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Entrada inválida. Digite um número.");
                    sc.nextLine(); // Limpar o buffer do scanner
                }
            }
        } finally {
            // Salva os dados antes de encerrar o programa
            p.salvarClientes();
            sc.close();
        }
    }

    private static void cadastrarCliente(Persistencia p, Scanner sc) {
        System.out.print("Digite o CPF do cliente: ");
        String cpf = sc.nextLine();
        System.out.print("Digite o nome do cliente: ");
        String nome = sc.nextLine();

        Cliente cliente = new Cliente(cpf, nome);
        p.adicionarCliente(cliente);
        System.out.println("Cliente cadastrado com sucesso!");
    }

    private static void listarClientes(Persistencia p) {
        System.out.println("Lista de clientes:");
        for (Cliente cliente : p.getClientes()) {
            System.out.println(cliente);
        }
    }

    private static void consultarClientePorCpf(Persistencia p, Scanner sc) {
        System.out.print("Digite o CPF do cliente: ");
        String cpf = sc.nextLine();

        Cliente cliente = p.localizarClientePorCpf(cpf);
        if (cliente != null) {
            System.out.println("Cliente encontrado:");
            System.out.println(cliente);
        } else {
            System.out.println("Cliente não encontrado.");
        }
    }

    private static void removerCliente(Persistencia p, Scanner sc) {
        System.out.print("Digite o CPF do cliente a ser removido: ");
        String cpf = sc.nextLine();

        Cliente cliente = p.localizarClientePorCpf(cpf);
        if (cliente != null) {
            p.removerCliente(cliente);
            System.out.println("Cliente removido com sucesso!");
        } else {
            System.out.println("Cliente não encontrado.");
        }
    }

    private static void menuCliente(Persistencia p, Scanner sc) {
        System.out.print("Digite o CPF do cliente: ");
        String cpf = sc.nextLine();
        Cliente cliente = p.localizarClientePorCpf(cpf);

        if (cliente == null) {
            System.out.println("Cliente não encontrado.");
            return;
        }

        boolean voltar = false;
        while (!voltar) {
            System.out.println("\n\nMenu do cliente:");
            System.out.println("1 - Criar conta corrente");
            System.out.println("2 - Criar conta poupança");
            System.out.println("3 - Realizar depósito");
            System.out.println("4 - Realizar saque");
            System.out.println("5 - Transferir para outra conta");
            System.out.println("6 - Consultar saldo");
            System.out.println("7 - Listar transações");
            System.out.println("8 - Aplicar rendimento (Poupança)");
            System.out.println("9 - Consultar balanço");
            System.out.println("10 - Listar contas");
            System.out.println("11 - Voltar");

            try {
                int opcao = sc.nextInt();
                sc.nextLine(); // Limpar o buffer do scanner

                switch (opcao) {
                    case 1:
                        criarContaCorrente(cliente);
                        break;
                    case 2:
                        criarContaPoupanca(cliente);
                        break;
                    case 3:
                        realizarDeposito(cliente, sc);
                        break;
                    case 4:
                        realizarSaque(cliente, sc);
                        break;
                    case 5:
                        transferirEntreContas(p, sc);
                        break;
                    case 6:
                        consultarSaldo(cliente, sc);
                        break;
                    case 7:
                        listarTransacoes(cliente, sc);
                        break;
                    case 8:
                        aplicarRendimento(cliente, sc);
                        break;
                    case 9:
                        consultarBalanco(cliente);
                        break;
                    case 10:
                        listarContas(cliente);
                        break;
                    case 11:
                        voltar = true;
                        break;
                    default:
                        System.out.println("Opção inválida!");
                }
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida. Digite um número.");
                sc.nextLine(); // Limpar o buffer do scanner
            }
        }
    }

    private static void criarContaCorrente(Cliente cliente) {
        Conta novaConta = new ContaCorrente(cliente.getContas().size() + 1);
        cliente.adicionarConta(novaConta);
        System.out.println("Conta corrente criada com sucesso!");
    }

    private static void criarContaPoupanca(Cliente cliente) {
        Conta novaConta = new ContaPoupanca(cliente.getContas().size() + 1);
        cliente.adicionarConta(novaConta);
        System.out.println("Conta poupança criada com sucesso!");
    }

    private static void realizarDeposito(Cliente cliente, Scanner sc) {
        System.out.print("Digite o número da conta: ");
        int numeroConta = sc.nextInt();
        sc.nextLine(); // Limpar o buffer do scanner

        Conta conta = cliente.localizarContaPorNumero(numeroConta);
        if (conta != null) {
            System.out.print("Digite o valor do depósito: ");
            BigDecimal valor = sc.nextBigDecimal();
            sc.nextLine(); // Limpar o buffer do scanner

            try {
                conta.depositar(valor);
                System.out.println("Depósito realizado com sucesso!");
            } catch (ValorInvalidoException | ContaInativaException e) {
                System.out.println("Erro: " + e.getMessage());
            }
        } else {
            System.out.println("Conta não encontrada.");
        }
    }

    private static void realizarSaque(Cliente cliente, Scanner sc) {
        System.out.print("Digite o número da conta: ");
        int numeroConta = sc.nextInt();
        sc.nextLine(); // Limpar o buffer do scanner

        Conta conta = cliente.localizarContaPorNumero(numeroConta);
        if (conta != null) {
            System.out.print("Digite o valor do saque: ");
            BigDecimal valor = sc.nextBigDecimal();
            sc.nextLine(); // Limpar o buffer do scanner

            try {
                conta.sacar(valor);
                System.out.println("Saque realizado com sucesso!");
            } catch (SaldoInsuficienteException | ValorInvalidoException | ContaInativaException e) {
                System.out.println("Erro: " + e.getMessage());
            }
        } else {
            System.out.println("Conta não encontrada.");
        }
    }

    private static void transferirEntreContas(Persistencia p, Scanner sc) {
        System.out.print("Digite o CPF do cliente de origem: ");
        String cpfOrigem = sc.nextLine();
        Cliente clienteOrigem = p.localizarClientePorCpf(cpfOrigem);

        if (clienteOrigem == null) {
            System.out.println("Cliente de origem não encontrado.");
            return;
        }

        System.out.print("Digite o número da conta de origem: ");
        int numeroContaOrigem = sc.nextInt();
        sc.nextLine(); // Limpar o buffer do scanner

        Conta contaOrigem = clienteOrigem.localizarContaPorNumero(numeroContaOrigem);
        if (contaOrigem == null) {
            System.out.println("Conta de origem não encontrada.");
            return;
        }

        System.out.print("Digite o CPF do cliente de destino: ");
        String cpfDestino = sc.nextLine();
        Cliente clienteDestino = p.localizarClientePorCpf(cpfDestino);

        if (clienteDestino == null) {
            System.out.println("Cliente de destino não encontrado.");
            return;
        }

        System.out.print("Digite o número da conta de destino: ");
        int numeroContaDestino = sc.nextInt();
        sc.nextLine(); // Limpar o buffer do scanner

        Conta contaDestino = clienteDestino.localizarContaPorNumero(numeroContaDestino);
        if (contaDestino == null) {
            System.out.println("Conta de destino não encontrada.");
            return;
        }

        System.out.print("Digite o valor da transferência: ");
        BigDecimal valor = sc.nextBigDecimal();
        sc.nextLine(); // Limpar o buffer do scanner

        try {
            contaOrigem.transferir(contaDestino, valor);
            System.out.println("Transferência realizada com sucesso!");
        } catch (SaldoInsuficienteException | ValorInvalidoException | ContaInativaException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private static void consultarSaldo(Cliente cliente, Scanner sc) {
        System.out.print("Digite o número da conta: ");
        int numeroConta = sc.nextInt();
        sc.nextLine(); // Limpar o buffer do scanner

        Conta conta = cliente.localizarContaPorNumero(numeroConta);
        if (conta != null) {
            System.out.println("Saldo da conta: " + conta.getSaldo());
        } else {
            System.out.println("Conta não encontrada.");
        }
    }

    private static void listarTransacoes(Cliente cliente, Scanner sc) {
        System.out.print("Digite o número da conta: ");
        int numeroConta = sc.nextInt();
        sc.nextLine(); // Limpar o buffer do scanner

        Conta conta = cliente.localizarContaPorNumero(numeroConta);
        if (conta != null) {
            System.out.println("Transações da conta:");
            for (Transacao transacao : conta.getTransacoes()) {
                System.out.println(transacao);
            }
        } else {
            System.out.println("Conta não encontrada.");
        }
    }

    private static void aplicarRendimento(Cliente cliente, Scanner sc) {
        System.out.print("Digite o número da conta poupança: ");
        int numeroConta = sc.nextInt();
        sc.nextLine(); // Limpar o buffer do scanner

        Conta conta = cliente.localizarContaPorNumero(numeroConta);
        if (conta instanceof ContaPoupanca) {
            ((ContaPoupanca) conta).aplicarRendimento();
            System.out.println("Rendimento aplicado com sucesso!");
        } else {
            System.out.println("A conta não é uma conta poupança.");
        }
    }

    private static void consultarBalanco(Cliente cliente) {
        BigDecimal balancoTotal = BigDecimal.ZERO;
        System.out.println("Balanço de todas as contas do cliente:");
        for (Conta conta : cliente.getContas()) {
            System.out.println("Conta " + conta.getNumeroDaConta() + ": Saldo = " + conta.getSaldo());
            balancoTotal = balancoTotal.add(conta.getSaldo());
        }
        System.out.println("Balanço total: " + balancoTotal);
    }

    private static void listarContas(Cliente cliente) {
        System.out.println("Contas do cliente:");
        for (Conta conta : cliente.getContas()) {
            System.out.println("Número da conta: " + conta.getNumeroDaConta() + " | Saldo: " + conta.getSaldo());
        }
    }
}