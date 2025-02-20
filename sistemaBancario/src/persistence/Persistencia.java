package persistence;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import model.Cliente;

public class Persistencia {
    private static final String arquivo = "clientes.dat";
    private ArrayList<Cliente> clientes;

    public Persistencia() {
        clientes = carregarClientes();
    }

    public void adicionarCliente(Cliente novoCliente) {
        if (clientes.contains(novoCliente)) {
            System.out.println("Cliente já cadastrado.");
        } else {
            clientes.add(novoCliente);
            salvarClientes();
            System.out.println("Cliente cadastrado com sucesso.");
        }
    }

    public void removerCliente(Cliente cliente) {
        if (clientes.contains(cliente)) {
            clientes.remove(cliente);
            salvarClientes();
            System.out.println("Cliente removido com sucesso.");
        } else {
            System.out.println("Cliente não encontrado.");
        }
    }

    public ArrayList<Cliente> getClientes() {
        return clientes;
    }

    public Cliente localizarClientePorCpf(String cpf) {
        for (Cliente cliente : clientes) {
            if (cliente.getCpf().equals(cpf)) {
                return cliente;
            }
        }
        return null;
    }

    // Alterado para public
    public void salvarClientes() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(arquivo))) {
            oos.writeObject(clientes);
        } catch (IOException e) {
            System.err.println("Erro ao salvar clientes: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    private ArrayList<Cliente> carregarClientes() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(arquivo))) {
            return (ArrayList<Cliente>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Erro ao carregar clientes: " + e.getMessage());
            return new ArrayList<>();
        }
    }
}