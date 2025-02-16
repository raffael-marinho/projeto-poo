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

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private List<Cliente> cadastroClientes = new ArrayList<>();

	private static PersistenceEmArquivo instance;

	private PersistenceEmArquivo() {
		carregarDadosDeArquivo();
	}

	public static PersistenceEmArquivo getInstance() {
		if (instance != null)
			return instance;
		else
			return new PersistenceEmArquivo();
	}

	public void salvarCliente(Cliente c) {
		if (!cadastroClientes.contains(c)) {
			cadastroClientes.add(c);
			salvarDadosEmArquivo();
			System.out.println("Cliente cadastrados com sucesso!");
		} else
			System.err.println("Cliente ja cadastrado no sistema!");

	}

	public Cliente localizarClientePorCPF(String cpf) {
		Cliente c = new Cliente(cpf);
		c.setCpf(cpf);
		if (cadastroClientes.contains(c)) {
			int index = cadastroClientes.indexOf(c);
			c = cadastroClientes.get(index);
			return c;
		} else
			return null;
	}

	public void atualizarClienteCadastro(Cliente c) {
		if (cadastroClientes.contains(c)) {
			int index = cadastroClientes.indexOf(c);
			cadastroClientes.set(index, c);
			salvarDadosEmArquivo();
		} else
			System.err.println("Cliente não encontrado!");
	}

	public void salvarDadosEmArquivo() {
		try {
			FileOutputStream fos = new FileOutputStream("dados");
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(cadastroClientes);
			oos.close();
			fos.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void carregarDadosDeArquivo() {

		try {
			FileInputStream fis = new FileInputStream("dados");
			ObjectInputStream ois = new ObjectInputStream(fis);
			cadastroClientes = (ArrayList<Cliente>) ois.readObject();
			ois.close();
			fis.close();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
