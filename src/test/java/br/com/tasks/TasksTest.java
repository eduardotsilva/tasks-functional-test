package br.com.tasks;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

public class TasksTest {

    public WebDriver acessarAplicacao() throws MalformedURLException {
        //navegando até aplicação utilizando chrome driver
        //WebDriver driver = new ChromeDriver();
        //driver.navigate().to("http://localhost:8001/tasks");
        //driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

        //Utilizando Selenium Grid (hub e nodes)
        //Foi necessário colocar IP invés de usar Localhost,
        //pois os testes serão executados dentro do container (acesso será Container -> Máquina)
        DesiredCapabilities cap = DesiredCapabilities.chrome();
        WebDriver driver = new RemoteWebDriver( new URL("http://192.168.0.88:4444/wd/hub"), cap);
        driver.navigate().to("http://192.168.0.88:8001/tasks");
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        return driver;
    }

    @Test
    public void deveSalvarTarefaComSucesso() throws MalformedURLException {
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
    public void naoDeveSalvarTarefaSemDescricao() throws MalformedURLException {
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
    public void naoDeveSalvarTarefaDataPassada() throws MalformedURLException {
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
    public void naoDeveSalvarTarefaSemData() throws MalformedURLException {
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
