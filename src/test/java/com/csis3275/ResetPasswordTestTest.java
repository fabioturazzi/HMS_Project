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
import java.net.MalformedURLException;
import java.net.URL;
public class ResetPasswordTestTest {
  private WebDriver driver;
  private Map<String, Object> vars;
  JavascriptExecutor js;
  @Before
  public void setUp() {
	System.setProperty("webdriver.chrome.driver", "c:/temp/chromedriver.exe");
    driver = new ChromeDriver();
    js = (JavascriptExecutor) driver;
    vars = new HashMap<String, Object>();
  }
  @After
  public void tearDown() {
    driver.quit();
  }
  @Test
  public void resetPasswordTest() {
    driver.get("http://localhost:8080/");
    driver.manage().window().setSize(new Dimension(1150, 754));
    driver.findElement(By.id("user")).click();
    driver.findElement(By.id("usernameForm")).sendKeys("doeJohn");
    driver.findElement(By.id("user")).click();
    driver.findElement(By.id("passwordForm")).click();
    driver.findElement(By.id("passwordForm")).sendKeys("22222222");
    driver.findElement(By.cssSelector(".btn")).click();
    driver.findElement(By.cssSelector(".navbar")).click();
    driver.findElement(By.linkText("My Profile")).click();
    driver.findElement(By.cssSelector(".btn-info")).click();
    driver.findElement(By.id("passwordForm")).click();
    driver.findElement(By.id("passwordForm")).sendKeys("22222222");
    driver.findElement(By.id("password")).click();
    driver.findElement(By.id("password")).sendKeys("11111111");
    driver.findElement(By.id("confPassword")).click();
    driver.findElement(By.id("confPassword")).sendKeys("11111111");
    driver.findElement(By.cssSelector(".btn")).click();
    driver.findElement(By.cssSelector(".alert")).click();
    driver.findElement(By.linkText("Logout")).click();
    driver.findElement(By.id("usernameForm")).click();
    driver.findElement(By.id("usernameForm")).sendKeys("doeJohn");
    driver.findElement(By.id("passwordForm")).click();
    driver.findElement(By.id("passwordForm")).sendKeys("11111111");
    driver.findElement(By.cssSelector(".btn")).click();
    driver.findElement(By.xpath("//h1[@id=\'pageTitle\']")).click();
    assertEquals("Room Search", driver.findElement(By.xpath("//h1[@id=\'pageTitle\']")).getText());
  }
}