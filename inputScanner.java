import java.util.InputMismatchException;
import java.util.Scanner;


public class inputScanner {
    public Scanner scanner;

    public inputScanner() {
        this.scanner = new Scanner(System.in);
    }

    public int nextInt(){
        while(true){
            try {
                return scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida porfavor digite um número");
            }
        }
    }
}

