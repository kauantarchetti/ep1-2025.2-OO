public class App {
    public static void main(String[] args) {
        Paciente paciente1 = new Paciente();
        paciente1.setNome("Kauan");
        paciente1.setCpf("05551512126");
        paciente1.setIdade(18);
        paciente1.setHistoricoConsultas("1 Consulta");
        paciente1.setHistoricoInternacoes("0 internações");
        paciente1.setTemRegistroPlano(false);
        System.out.println(paciente1.getNome());
        System.out.println(paciente1.getCpf());
        System.out.println(paciente1.getIdade());
        System.out.println(paciente1.getHistoricoConsultas());
        System.out.println(paciente1.getHistoricoInternacoes());
        System.out.println(paciente1.getTemRegistroPlano());
        
        Medico medico1 = new Medico();
        medico1.setNome("Dr. Dario");
        medico1.setCrm(000000);
        medico1.setEspecialidade("Ortopedista");
        medico1.setCustoConsulta(1000);
        medico1.setCustoInternacao(5000);
        medico1.setAgendaHorario("08 horas da manhã");
        System.out.println(medico1.getNome());
        System.out.println(medico1.getCrm());
        System.out.println(medico1.getEspecialidade());
        System.out.println(medico1.getCustoConsulta());
        System.out.println(medico1.getAgendaHorario());
    }
}
