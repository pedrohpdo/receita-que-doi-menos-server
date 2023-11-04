<div align="center">
  <img alt="Unifacisa" src="https://img.shields.io/badge/Unifacisa-00599C?style=for-the-badge&logo=java&logoColor=white">
  <img alt="Java" src="https://img.shields.io/badge/Java-B1361E?style=for-the-badge&logo=coffeescript&logoColor=white">
  <img alt="Spring_Framework" src="https://img.shields.io/badge/Spring_Framework-6DB33F?style=for-the-badge&logo=spring&logoColor=white">
  <img alt="Spring_Boot" src="https://img.shields.io/badge/Spring_Boot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white">
  <img alt="Spring Security" src="https://img.shields.io/badge/Spring_Security-6DB33F?style=for-the-badge&logo=springsecurity&logoColor=white">
  <img alt="Jwt" src="https://img.shields.io/badge/JSON_Web_Tokens-000000?style=for-the-badge&logo=jsonwebtokens&logoColor=white">
  <img alt="MongoDB" src="https://img.shields.io/badge/MongoDB-4EA94B?style=for-the-badge&logo=mongodb&logoColor=white">
</div>

# Unifacisa - Receita Que Doi Menos 🗃️

Repositório para alocar a aplicação REST desenvolvida durante as aulas das competências <strong>[Integrar Interfaces e Serviços Web](https://github.com/pedrohpdo/integrar-interfaces-servicos-web-unifacisa)</strong> e <strong>Desenvolver Aplicativos Para Dispositivos Móveis e IOT</strong> da Unifacisa.

## Getting Started ▶️

O colaborador deverá possuir credenciais de acesso ao repositório e estar associado ao grupo do projeto.

Todos os pré-requisitos necessários para execução do projeto deverão ser devidamente instalados na máquina local do desenvolvedor.

### Pré-requisitos ⚙️

- [IntelliJ Idea](https://www.jetbrains.com/idea/)
- [JDK17](https://www.oracle.com/java/technologies/downloads/)
- [Lombok](https://projectlombok.org/)
- [MongoDB](https://www.mongodb.com/pt-br)
- [Repositório](https://github.com/pedrohpdo/receita-que-doi-menos-server)

### Protótipo ✏️

É possível acessar o Layout Mobile do projeto [clicando aqui](https://www.figma.com/file/N6568lZYNEG94JrZwgQpKL/receita-que-doi-menos?type=design&node-id=0%3A1&mode=design&t=mV2Y7Pqrp9xx7F7B-1).

Lembrando que você deve ter uma conta no [Figma](https://www.figma.com/) para ter acesso ao Layout.

### Configurando as Variáveis de Ambiente 🌐

O projeto utiliza um arquivo `.properties` para armazenar informações sensíveis, como credenciais do banco de dados e configurações de autenticação.

Caso queira executar o projeto localmente, certifique-se de criar um arquivo .properties na raiz do projeto e configurar as variáveis necessárias.

Dentro dele crie a seguintes credenciais, ou implemente seus respectivos valores:


`spring.data.mongodb.uri=${DATABASE_URL}` <br>
`api.security.token.secret=${JWT_SECRET}` <br>
`api.security.token.issuer=${ISSUER}` <br>
`api.security.token.access.duration=${ACCESS-DURATION}` <br>
`api.security.token.refresh.duration=${REFRESH-DURATION}`


## Autores 🧑‍💻

- [Darllinson Azevedo](https://github.com/darllinsonazvd)
- [Pedro Azevedo Teixeira](https://github.com/pedro-azevedo3)
- [Pedro Henrique Pereira](https://github.com/pedrohpdo)
- [Pedro Henrique Santos](https://github.com/pedrohsantosg)
- [Rafael Macedo](https://github.com/rafaelmacedos)
- [Ramon Montenegro](https://github.com/ramonmontenegropng)

