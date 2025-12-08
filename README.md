# Open-Flights---API
A OpenFlights é um projeto de código aberto que se dedica a coletar, armazenar e disponibilizar dados relacionados à aviação, incluindo informações sobre aeroportos, rotas de voo e companhias aéreas. O projeto busca fornecer uma fonte aberta e acessível de dados para desenvolvedores, pesquisadores e entusiastas da aviação em todo o mundo.
A OpenFlights-API é uma API REST desenvolvida em Java para gerenciamento dos dados obtidos da OpenFlights.

## Objetivo do Projeto

O objetivo é fornecer uma interface programática para:
1.  Consultar, cadastrar, atualizar e remover aeroportos.
2.  Importar uma massa de dados inicial de um arquivo CSV público obtido da OpenFlights e que pode ser baixado separadamente em:
https://raw.githubusercontent.com/profdiegoaugusto/banco-dados/master/mysql/linguagem-consulta-dados/data/airports.csv
3.  Garantir a integridade dos dados e funções através de validações e testes automatizados.

## Tecnologias Utilizadas

* **Java 17** (Linguagem base)
* **Spring Boot 3.4.0** (Framework principal)
* **Spring Data JPA** (Persistência de dados)
* **MariaDB** (Banco de dados relacional)
* **Maven** (Gerenciador de dependências e build)
* **Lombok** (Redução de código boilerplate)
* **JUnit 5 & Mockito** (Testes de Unidade)
* **Spring Boot Test** (Testes de Integração)

---

## Configuração do Ambiente

### Pré-requisitos
* Java JDK 17 instalado.
* MariaDB instalado e rodando na porta `3306`.

### Configuração do Banco de Dados
1.  O projeto está configurado para criar o banco de dados `aeroportos_db` automaticamente, pegando de base os dados obtidos do arquivo localizado na pasta `src/main/resouces/airports (1).csv`
2.  Verifique o arquivo `src/main/resources/application.properties`. Certifique-se de que o usuário e senha do banco correspondem aos da sua máquina na parte:

```properties
spring.datasource.username=root
spring.datasource.password=SUA_SENHA_AQUI 
```
---

## Como Executar a Aplicação

Para rodar o projeto, certifique-se de que o serviço do **MariaDB** esteja rodando na porta 3306.

1.  Abra o terminal na raiz do projeto.
2.  Execute o comando abaixo para baixar as dependências e subir o servidor:

    **Windows (PowerShell):**
    ```powershell
    .\mvnw spring-boot:run
    ```

    **Linux/Mac (Bash):**
    ```bash
    ./mvnw spring-boot:run
    ```

3.  Aguarde até aparecer a mensagem `Started AeroportsApplication in ... seconds`.
4.  A API estará disponível em: `http://localhost:8080/api/v1/aeroportos`

---

## Como Executar os Testes

O projeto segue a convenção do Maven para separar testes leves (unidade) de testes pesados (integração).

### 1. Testes de Unidade (Unit Tests) 
Testam classes isoladas (Domínio, Services e Utilitários) sem carregar o Spring Boot ou Banco de Dados. São executados pelo plugin `maven-surefire-plugin`.

* **Comando:**
    ```powershell
    .\mvnw test
    ```
* **O que será testado:** Conversões de medidas, validações de regras de negócio como o limite de tamanho da IATA e lançamento de exceções.

### 2. Testes de Integração (Integration Tests) 
Testam o fluxo completo da API. O sistema sobe o servidor Spring Boot em uma porta aleatória, conecta no banco H2/MariaDB e realiza requisições HTTP reais. São executados pelo plugin `maven-failsafe-plugin`.

* **Comando:**
    ```powershell
    .\mvnw verify
    ```
* **O que será testado:** Endpoints da API (`GET`, `POST`, `PUT`, `DELETE`), persistência no banco de dados e códigos de status HTTP (200, 201, 404, etc.).

> **Nota:** O comando `verify` executa tanto os testes de unidade quanto os de integração em sequência.