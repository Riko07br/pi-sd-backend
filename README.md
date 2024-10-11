# Backend - Buzzard Pix

## Rodar local

### Requisitos

- Java 17 (ou superior)
- Maven 3.9.9 (ou superior)

### Passos

- Clone o repositório
- Entre na pasta do projeto
- Execute o comando `mvn spring-boot:run`

## Docker

### Docker Hub

A imagem está hospedada no docker hub [aqui](https://hub.docker.com/r/julioalencar/pi-sd-backend) <br />
Para dar pull na imagem: `docker pull julioalencar/pi-sd-backend`

### Imagem local

Caso queira construir uma imagem com o docker utilize o comando `docker build -t buzzard-pix-backend .`

### Rodar imagem

Para rodar a imagem utilize o comando
`docker run -p 8080:8080 --name buzzard-pix-backend buzzard-pix-backend`

## Frontend

O Repositório do frontend está hospedado [aqui](https://github.com/Riko07br/pi-sd-frontend)
