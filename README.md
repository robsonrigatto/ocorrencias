# Endpoint da aplicação

[http://localhost:8080/ocorrencias/{expressao}]


## **1 -** Explique o porquê do modelo de arquitetura e tecnologias adotadas;

Tecnologia utilizada:
> Spring boot

Justificativa
> Estrutura de projeto que facilita na configuração, escalabilidade e construção de uma aplicação, através de um conjunto de ferramentas que fornecem ao desenvolvedor produzir soluções de forma mais eficientes e coesas. 


## **2 -** Como a solução poderia ser escalada (o que se espera da sua solução se tiver 1 milhão de arquivos);

TODO

## **3 -** Como seria o procedimento de build, deploy, etc.

O deploy da aplicação está dentro de uma imagem do docker.

> A imagem do docker está disponibilizada no docker hub [https://hub.docker.com/r/robsonrigatto/ocorrencias-image]

Caso queira fazer o build da aplicação Spring boot:

> 1 - Baixe o projeto do gitlab [http://gitlab.com.br/robsonrigatto/ocorrencias]

> 2 - Execute o comando *docker login -u robsonrigatto -p 123456* (somente pela primeira vez por sessão de usuário)

> 3 - Execute o comando *mvn clean install*

> 4 - Atualize a imagem local com *docker pull robsonrigatto/ocorrencias-image*

Para subir a aplicação é possível fazer de 2 maneiras, em formato standalone ou via docker.

# Standalone
> mvn spring-boot:run

# Docker
> docker run -p 8080:8080 -t robsonrigatto/ocorrencias-docker