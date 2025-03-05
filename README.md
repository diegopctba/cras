## Pré-requisitos

Maven

Java 17+

Postman

Observação:
Arquivo Movielist.csv deve estar na mesma pasta da aplicação

## Executando a aplicação

Execute os passo em ordem:

1. Baixe o código:
```shell
git clone git@github.com:diegopctba/cras.git
```
2. Execute a aplicação usando maven:
```shell
./mvnw clean spring-boot:run
```
3. Importe a collection Gras.postman_coolection.json no Postman
4. Execute o serviço http://localhost:8080/movies/awards pelo método GET