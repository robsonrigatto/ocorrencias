# Endpoint da aplicação

[http://localhost:8080/ocorrencias/{expressao}]


## **1 -** Explique o porquê do modelo de arquitetura e tecnologias adotadas;

Tecnologia utilizada:
> Spring boot

Justificativa
> Estrutura de projeto que facilita na configuração, escalabilidade e construção de uma aplicação, através de um conjunto de ferramentas que fornecem ao desenvolvedor produzir soluções de forma mais eficientes e coesas. 


## **2 -** Como a solução poderia ser escalada (o que se espera da sua solução se tiver 1 milhão de arquivos);

A solução atual permite o paralelismo de acordo com o número fixo de arquivos (7).

Cada arquivo é distribuido para, inicialmente, 1 núcleo do processador. No entanto, a varredura nos arquivos é feita de forma paralela, assim, otimizando os recursos da máquina alvo.

Uma possibilidade de implementação é a distribuição dos arquivos de forma equivalente, através da relação qtde de núcleos x qtde de arquivos x tamanho dos arquivos.

## **3 -** Como seria o procedimento de build, deploy, etc.

O deploy da aplicação está dentro de uma imagem do docker.

> A imagem do docker está disponibilizada no docker hub [https://hub.docker.com/r/robsonrigatto/ocorrencias-image]

Caso queira fazer o build da aplicação Spring boot:

> 1 - Baixe o projeto do gitlab [http://gitlab.com.br/robsonrigatto/ocorrencias]

> 2 - Execute o comando *docker login -u robsonrigatto -p 123456* (somente pela primeira vez por sessão de usuário)

> 3 - Execute o comando *mvn clean install*

> 4 - Atualize a imagem local com *docker pull robsonrigatto/ocorrencias-image*

Antes de subir a aplicação, é necessário criar um diretório que contém os arquivos (nesse caso, coloquei em */tmp/files/*)

Para subir a aplicação é possível fazer de 2 maneiras, em formato standalone ou via docker.

# Standalone
> mvn spring-boot:run

# Docker
> docker run -p 8080:8080 -v /tmp/files:/tmp/files -t robsonrigatto/ocorrencias-image