package DAOS;
import entidades.Paciente;
import java.io.*;
import java.util.*;

public class pacienteDAO implements Serializable {  
    public void criarArquivoPaciente(){
    try {
        File myFile = new File("pacientes.txt");
        if(myFile.createNewFile()){
            System.out.println("Arquivo criado: "+ myFile.getName());
        } else {
            System.out.println("O Arquivo já existe: "+ myFile.getName());
        }
    } catch (IOException e) {
        System.out.println("Erro ao criar o arquivo");
    }
    }

    public void salvarPaciente(Paciente paciente){
        try (FileWriter writer = new FileWriter("pacientes.txt",true)) {
            writer.write(paciente.getNome() + ";" + paciente.getCpf()+ ";"+ paciente.getIdade()+ ";" + paciente.getTemRegistroPlano() + System.lineSeparator());
            
        } catch (IOException e) {
            System.out.println("Erro ao salvar paciente" + e.getMessage());
        }
    }

    public List<Paciente> listarPaciente(){
        List<Paciente> pacientes = new ArrayList<>();
        try(BufferedReader reader = new BufferedReader(new FileReader("pacientes.txt"))) {
            String linha;
            while ((linha = reader.readLine()) != null){
                String[] partes = linha.split(";");
                if (partes.length == 4){
                    Paciente p = new Paciente();
                    p.setNome(partes[0]);
                    p.setCpf(partes[1]);
                    p.setIdade(Integer.parseInt(partes[2]));
                    p.setTemRegistroPlano(Boolean.parseBoolean(partes[3]));
                    pacientes.add(p);
                }
            }
        } catch(IOException e){
            System.out.println("Falha ao listar pacientes: " + e.getMessage());
        }
        return pacientes;
    }
}