package exception;

public class ExceptionHandler {
    public static void tratarClienteException(ClienteException e) {
        System.out.println("Erro no cadastro do cliente: " + e.getMessage());
    }

    // Caso deseje adicionar mais tipos de exceções, pode criar outros métodos aqui.
}
