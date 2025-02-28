## Pré-requisitos

Maven

Java 17+

H2 Embedded

Postman

## Executando a aplicação

Execute os passo em ordem:

1. Baixe o código:
```shell
git clone git@github.com:diegopctba/cras.git
```
2. Execute a aplicação usando maven:
```shell
./mvnw clean spring-boot:run -Dspring-boot.run.arguments="--moviesList=./Movielist.csv"
```
3. Importe a collection Gras.postman_coolection.json no Postman
4. Execute o serviço http://localhost:8080/movies/awards pelo método GET