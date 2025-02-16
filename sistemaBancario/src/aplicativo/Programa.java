package aplicativo;

import java.math.BigDecimal;
import java.util.Scanner;

import exception.ClienteException;
import exception.ContaException;
import model.Cliente;
import model.ContaCorrente;
import model.ContaPoupanca;
import persistence.PersistenceEmArquivo;

public class Programa {
	private static Scanner scanner = new Scanner(System.in);
	private static PersistenceEmArquivo persistence = PersistenceEmArquivo.getInstance();

	public static void main(String[] args) throws ClienteException {
		while (true) {
			exibirMenu();
			int opcao = scanner.nextInt();
			scanner.nextLine(); // Limpar o buffer de linha

			switch (opcao) {
			case 1:
				cadastrarCliente(); // Aqui a exceção é propagada
				break;
			case 2:
				acessarConta();
				break;
			case 3:
				System.out.println("Saindo...");
				return;
			default:
				System.out.println("Opção inválida. Tente novamente.");
			}
		}
	}

	private static void exibirMenu() {
		System.out.println("\n--- Sistema Bancário ---");
		System.out.println("1 - Cadastrar Cliente");
		System.out.println("2 - Acessar Conta");
		System.out.println("3 - Sair");
		System.out.print("Escolha uma opção: ");
	}

	private static void cadastrarCliente() throws ClienteException {
		System.out.print("Digite o CPF do cliente: ");
		String cpf = scanner.nextLine();

		if (persistence.localizarClientePorCPF(cpf) != null) {
			System.out.println("Cliente já cadastrado!");
			return;
		}

		System.out.print("Digite o nome do cliente: ");
		String nome = scanner.nextLine();

		Cliente cliente = new Cliente(nome, cpf);
		persistence.salvarCliente(cliente);

		System.out.println("Cliente cadastrado com sucesso!");
	}

	private static void acessarConta() throws ClienteException {
		System.out.print("Digite o CPF do cliente: ");
		String cpf = scanner.nextLine();

		Cliente cliente = persistence.localizarClientePorCPF(cpf);
		if (cliente == null) {
			System.out.println("Cliente não encontrado!");
			return;
		}

		System.out.println("Conta do cliente: " + cliente.getNome());
		exibirMenuContas(cliente);
	}

	private static void exibirMenuContas(Cliente cliente) throws ClienteException {
		System.out.println("\n1 - Criar Conta Corrente");
		System.out.println("2 - Criar Conta Poupança");
		System.out.println("3 - Consultar Saldo");
		System.out.println("4 - Depositar");
		System.out.println("5 - Sacar");
		System.out.println("6 - Transferir");
		System.out.println("7 - Voltar");
		System.out.print("Escolha uma opção: ");

		int opcao = scanner.nextInt();
		scanner.nextLine(); // Limpar o buffer de linha

		switch (opcao) {
		case 1:
			criarContaCorrente(cliente);
			break;
		case 2:
			criarContaPoupanca(cliente);
			break;
		case 3:
			consultarSaldo(cliente);
			break;
		case 4:
			depositar(cliente);
			break;
		case 5:
			sacar(cliente);
			break;
		case 6:
			transferir(cliente);
			break;
		case 7:
			return;
		default:
			System.out.println("Opção inválida. Tente novamente.");
		}
	}

	private static void criarContaCorrente(Cliente cliente) throws ClienteException {
		System.out.print("Digite o número da Conta Corrente: ");
		String numero = scanner.nextLine();

		ContaCorrente contaCorrente = new ContaCorrente(numero);
		try {
			cliente.adicionarConta(contaCorrente);
		} catch (ClienteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("Conta Corrente criada com sucesso!");
	}

	private static void criarContaPoupanca(Cliente cliente) throws ClienteException {
		System.out.print("Digite o número da Conta Poupança: ");
		String numero = scanner.nextLine();

		ContaPoupanca contaPoupanca = new ContaPoupanca(numero);
		try {
			cliente.adicionarConta(contaPoupanca);
		} catch (ClienteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("Conta Poupança criada com sucesso!");
	}

	private static void consultarSaldo(Cliente cliente) {
		System.out.print("Digite o número da conta: ");
		String numeroConta = scanner.nextLine();

		cliente.getContas().stream().filter(conta -> conta.getNumero().equals(numeroConta)).findFirst().ifPresentOrElse(
				conta -> System.out.println("Saldo da conta " + conta.getNumero() + ": R$" + conta.getSaldo()),
				() -> System.out.println("Conta não encontrada!"));
	}

	private static void depositar(Cliente cliente) {
		System.out.print("Digite o número da conta: ");
		String numeroConta = scanner.nextLine();

		System.out.print("Digite o valor do depósito: ");
		BigDecimal valor = scanner.nextBigDecimal();
		scanner.nextLine(); // Limpar o buffer de linha

		cliente.getContas().stream().filter(conta -> conta.getNumero().equals(numeroConta)).findFirst()
				.ifPresentOrElse(conta -> {
					try {
						conta.realizarDeposito(valor);
					} catch (ContaException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					System.out.println("Depósito realizado com sucesso!");
				}, () -> System.out.println("Conta não encontrada!"));
	}

	private static void sacar(Cliente cliente) {
		System.out.print("Digite o número da conta: ");
		String numeroConta = scanner.nextLine();

		System.out.print("Digite o valor do saque: ");
		BigDecimal valor = scanner.nextBigDecimal();
		scanner.nextLine(); // Limpar o buffer de linha

		cliente.getContas().stream().filter(conta -> conta.getNumero().equals(numeroConta)).findFirst()
				.ifPresentOrElse(conta -> {
					try {
						if (conta.realizarSaque(valor)) {
							System.out.println("Saque realizado com sucesso!");
						} else {
							System.out.println("Saldo insuficiente!");
						}
					} catch (ContaException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}, () -> System.out.println("Conta não encontrada!"));
	}

	private static void transferir(Cliente cliente) {
		System.out.print("Digite o número da conta origem: ");
		String numeroContaOrigem = scanner.nextLine();

		System.out.print("Digite o número da conta destino: ");
		String numeroContaDestino = scanner.nextLine();

		System.out.print("Digite o valor da transferência: ");
		BigDecimal valor = scanner.nextBigDecimal();
		scanner.nextLine(); // Limpar o buffer de linha

		cliente.getContas().stream().filter(conta -> conta.getNumero().equals(numeroContaOrigem)).findFirst()
				.ifPresentOrElse(contaOrigem -> {
					cliente.getContas().stream().filter(conta -> conta.getNumero().equals(numeroContaDestino))
							.findFirst().ifPresentOrElse(contaDestino -> {
								try {
									if (contaOrigem.realizarTransferencia(contaDestino, valor)) {
										System.out.println("Transferência realizada com sucesso!");
									} else {
										System.out.println("Erro na transferência.");
									}
								} catch (ContaException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}, () -> System.out.println("Conta destino não encontrada!"));
				}, () -> System.out.println("Conta origem não encontrada!"));
	}
}
