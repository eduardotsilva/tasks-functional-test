package br.com.tasks;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

public class TasksTest {

    public WebDriver acessarAplicacao(){
        //navega até aplicação
        WebDriver driver = new ChromeDriver();
        driver.navigate().to("http://localhost:8001/tasks");
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

        return driver;
    }

    @Test
    public void deveSalvarTarefaComSucesso(){
        WebDriver driver = acessarAplicacao();

        try {
        //clicar em addTodo
        driver.findElement(By.id("addTodo")).click();

        //escrever descricao
        driver.findElement(By.id("task")).sendKeys("Teste via Selenium");

        //escrever a data
        String strNow = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        driver.findElement(By.id("dueDate")).sendKeys(strNow);

        //clicar em salvar
        driver.findElement(By.id("saveButton")).click();

        //validar msg de sucesso
        String message = driver.findElement(By.id("message")).getText();

        Assert.assertEquals("Success!", message);
        } finally {
           driver.quit();
        }

    }

    @Test
    public void naoDeveSalvarTarefaSemDescricao(){
        WebDriver driver = acessarAplicacao();

        try {
            //clicar em addTodo
            driver.findElement(By.id("addTodo")).click();

            //escrever a data
            String strNow = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            driver.findElement(By.id("dueDate")).sendKeys(strNow);

            //clicar em salvar
            driver.findElement(By.id("saveButton")).click();

            //validar msg de sucesso
            String message = driver.findElement(By.id("message")).getText();

            Assert.assertEquals("Fill the task description", message);
        } finally {
            driver.quit();
        }
    }

    @Test
    public void naoDeveSalvarTarefaDataPassada(){
        WebDriver driver = acessarAplicacao();

        try {
            //clicar em addTodo
            driver.findElement(By.id("addTodo")).click();

            //escrever descricao
            driver.findElement(By.id("task")).sendKeys("Teste via Selenium");

            //escrever a data
            String strNow = "10/10/2010";
            driver.findElement(By.id("dueDate")).sendKeys(strNow);

            //clicar em salvar
            driver.findElement(By.id("saveButton")).click();

            //validar msg de sucesso
            String message = driver.findElement(By.id("message")).getText();

            Assert.assertEquals("Due date must not be in past", message);
        } finally {
            driver.quit();
        }
    }

    @Test
    public void naoDeveSalvarTarefaSemData(){
        WebDriver driver = acessarAplicacao();

        try {
            //clicar em addTodo
            driver.findElement(By.id("addTodo")).click();

            //escrever descricao
            driver.findElement(By.id("task")).sendKeys("Teste via Selenium");

            //clicar em salvar
            driver.findElement(By.id("saveButton")).click();

            //validar msg de sucesso
            String message = driver.findElement(By.id("message")).getText();

            Assert.assertEquals("Fill the due date", message);
        } finally {
            driver.quit();
        }
    }

}
