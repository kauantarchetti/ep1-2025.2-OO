🏥 Trabalho Prático – Sistema de Gerenciamento Hospitalar
🎯 Objetivo
Implementar um Sistema de Gerenciamento Hospitalar em Java, aplicando conceitos avançados de **Programação Orientada a Objetos (POO), com foco em *herança, polimorfismo, encapsulamento, persistência de dados e regras de negócio mais complexas.

Descrição do Projeto
Desenvolvimento de um sistema de gerenciamento hospitalar utilizando os conceitos de orientação a objetos (herança, polimorfismo e encapsulamento) e persistência de dados em arquivos.

Dados do Aluno
Nome completo: Kauan Tarchetti Peixoto
Matrícula: 251035363
Curso: Engenharias/FCTE
Turma: Turma 02
Instruções para Compilação e Execução
Compilação:
Na raiz do projeto: javac --release 8 -encoding UTF-8 -cp lib\gson-2.11.0.jar -d out @sources.txt

Execução:
java -cp out;lib\gson-2.11.0.jar entidades.App

Estrutura de Pastas:
ep1-2025.2-OO/
├─ src/
│ ├─ entidades/
│ │ ├─ App.java
│ │ ├─ Service.java
│ │ ├─ AgendamentoConsultas.java
│ │ ├─ Consulta.java
│ │ ├─ StatusConsulta.java
│ │ ├─ Internacao.java
│ │ ├─ StatusInternacao.java
│ │ ├─ Paciente.java
│ │ ├─ PacienteComum.java
│ │ ├─ PacienteEspecial.java
│ │ ├─ PlanoDeSaude.java
│ │ ├─ Medico.java
│ │ └─ inputScanner.java
│ ├─ DAOS/
│ │ ├─ pacienteDAO.java
│ │ ├─ medicoDAO.java
│ │ ├─ consultaDAO.java
│ │ ├─ internacaoDAO.java
│ │ └─ LocalDateTimeAdapter.java
│ ├─ entidadesOUT/ 
│ └─ DAOSOUT/ 
├─ lib/
│ └─ gson-2.11.0.jar
├─ pacientes.json
├─ medicos.json
├─ consultas.json
├─ internacoes.json
└─ .vscode/
└─ settings.json

Versão do JAVA utilizada:
java 21

Vídeo de Demonstração
https://youtu.be/66o1tIYeKmM?si=quj1wp33uLGMV6Jn

Prints da Execução
Menu Principal:
<img width="508" height="130" alt="image" src="https://github.com/user-attachments/assets/47a2967b-5e7f-4d8e-bd74-1f3876b2d93c" />

Cadastro de Médico:
<img width="632" height="647" alt="image" src="https://github.com/user-attachments/assets/7e197be1-a860-4397-913a-46048b280357" />
<img width="658" height="626" alt="image" src="https://github.com/user-attachments/assets/ec3075e0-b901-49dc-8c1d-887c79f34c8a" />



Relatório de Médicos:
<img width="1781" height="832" alt="image" src="https://github.com/user-attachments/assets/8b4bad61-b453-4148-8c0b-9f2e472c2d63" />


Observações (Extras ou Dificuldades)
Tive dificuldades para implementação do DAOS e a compilação do java, Tanto que tive que alterar partes do meu código de última hora para uma lógica mais recente do java. Porém eu acredito que obtive êxito na implementação de todas as funções.
Contato
kauantarchetti@gmail.com
🖥️ Descrição do Sistema
O sistema deve simular o funcionamento de um hospital com cadastro de pacientes, médicos, especialidades, consultas e internações.

Cadastro de Pacientes

Pacientes comuns e pacientes especiais (ex: com plano de saúde).
Cada paciente deve ter: nome, CPF, idade, histórico de consultas e internações.
Cadastro de Médicos

Médicos podem ter especialidades (ex: cardiologia, pediatria, ortopedia).
Cada médico deve ter: nome, CRM, especialidade, custo da consulta e agenda de horários.
Agendamento de Consultas

Um paciente pode agendar uma consulta com um médico disponível.
Consultas devem registrar: paciente, médico, data/hora, local, status (agendada, concluída, cancelada).
Pacientes especiais (plano de saúde) podem ter vantagens, como desconto.
Duas consultas não podem estar agendadas com o mesmo médico na mesma hora, ou no mesmo local e hora
Consultas e Diagnósticos

Ao concluir uma consulta, o médico pode registrar diagnóstico e/ou prescrição de medicamentos.
Cada consulta deve ser registrada no histórico do paciente.
Internações

Pacientes podem ser internados.
Registrar: paciente, médico responsável, data de entrada, data de saída (se já liberado), quarto e custo da internação.
Deve existir controle de ocupação dos quartos (não permitir duas internações no mesmo quarto simultaneamente).
Internações devem poder ser canceladas, quando isso ocorrer, o sistema deve ser atualizado automaticamente.
Planos de saúde

Planos de saude podem ser cadastrados.
Cada plano pode oferecer descontos para especializações diferentes, com possibilidade de descontos variados.
Um paciente que tenha o plano de saúde deve ter o desconto aplicado.
Deve existir a possibilidade de um plano especial que torna internação de menos de uma semana de duração gratuita.
Pacientes com 60+ anos de idade devem ter descontos diferentes.
Relatórios

Pacientes cadastrados (com histórico de consultas e internações).
Médicos cadastrados (com agenda e número de consultas realizadas).
Consultas futuras e passadas (com filtros por paciente, médico ou especialidade).
Pacientes internados no momento (com tempo de internação).
Estatísticas gerais (ex: médico que mais atendeu, especialidade mais procurada).
Quantidade de pessoas em um determinado plano de saúde e quanto aquele plano economizou das pessoas que o usam.
⚙️ Requisitos Técnicos
O sistema deve ser implementado em Java.
Interface via terminal (linha de comando).
Os dados devem ser persistidos em arquivos (.txt ou .csv).
Deve existir menu interativo, permitindo navegar entre as opções principais.
📊 Critérios de Avaliação
Modos da Aplicação (1,5) → Cadastro de pacientes, médicos, planos de saúde, consultas e internações.
Armazenamento em arquivo (1,0) → Dados persistidos corretamente, leitura e escrita funcional.
Herança (1,0) → Ex.: Paciente e PacienteEspecial, Consulta e ConsultaEspecial, Médico e subclasses por especialidade.
Polimorfismo (1,0) → Ex.: regras diferentes para agendamento, preços de consultas.
Encapsulamento (1,0) → Atributos privados, getters e setters adequados.
Modelagem (1,0) → Estrutura de classes clara, bem planejada e com relacionamentos consistentes.
Execução (0,5) → Sistema compila, roda sem erros e possui menus funcionais.
Qualidade do Código (1,0) → Código limpo, organizado, nomes adequados e boas práticas.
Repositório (1,0) → Uso adequado de versionamento, commits frequentes com mensagens claras.
README (1,0) → Vídeo curto (máx. 5 min) demonstrando as funcionalidades + prints de execução + explicação da modelagem.
🔹 Total = 10 pontos
🔹 Pontuação extra (até 1,5) → Melhorias relevantes, como:

Sistema de triagem automática com fila de prioridade.
Estatísticas avançadas (tempo médio de internação, taxa de ocupação por especialidade).
Exportação de relatórios em formato .csv ou .pdf.
Implementação de testes unitários para classes principais.
Menu visual.
