package br.com.seguralta.danielpf;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static String preencherFormulario(RegistroFormulario registro) {
        //Inicializa o firefox
        WebDriver driver = new FirefoxDriver();
        String retornoSite = "";

        try {
            driver.get("https://seguralta.com.br/site/contato");

            //Preenche os elementos
            WebElement nome = driver.findElement(By.id("name"));
            nome.sendKeys(registro.getNomeCompleto());

            WebElement email = driver.findElement(By.id("email"));
            email.sendKeys(registro.getEmail());

            WebElement cep = driver.findElement(By.id("cep"));
            cep.sendKeys(registro.getCep());

            Select estado = new Select(driver.findElement(By.id("estado")));
            estado.selectByVisibleText(registro.getEstado());

            Select cidade = new Select(driver.findElement(By.id("cidade")));
            cidade.selectByVisibleText(registro.getCidade());

            WebElement assunto = driver.findElement(By.id("assunto"));
            assunto.sendKeys(registro.getAssunto());

            WebElement telefone = driver.findElement(By.id("telefone"));
            telefone.sendKeys(registro.getTelefone());

            WebElement mensagem = driver.findElement(By.id("mensagem"));
            mensagem.sendKeys(registro.getMensagem());

            // Submete o formulário
            WebElement enviarMensagem = driver.findElement(By.cssSelector(".btn.btn-success.btn-orange.btn-md.pull-right"));
            enviarMensagem.click();

            // Pega a resposta
            WebElement elementoResposta = driver.findElement(By.id("contactError"));
            retornoSite = elementoResposta.getText();
        } catch(Exception e) {
            retornoSite = e.getMessage();
        } finally {
            // Fecha o firefox caso dê erro ou não
            driver.close();
        }

        return retornoSite;
    }

    public static List<RegistroFormulario> transformarRegistroFormulario() throws IOException {
        List<RegistroFormulario> registros = new ArrayList<>();

        // Le o arquivo csv
        Reader in = new FileReader("Contatos.csv");

        Iterable<CSVRecord> records = CSVFormat.DEFAULT
                .withHeader("Nome", "Cidade", "Estado", "CEP", "Email", "Telefone", "Assunto", "Mensagem")
                .withDelimiter(';')
                .withFirstRecordAsHeader()
                .parse(in);

        for (CSVRecord record : records) {
            RegistroFormulario registro = new RegistroFormulario();

            registro.setNomeCompleto(record.get("Nome"));
            registro.setCidade(record.get("Cidade"));
            registro.setEstado(record.get("Estado"));
            registro.setCep(record.get("CEP"));
            registro.setEmail(record.get("Email"));
            registro.setTelefone(record.get("Telefone"));
            registro.setAssunto(record.get("Assunto"));
            registro.setMensagem(record.get("Mensagem"));

            //Adiciona o registro na lista de registros
            registros.add(registro);
        }

        return registros;
    }

    public static void main(String... args) throws Exception {
        List<RegistroFormulario> registros = transformarRegistroFormulario();
        ConexaoBanco con = new ConexaoBanco();

        for(RegistroFormulario registro : registros) {
            String retornoSite = preencherFormulario(registro);
            con.persistirDados(registro, retornoSite);
        }
    }
}
