<img src="https://img.shields.io/badge/STATUS-CONCLUÍDO-green"/> 

# Projeto de microsserviços spring-boot

## Sobre o projeto
Api backend de microsserviços que simula um fluxo de pedidos em um e-commerce. O Projeto conta com uso de tecnologias como kafka para mensageria, eureka server, feing client, api gateway, entre outros


## Tecnologias Utilizadas
* Java
* Spring Boot
* Spring Cloud
* JPA / Hibernate
* Maven
* MySQL
* Postgres
* Keycloak
* Eureka Server
* API Gateway
* Feing Client
* Kafka

## Fluxo Principal

A Principal finalidade desse projeto é demonstra o fluxo no momento em que um cliente realiza um pedido desde quais serviços são usados para essa operação, envio de eventos para serviços de mensageria (kafka), quais serviços consomem esses eventos e qual medida deve ser tomada em caso de falhas como falta de estoque.



1. O cliente realiza uma solicitação para criar um pedido.

2. A API de Pedidos utiliza comunicação síncrona, por meio do **Feign Client**, para consultar a API de Produtos.

3. A API de Produtos retorna as informações necessárias dos produtos, como preço e demais dados necessários para o pedido.

4. A API de Pedidos calcula o valor total de cada item e o valor total do pedido.

5. Após o cálculo dos valores, o pedido é salvo no banco de dados com o status **PENDENTE**.

6. Em seguida, a API de Pedidos publica um evento no **Kafka** solicitando à API de Produtos a validação e a atualização do estoque.

7. A API de Produtos consome o evento e verifica se existe estoque suficiente para atender ao pedido.

8. Caso exista estoque disponível:

   - A quantidade dos produtos é decrementada.
   - A API de Produtos publica um evento de sucesso no Kafka.

9. Caso não exista estoque suficiente:

   - Nenhuma alteração no estoque é realizada.
   - A API de Produtos publica um evento de falha no Kafka.

10. A API de Pedidos consome o evento de resposta.

11. Caso o processamento do estoque tenha sido realizado com sucesso, o status do pedido é alterado para **FINALIZADO**.

12. Caso não seja possível processar o estoque, o status do pedido é alterado para **CANCELADO**.

## Resumo da Comunicação

- **Feign Client:** utilizado para comunicação síncrona entre a API de Pedidos e a API de Produtos durante a criação do pedido e cálculo dos valores.

- **Kafka:** utilizado para comunicação assíncrona durante a validação e atualização do estoque.

## Fluxo Resumido

1. Cliente cria o pedido.
2. API de Pedidos consulta os produtos via Feign.
3. API de Pedidos calcula os valores.
4. Pedido é salvo como **PENDENTE**.
5. Evento é enviado para o Kafka.
6. API de Produtos verifica e atualiza o estoque.
7. API de Produtos envia o resultado pelo Kafka.
8. Pedido é atualizado para **FINALIZADO** ou **CANCELADO**.

## 🔐 Autenticação & Segurança (Keycloak)
O ecossistema utiliza **Keycloak** para gerenciamento de identidade e emissão de tokens **OAuth2 / OpenID Connect (JWT)**. 

Ao subir o ambiente via `docker-compose up`, o Keycloak é inicializado e provisionado automaticamente com o Realm, Clients e Usuários de teste pré-configurados.

---

### 1. Parâmetros de Conexão

* **Realm:** `produtos-realm`
* **Token Endpoint:** `http://localhost:8080/realms/produtos-realm/protocol/openid-connect/token`
* **Client ID:** `client123`
* **Client Secret:** `**********` 
* **Grant Type:** `password`

---

### 2. Usuários de Teste Pré-cadastrados

Utilize as credenciais abaixo conforme o fluxo do sistema que deseja testar:

| Perfil / Função | Username | Password | Permissões / Roles |
| :--- | :--- | :--- | :--- |
| **Usuário Padrão** | `user` | `user123` | `ROLE_USER` |
| **Administrador** | `admin` | `admin123` | `ROLE_ADMIN` |

---

### 3. Como Obter e Utilizar o Token JWT no postman



Obtenha o `access_token` executando:

1. Abra o postman, crie uma request e entre na aba 'Authorization'
2. Escolha a opção oauth2
3. preencha os seguintes campos
* Grant type: Authorization code
* Callback URL: http://localhost:8080/callback
* Auth URL: http://localhost:28080/realms/produtos-realm/protocol/openid-connect/auth
* Access Token URL: http://localhost:28080/realms/produtos-realm/protocol/openid-connect/token
* Client ID: client123
* Client Secret: **********

<img width="753" height="636" alt="postman-oauth2" src="https://github.com/user-attachments/assets/f2f075c9-9519-4110-85f3-ff3c5d26b757" />


