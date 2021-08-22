## Como foi feito
Foi utilizado as seguintes tecnologias: apache commons csv, sql server com jdbc, selenium.
## Como executar
Abrir o projeto no intellij e executar o arquivo Main.
Foram criadas três classes dentro do projeto sendo:
ConexaoBanco: Classe que contém a String de conexão com banco que realizar as inserções criadas pelo formulário através da classe RegistroFormulario.
RegistroFormulario: Classe criada para inserir as variáveis usada no código fonte juntamente com os métodos get e set, usadas na classe Main.
Main: Classe principal do projeto que foi criada para gerar os dados dentro do formulário solicitado pelo gestor do projeto. Essa classe preenche os elementos e pega a resposta que é retornada dentro do banco após a inserção de cada linha da coluna, que busca dentro de um arquivo CSV, também criado pelo gestor do projeto e deixado com uma das instruções a serem feitos no mesmo. Após a leitura do CSV, os dados são capturados pelos registos do formulário que adiciona todos os campos dentro de uma lista que é persistido pela classe ConexaoBanco dentro do banco de dados.
## Execução do Projeto.
Para executar o projeto com todos os recursos (inserção do banco), e necessário alterar a " String " de conexão com ip e porta 1433.
