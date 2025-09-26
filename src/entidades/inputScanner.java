package entidades;
import java.util.InputMismatchException;
import java.util.Scanner;


public class inputScanner {
    public Scanner sc;

    public inputScanner() {
        this.sc = new Scanner(System.in);
    }

    public int nextNum(){
        while(true){
            try {
                return sc.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida porfavor digite um número");
                sc.nextLine();
            }
        }
    }
    public String nextText(){
        while(true){
            try {
                return sc.nextLine();
            } catch (InputMismatchException e) {
            }   System.out.println("Entrada inválida porfavor digite um texto");
                sc.nextLine();
        }        
    }

    public String nextEspaco(){
        return sc.nextLine();
    }
}


