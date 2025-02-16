package persistence;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import model.Cliente;

public class PersistenceEmArquivo implements Serializable {

	private static final long serialVersionUID = 1L;
	private List<Cliente> cadastroClientes = new ArrayList<>();
	private static PersistenceEmArquivo instance;

	private PersistenceEmArquivo() {
		carregarDadosDeArquivo();
	}

	// Singleton para garantir uma única instância
	public static PersistenceEmArquivo getInstance() {
		if (instance == null) {
			instance = new PersistenceEmArquivo();
		}
		return instance;
	}

	// Salvar cliente na lista
	public void salvarCliente(Cliente cliente) {
		if (!cadastroClientes.contains(cliente)) {
			cadastroClientes.add(cliente);
			salvarDadosEmArquivo();
			System.out.println("Cliente cadastrado com sucesso!");
		} else {
			System.err.println("Cliente já cadastrado no sistema!");
		}
	}

	// Localizar cliente por CPF
	public Cliente localizarClientePorCPF(String cpf) {
		return cadastroClientes.stream().filter(cliente -> cliente.getCpf().equals(cpf)).findFirst().orElse(null); // Retorna
																													// null
																													// caso
																													// o
																													// cliente
																													// não
																													// seja
																													// encontrado
	}

	// Atualizar dados de um cliente
	public void atualizarClienteCadastro(Cliente cliente) {
		int index = encontrarClienteIndex(cliente);
		if (index != -1) {
			cadastroClientes.set(index, cliente);
			salvarDadosEmArquivo();
		} else {
			System.err.println("Cliente não encontrado!");
		}
	}

	// Salvar lista de clientes em arquivo
	private void salvarDadosEmArquivo() {
		try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("dados"))) {
			oos.writeObject(cadastroClientes);
			System.out.println("Dados salvos com sucesso!");
		} catch (IOException e) {
			System.err.println("Erro ao salvar os dados: " + e.getMessage());
		}
	}

	// Carregar lista de clientes do arquivo
	private void carregarDadosDeArquivo() {
		try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("dados"))) {
			cadastroClientes = (ArrayList<Cliente>) ois.readObject();
		} catch (FileNotFoundException e) {
			System.out.println("Nenhum dado encontrado, criando novo arquivo.");
		} catch (IOException | ClassNotFoundException e) {
			System.err.println("Erro ao carregar os dados: " + e.getMessage());
		}
	}

	// Encontrar índice do cliente para atualização
	private int encontrarClienteIndex(Cliente cliente) {
		for (int i = 0; i < cadastroClientes.size(); i++) {
			if (cadastroClientes.get(i).equals(cliente)) {
				return i;
			}
		}
		return -1; // Retorna -1 se não encontrar o cliente
	}
}
