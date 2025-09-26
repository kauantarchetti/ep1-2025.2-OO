package DAOS;
import entidades.Medico;
import java.io.*;
import java.util.*;


public class medicoDAO {
    public void criarArquivoMedico(){
        try {
        File myFile = new File("medicos.txt");
        if(myFile.createNewFile()){
            System.out.println("Arquivo criado: "+ myFile.getName());
        } else {
            System.out.println("O Arquivo já existe: "+ myFile.getName());
        }
    } catch (IOException e) {
        System.out.println("Erro ao criar o arquivo");
    }
    }

    public void salvarMedico(Medico medico){
        try (FileWriter writer = new FileWriter("medicos.txt",true)) {
            writer.write(medico.getNome() + ";" + medico.getCrm() + ";"+ medico.getCustoConsulta() + ";" +  medico.getCustoInternacao() + ";" + medico.getEspecialidade() + System.lineSeparator());
            
        } catch (IOException e) {
            System.out.println("Erro ao salvar paciente" + e.getMessage());
    }}

    public List<Medico> listarMedicos(){
        List<Medico> medicos = new ArrayList<>();
        try(BufferedReader reader = new BufferedReader(new FileReader("medicos.txt"))) {
            String linha;
            while ((linha = reader.readLine()) != null){
                String[] partes = linha.split(";");
                if (partes.length == 5){
                    Medico m = new Medico();
                    m.setNome(partes[0]);
                    m.setCrm(Integer.parseInt(partes[1]));
                    m.setCustoConsulta(Integer.parseInt(partes[2]));
                    m.setCustoInternacao(Integer.parseInt(partes[3]));
                    m.setEspecialidade(Integer.parseInt(partes[4]));
                    medicos.add(m);
                }
            }
        } catch(IOException e){
            System.out.println("Falha ao listar medicos: " + e.getMessage());
        }
        return medicos;

}}

