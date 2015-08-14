package com.ecofactor.comcastUI;

import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;

import org.junit.*;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

public class test {
  private WebDriver driver;
  private String baseUrl;
  private boolean acceptNextAlert = true;
  private StringBuffer verificationErrors = new StringBuffer();

  @Before
  public void setUp() throws Exception {
    driver = new FirefoxDriver();
    baseUrl = "https://sp03.qa.xfinityhome.com/sp";
    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
  }

  @Test
  public void testQqq() throws Exception {
    driver.get(baseUrl);
    driver.findElement(By.id("passwd")).clear();
    driver.findElement(By.id("passwd")).sendKeys("CakeRamp1");
    driver.findElement(By.id("user")).clear();
    driver.findElement(By.id("user")).sendKeys("xheco16@comcast.net");
    driver.findElement(By.id("sign_in")).click();
    Thread.sleep(2000);
    driver.findElement(By.xpath("//button[text() = 'OK']")).click();
    driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    driver.findElement(By.xpath("//button[text() = 'Devices']")).click();
    Thread.sleep(2000);
    driver.findElement(By.className("x-menu-item-text")).click();
    String setpoint = driver.findElement(By.id("multiThermoTempDecimal_0")).getText();
    String setpointDecimal = driver.findElement(By.id("multiThermoTempFractional_0")).getText();
    System.out.println("my setpoint is" + setpoint + setpointDecimal);
    driver.findElement(By.cssSelector("area.minusArrow")).click();
    driver.findElement(By.cssSelector("area.minusArrow")).click();
    driver.findElement(By.cssSelector("area.minusArrow")).click();
    driver.findElement(By.cssSelector("area.minusArrow")).click();
    driver.findElement(By.cssSelector("area.minusArrow")).click();
    driver.findElement(By.cssSelector("area.minusArrow")).click();
    driver.findElement(By.cssSelector("area.minusArrow")).click();
    driver.findElement(By.cssSelector("area.minusArrow")).click();
    Thread.sleep(10000);
    String setpoint1 = driver.findElement(By.id("multiThermoTempDecimal_0")).getText();
    String setpointDecimal1 = driver.findElement(By.id("multiThermoTempFractional_0")).getText();
    System.out.println("my NEW setpoint is" + setpoint1 + setpointDecimal1);

  }

  @After
  public void tearDown() throws Exception {
    driver.quit();
    String verificationErrorString = verificationErrors.toString();
    if (!"".equals(verificationErrorString)) {
      fail(verificationErrorString);
    }
  }
}
