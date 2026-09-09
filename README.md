# Projeto de microsserviços spring-boot

## Sobre o projeto
Api backend de microsserviços que simula um fluxo de pedidos em um e-commerce. O Projeto conta com uso de tecnologias como kafka para mensageria, eureka server, feing client, api gateway, entre outros


## Tecnologias Utilizadas
* Java
* Spring Boot
* JPA / Hibernate
* Maven
* MySQL
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




## Inicialização com docker compose
Para iniciar o projeto localmente, basta ter o docker instalado na máquina e executar o comando:

```
docker compose up --build

```

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






