package persistence;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import model.Cliente;

public class PersistenceEmArquivo implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<Cliente> cadastroClientes = new ArrayList<>();

    // Singleton
    private static PersistenceEmArquivo instance;

    private PersistenceEmArquivo() {
        carregarDadosDeArquivo();
    }

    public static PersistenceEmArquivo getInstance() {
        if (instance == null) {
            instance = new PersistenceEmArquivo();
        }
        return instance;
    }

    // Salvar cliente no arquivo
    public void salvarCliente(Cliente c) {
        if (!cadastroClientes.contains(c)) {
            cadastroClientes.add(c);
            salvarDadosEmArquivo();
            System.out.println("Cliente cadastrado com sucesso!");
        } else {
            System.err.println("Cliente já cadastrado no sistema!");
        }
    }

    // Localizar cliente por CPF
    public Cliente localizarClientePorCPF(String cpf) {
        for (Cliente cliente : cadastroClientes) {
            if (cliente.getCpf().equals(cpf)) {
                return cliente;
            }
        }
        return null;
    }

    // Atualizar cliente
    public void atualizarClienteCadastro(Cliente c) {
        int index = encontrarClienteIndex(c);
        if (index != -1) {
            cadastroClientes.set(index, c);
            salvarDadosEmArquivo();
        } else {
            System.err.println("Cliente não encontrado!");
        }
    }

    // Salvar os dados em arquivo
    public void salvarDadosEmArquivo() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("dados"))) {
            oos.writeObject(cadastroClientes);
        } catch (IOException e) {
            System.err.println("Erro ao salvar os dados: " + e.getMessage());
        }
    }

    // Carregar os dados de arquivo
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
