# Agendador de Processos

## Visão Geral
Este projeto é um sistema de **agendamento e execução de processos**, desenvolvido com **Spring Boot (Java)** no backend, **Angular** no frontend e **SQL Server** como banco de dados.  
O sistema permite cadastrar, editar, excluir e acompanhar a execução de *jobs* agendados via **Quartz Scheduler**, além de processar arquivos de retorno bancário automaticamente.

---

## Tecnologias Utilizadas
- **Backend:** Spring Boot, Quartz Scheduler, Hibernate/JPA  
- **Frontend:** Angular, Bootstrap, Bootstrap Icons  
- **Banco de Dados:** SQL Server  
- **Ferramentas:** IntelliJ IDEA, VSCode, Postman, GitHub

---

## Estrutura do Projeto

### Backend (Spring Boot)
- **Controller:** Endpoints REST para manipulação dos jobs, execuções e arquivos.  
- **Service:** Camada de lógica de negócios (agendamento, processamento de arquivos, validações).  
- **Repository:** Interfaces JPA para comunicação com o banco de dados.  
- **Model:** Entidades representando as tabelas (Job, JobExecucao, ArquivoRetorno, etc).  
- **Scheduler:** Integração com Quartz Scheduler para execução automática de jobs.

### Frontend (Angular)
- **Components:**  
  - `JobsListComponent`: Lista de agendamentos com ações (novo, editar, excluir, detalhes).  
  - `JobFormComponent`: Formulário para criação e edição de agendamentos.  
  - `JobDetailsComponent`: Tela de detalhes do job e arquivos processados.  
  - `CronPickerComponent`: Conversor amigável de data/hora para expressão CRON.  

- **Service:** Comunicação com a API REST do backend.  
- **Rotas:** Configuradas em `app.routes.ts`, mapeando cada tela.  

### Banco de Dados
Modelo relacional no SQL Server:
- **JOB:** informações básicas do agendamento.  
- **JOB_EXECUCAO:** histórico de execuções realizadas.  
- **ARQUIVO_RETORNO:** arquivos processados, com controle de status (PENDENTE, PROCESSADO, ERRO).  

Scripts de criação das tabelas estão incluídos na pasta do backend.

---

## Funcionalidades
- Cadastro, edição, exclusão e listagem de jobs.  
- Validação de expressões CRON.  
- Execução automática de processos agendados.  
- Processamento de arquivos de retorno bancário, com movimentação entre pastas (`pendentes`, `processados`, `erros`).  
- Histórico de execuções e status atualizado em tempo real.  
- Exibição de arquivos associados a cada job.  

---

## Integração Frontend/Backend
A comunicação entre o frontend e o backend é feita via **API REST**:
- Angular consome os endpoints expostos pelo Spring Boot.  
- Todas as operações CRUD de jobs são feitas por meio do `JobsService` (Angular).  
- O backend expõe endpoints em `/api/jobs` e sub-recursos (`/execucoes`, `/arquivos`).  

---

## Instalação e Execução

### Pré-requisitos
- **Java 17+**  
- **Node.js 18+**  
- **SQL Server**  
- **Angular CLI** instalado globalmente (`npm install -g @angular/cli`)

### Backend
```bash
cd agendadorProcessos2
mvn clean install
mvn spring-boot:run
```

O servidor sobe em: `http://localhost:8080`

### Frontend
```bash
cd agendador-frontend
npm install
ng serve
```

O servidor Angular roda em: `http://localhost:4200`

---

## Observações
- A pasta de arquivos de retorno deve estar criada em `C:\retornos_banco\pendentes`.  
- Expressões CRON devem incluir segundos. Exemplo:  
  - A cada 10 segundos → `0/10 * * * * ?`  
  - Todos os dias às 14:32 → `0 32 14 * * ?`