4. Depois, é só clicar no botão 'Get New Access Token' que irá abrir a tela de login do keycloak



## Inicialização com docker compose
Para iniciar o projeto localmente, basta ter o docker instalado na máquina e executar o comando:


docker compose up --build



Depois disso, o projeto ira subir a aplicação em alguns minutos, A API gateway estará disponível para teste na porta 3000 (http://localhost:3000)


## Entidades

### API Produtos
#### Produto

| id | nome | preco | quantidade |
| :---: | :--- | :---: | :---: |
| 1 | Teclado Mecânico RGB | 250.00 | 15 |
| 2 | Mouse Sem Fio Ergonômico | 120.50 | 30 |
| 3 | Monitor 24" Full HD | 899.90 | 8 |


### API Pedidos



#### Pedidos

| id | usuario_id | valor_total | status | data_compra |
| :---: | :--- | :---: | :---: | :---: |
| 1 | usr_abc123 | 370.50 | FINALIZADO | 2026-03-01 |
| 2 | usr_xyz789 | 899.90 | CANCELADO | 2026-03-02 |
| 3 | usr_def456 | 250.00 | PENDENTE | 2026-03-05 |

### Item

| id | produto_id | pedido_id | preco_unitario | preco_total | quantidade |
| :---: | :---: | :---: | :---: | :---: | :---: |
| 1 | 1 | 1 | 250.00 | 250.00 | 1 |
| 2 | 2 | 1 | 120.50 | 241.00 | 2 |
| 3 | 3 | 2 | 899.90 | 899.90 | 1 |


### API RegistroCompra

#### Histórico

| id | produto_id | usuario_id | valor_unitario | valor_total | quantidade | data_compra |
| :---: | :---: | :--- | :---: | :---: | :---: | :---: |
| 1 | 1 | usr_abc123 | 250.00 | 250.00 | 1 | 2026-03-01 |
| 2 | 2 | usr_abc123 | 120.50 | 241.00  | 2 | 2026-03-01 |
| 3 | 3 | usr_xyz789 | 899.90 | 899.90 | 1 | 2026-03-02 |


## Serviços

### API de Produtos
porta: 8000\
host: http://localhost:8000

| Método | Endpoint no Gateway | Endpoint Interno | Descrição | Autorização |
| :---: | :---: | :---: | :---: | :---: |
| POST | `/produtos-api/produtos` | `/produtos` | Criar produto | ADMIN |
| GET | `/produtos-api/produtos/{id}` | `/produtos/{id}` | Buscar produto por ID | ADMIN ou USER |
| GET | `/produtos-api/produtos/listaProdutos?ids={ids}` | `/produtos/listaProdutos?ids={ids}` | Buscar produtos por IDs | ADMIN ou USER |
| GET | `/produtos-api/produtos` | `/produtos` | Listar produtos | ADMIN ou USER |
| PATCH | `/produtos-api/produtos/{id}/decrementar?valor={valor}` | `/produtos/{id}/decrementar?valor={valor}` | Decrementar estoque | ADMIN |
| PATCH | `/produtos-api/produtos/{id}/acrescentar?valor={valor}` | `/produtos/{id}/acrescentar?valor={valor}` | Acrescentar estoque | ADMIN |

---

### API de Pedidos
porta: 8100\
host: http://localhost:8100

| Método | Endpoint no Gateway | Endpoint Interno | Descrição | Autorização |
| :---: | :---: | :---: | :---: | :---: |
| POST | `/pedidos-api/pedidos` | `/pedidos` | Criar pedido | ADMIN ou USER |
| GET | `/pedidos-api/pedidos` | `/pedidos` | Listar pedidos | ADMIN ou USER |
| GET | `/pedidos-api/pedidos/{id}` | `/pedidos/{id}` | Obter pedido | ADMIN ou USER |


### API Gateway
porta: 8300\
host: http://localhost:8300

| Serviço | Prefixo no Gateway | Destino | url|
| :---: | :---: | :---: | :---: |
| Produtos | `/produtos-api/**` | `lb://produtos-api` | `http://localhost:8300/produtos-api`|
| Pedidos | `/pedidos-api/**` | `lb://pedidos-api` | `http://localhost:8300/pedidos-api`|


### API Historico-service
porta: 8400\
host: http://localhost:8400

Responsável apenas por receber um evento kafka e registra o registro de compra no banco de dados.


### Eureka Server
porta: 8761\
host: http://localhost:8761

Responsável por registrar os endereços dos serviços facilitando a comunicação entre eles.


### Keycloak
porta: 8080\
host: http://localhost:8080

Responsável pela autenticação e geração de tokens jwt.

### Kafka
porta: 9092\
host: http://localhost:9092

Responsável pela comunicação assincrona(mensageria) entre os microsserviços.

