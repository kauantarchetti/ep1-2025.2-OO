package entidades;

public class App {
    public static void main(String[] args) {
        Service hospitalService = new Service();
        hospitalService.exibirMenu();
        hospitalService.lerEntrada();
        hospitalService.escolherMenu();
        }
    }

