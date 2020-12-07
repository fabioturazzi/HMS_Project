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
public class RoomManagementTest {
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
//This test edits information from a room and verifies if the new information was updated
  @Test
  public void roomManagement() {
    driver.get("http://localhost:8080/");
    driver.manage().window().setSize(new Dimension(1900, 1020));
    driver.findElement(By.id("usernameForm")).click();
    driver.findElement(By.id("usernameForm")).sendKeys("admin");
    driver.findElement(By.id("passwordForm")).sendKeys("admin");
    driver.findElement(By.id("passwordForm")).sendKeys(Keys.ENTER);
    driver.findElement(By.cssSelector(".roomNav1 > a")).click();
    driver.findElement(By.linkText("Edit")).click();
    driver.findElement(By.id("housekeepingStatus")).click();
    driver.findElement(By.id("housekeepingStatus")).click();
    driver.findElement(By.id("floor")).clear();
    driver.findElement(By.id("floor")).sendKeys("2");
    driver.findElement(By.id("floor")).click();
    driver.findElement(By.cssSelector(".form-group:nth-child(2)")).click();
    driver.findElement(By.id("roomNumber")).clear();
    driver.findElement(By.id("roomNumber")).sendKeys("210");
    driver.findElement(By.id("roomType")).click();

    driver.findElement(By.id("roomType")).click();
    driver.findElement(By.cssSelector(".btn")).click();
    driver.findElement(By.cssSelector(".dataRows:nth-child(2) > td:nth-child(2)")).click();
    driver.findElement(By.cssSelector(".dataRows:nth-child(2) > td:nth-child(4)")).click();
    
    //Asserts that new room number is updated
	assertThat(driver.findElement(By.xpath("/html/body/div[2]/table/tbody/tr[2]/td[2]")).getText(), is("210"));

    //Asserts that new floor is updated
	assertThat(driver.findElement(By.xpath("/html/body/div[2]/table/tbody/tr[2]/td[4]")).getText(), is("2"));
  }
}
