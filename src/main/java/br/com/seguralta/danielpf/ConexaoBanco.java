package br.com.seguralta.danielpf;

import net.bytebuddy.dynamic.scaffold.MethodRegistry;

import java.sql.*;

public class ConexaoBanco {
    private final Connection connection;

    public ConexaoBanco() throws ClassNotFoundException, SQLException {
        String connectionUrl = "jdbc:sqlserver://192.168.15.3;user=sa;password=5550123Dgk;databaseName=rpa-challenge";
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        // Abre conexão com banco
        this.connection = DriverManager.getConnection(connectionUrl);
    }

    public void persistirDados(RegistroFormulario registroFormulario, String retornoSite) throws ClassNotFoundException, SQLException {
        // Utiliza prepared statement para sanitizar parâmetros
        PreparedStatement stmtInsertPessoa = connection.prepareStatement("insert into Pessoa(nome, cidade, estado) values (?, ?, ?)");
        stmtInsertPessoa.setString(1, registroFormulario.getNomeCompleto());
        stmtInsertPessoa.setString(2, registroFormulario.getCidade());
        stmtInsertPessoa.setString(3, registroFormulario.getEstado());
        stmtInsertPessoa.execute();

        ResultSet rsUltimoIdPessoa = connection.createStatement().executeQuery("select max(id) from pessoa");
        rsUltimoIdPessoa.next();
        Long ultimoIdPessoa = rsUltimoIdPessoa.getLong(1);

        String telefoneSanitizado = registroFormulario.getTelefone().replace("(", "").replace(")", "");

        PreparedStatement stmtInsertContato = connection.prepareStatement("insert into contato(idpessoa, email, ddd, telefone) values (" +
                "?, ?, ?, ?)");
        stmtInsertContato.setLong(1, ultimoIdPessoa);
        stmtInsertContato.setString(2, registroFormulario.getEmail());
        stmtInsertContato.setString(3, telefoneSanitizado.substring(0, 2));
        stmtInsertContato.setString(4, telefoneSanitizado.substring(2));
        stmtInsertContato.execute();

        ResultSet rsUltimoIdContato = connection.createStatement().executeQuery("select max(id) from contato");
        rsUltimoIdContato.next();
        Long ultimoIdContato = rsUltimoIdContato.getLong(1);

        PreparedStatement stmtInsertMensagemEnviada = connection.prepareStatement("insert into statusmensagemenviada(idpessoa," +
                " idcontato, assunto, mensagem, retornosite) values (?, ?, ?, ?, ?)");
        stmtInsertMensagemEnviada.setLong(1, ultimoIdPessoa);
        stmtInsertMensagemEnviada.setLong(2, ultimoIdContato);
        stmtInsertMensagemEnviada.setString(3, registroFormulario.getAssunto());
        stmtInsertMensagemEnviada.setString(4, registroFormulario.getMensagem());
        stmtInsertMensagemEnviada.setString(5, retornoSite);
        stmtInsertMensagemEnviada.execute();
    }
}
