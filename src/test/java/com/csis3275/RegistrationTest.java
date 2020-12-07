package com.csis3275;

// Generated by Selenium IDE
import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsNot.not;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Alert;
import org.openqa.selenium.Keys;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.net.MalformedURLException;
import java.net.URL;
public class RegistrationTest {
  private WebDriver driver;
  private Map<String, Object> vars;
  JavascriptExecutor js;
  
  @Before
  public void setUp() {
		System.setProperty("webdriver.chrome.driver", "C:/temp/chromedriver.exe");
		driver = new ChromeDriver();
		js = (JavascriptExecutor) driver;
		vars = new HashMap<String, Object>();

  }
  @After
  public void tearDown() {
    driver.quit();
  }
//This test register a new user and asserts if registration was succesfull
  @Test
  public void registrationTest() {
    driver.get("http://localhost:8080/");
    driver.manage().window().setSize(new Dimension(1900, 1020));
    driver.findElement(By.cssSelector(".form-group:nth-child(6) > .col-md-offset-3")).click();
    driver.findElement(By.id("fName")).click();
    driver.findElement(By.id("fName")).sendKeys("Fabio");
    driver.findElement(By.id("lName")).sendKeys("Turazzi");
    driver.findElement(By.id("username")).sendKeys("fabioturazzi");
    driver.findElement(By.id("password")).sendKeys("11111111");
    driver.findElement(By.id("confPassword")).click();
    driver.findElement(By.id("confPassword")).sendKeys("11111111");
    driver.findElement(By.id("email")).click();
    driver.findElement(By.id("email")).sendKeys("fabio@email.com");
    driver.findElement(By.id("address")).click();
    driver.findElement(By.id("address")).sendKeys("Address");
    driver.findElement(By.id("phoneNumber")).click();
    driver.findElement(By.id("phoneNumber")).sendKeys("Phone");
    driver.findElement(By.id("passQuestion")).sendKeys("First dog?");
    driver.findElement(By.id("passAnswer")).sendKeys("Toquinho");
    driver.findElement(By.cssSelector(".form-group:nth-child(11) > .col-md-3")).click();
    driver.findElement(By.cssSelector(".btn")).click();
    driver.findElement(By.id("pageTitle")).click();
    
    //Asserts that the registration was successful
	assertThat(driver.findElement(By.id("pageTitle")).getText(), is("Successful Registration"));
  }
}
