use [rpa-challenge]

create table Pessoa(
	Id int IDENTITY(1,1) primary key,
	Nome VARCHAR(300),
	Cidade VARCHAR(300),
	Estado VARCHAR(150)
);

create table Contato (
	Id int IDENTITY(1,1) Primary Key,
	Idpessoa INT FOREIGN KEY references Pessoa(id),
	Email VARCHAR(150),
	DDD VARCHAR(3),
	Telefone VARCHAR(15)
);

create table StatusMensagemEnviada(
	Id int IDENTITY(1,1) Primary Key,
	IdPessoa INT references Pessoa(Id),
	IdContato INT FOREIGN KEY references Contato(Id),
	Assunto VARCHAR(1000),
	Mensagem VARCHAR (max),
	RetornoSite VARCHAR (max)
);
