# AZ Avaliação

## Como subir o sistema

### Pré-requisitos

- [Docker](https://docs.docker.com/engine/install/) e [Docker Compose](https://docs.docker.com/compose/install/)

### Passo a passo

1. **Clone o repositório** e acesse a pasta do projeto:

   ```bash
   git clone <url-do-repositorio>
   cd Dev
   ```

2. **Configure as variáveis de ambiente**: copie o arquivo de exemplo e ajuste se necessário:

   ```bash
   cp example.env .env
   ```
   
   ```
   DB_DATABASE=az_avaliacao
   DB_USERNAME=postgres
   DB_PASSWORD=postgres
   DB_URL=jdbc:postgresql://db:5432/az_avaliacao
   ```

3. **Suba os containers** com Docker Compose:

   ```bash
   docker compose up --build
   ```

   Na primeira execução o build pode demorar alguns minutos (download de imagens e dependências Maven/npm).

4. **Acesse o sistema:**

   | Serviço       | URL                          |
   |---------------|-----------------------------|
   | Frontend (Angular) | http://localhost:4200    |
   | API (Spring Boot)  | http://localhost:8080    |
   | Banco (PostgreSQL) | localhost:5432           |

5. **Para parar os containers:**

   ```bash
   docker compose down
   ```

   Para remover também os volumes (limpa os dados do banco):

   ```bash
   docker compose down -v
   ```

### Estrutura dos serviços

| Serviço | Diretório      | Tecnologia            | Porta |
|---------|---------------|-----------------------|-------|
| `db`    | —             | PostgreSQL 18 Alpine  | 5432  |
| `api`   | `leilao-api/` | Spring Boot 2.7 / Java 11 | 8080  |
| `app`   | `leilao-app/` | Angular 21            | 4200  |

Os fontes da API (`leilao-api/src`) e do frontend (`leilao-app/src`) são montados como volumes nos containers, portanto alterações no código são refletidas com hot reload automaticamente.

Projeto para avaliação e seleção de candidatos a vaga de programador full stack da empresa AZ Tecnologia em Gestão. 
Nesta avaliação você deverá criar uma aplicação completa utilizando os frameworks e banco de dados abaixo:

**Client**: Vue
 
**Server**: Spring Boot. 

**Banco de dados**: PostgreSQL

## Exercício 1
Crie um script para a criação do banco de dados (DDL) contendo todas as tables, columns, constraints (primary key, foreing key e unique) e sequence.

Não esqueça de observar as colunas NOT NULL que estão marcadas com asterisco no diagrama e os campos únicos, sublinhados.

![alt Banco de dados][database]

## Exercício 2
Prepare script (DML) com uma massa de dados inicial para permitir que a aplicação seja testada. Adicione pelo menos 10 registros em cada tabela.

## Exercício 3
Prepare os serviços rest responsáveis pelas operações CRUD (Create, Read, Update e Delete) de **todas as tabelas**.
Utilize o seguinte padrão:

- GET /nomedatabela: Recupera todos os registros da tabela.
- GET /nomedatabela/:id: Recupera somente o registro que seja igual o :id informado.
- POST /nomedatabela: Insere um novo registro na tabela com os dados enviados no body da mensagem.
- PUT /nomedatabela/:id: Altera o registro que seja igual o :id informado e com os dados recebidos no body da mensagem.
- DELETE /nomedatabela/:id: Remove da tabela o registro com o :id informado.

Exemplos:

- GET /unidade/1
- POST /leiao
- PUT /comprador/2

### Padrões

#### Front-End 

* **Aplicação Cliente**: Front-End Vue. Separe bem o código em componentes.

#### Back-End

* **Controladores REST**: pacote service - endpoints dos serviços rest (POST, PUT, UPDATE e DELETE)
* **Camada de serviço**: pacote business - implementação das regras de negócio
* **Camada de acesso a dados (Repository)**: pacote repository
* **Camada de domínio**: pacote entity

![alt_arquitetura][arquitetura]


## Exercício 4

Crie a tela principal do sistema no arquivo App.vue. Esta tela deve ter um menu para navegar de uma página para outra. 
O menu deve ter links com as opções: unidades, empresas e leilões. 
Cada página deve ter uma view separada: views/unidade/Unidades.vue, views/empresa/Empresas.vue e views/Leilao/leiloes.vue, respectivamente. 
Ao clicar no link, o sistema deve mudar a url para #/nomedatela e o conteúdo interno da section root deve ser trocado pelo html da 
página selecionada.

- HTML da página inicial: App.vue
- URL da página unidades: http://server/#/unidades
- URL da página leiloes: http://server/#/leiloes
- URL da página empresas: http://server/#/empresas

## Exercício 5
Crie uma tela de consulta e edição simples para o cadastro de Unidade. 
Uma grid editável capaz de realizar todas as operações CRUD na tabela de Unidade (v-data-table). Siga os padrões:

- URL da página:            http://server/#/unidades
- URL dos serviços restful: http://server/unidades (GET | POST | PUT | DELETE)
- HTML:                     views/unidade/Unidades.vue
- COMPONENTES:              components/

## Exercício 6
Crie uma tela de consulta parametrizada para cadastro de Empresas. 
Uma grid somente leitura (v-data-table) contendo os campos: cnpj, razaoSocial, telefone e email.

- URL da página:            http://server/#/empresas
- URL dos serviços restful: http://server/empresas (GET)
- HTML:                     views/empresa/Empresas.vue
- COMPONENTES:              components/

## Exercício 7
Crie uma tela de consulta parametrizada para cadastro de Leilões. 
Uma grid somente leitura (v-data-table) contendo os campos: razaoSocial do vendedor, inicioPrevisto, total do leilão (soma dos totais individuais de cada lote (quantidade * valorInicial)).

- URL da página:            http://server/#/leiloes
- URL dos serviços restful: http://server/leiloes (GET)
- HTML:                     views/leilao/Leiloes.vue
- COMPONENTES:              components/

## Exercício 8
Crie a tela de inclusão e alteração do cadastro de empresas.
Observe que esta tela deve validar os campos obrigatórios especificados no modelo de banco de dados. 
Deve validar também as máscaras e os formatos de número, email, url e tamanho máximo do texto. Quanto mais completo melhor.
Não esqueça de criar os links para editar e excluir uma empresa na tela de consulta feita no exercício 6. 
O link de editar deve chamar a tela de cadastro usando a seguinte url #/leilao/:id

- URL da página INCLUIR:    http://server/#/empresa
- URL da página EDITAR:     http://server/#/empresa/:id
- URL dos serviços restful: http://server/empresa (GET | POST | PUT | DELETE)
- HTML:                     views/empresa/Empresa.vue
- COMPONENTES:              components/


## Teste tudo muito bem e boa sorte.

[database]: imgs/database.png
[arquitetura]: imgs/arquitetura.png
