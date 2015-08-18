package com.ecofactor.comcastUI;

import java.util.concurrent.TimeUnit;

import org.junit.*;

import static org.junit.Assert.*;

import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;

import com.ecofactorqa.dao.Thermostat_State_DAO_Impl;

public class test {
  private WebDriver driver;
  private String baseUrl;
  private StringBuffer verificationErrors = new StringBuffer();
  final int t_id = 24818;


  @Before
  public void setUp() throws Exception {
    driver = new FirefoxDriver();
    baseUrl = "https://sp03.qa.xfinityhome.com/sp";
    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
  }

  @Test
  public void moDetection() throws Exception {
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
    System.out.println("my setpoint is " + setpoint + setpointDecimal);
    driver.findElement(By.cssSelector("area.minusArrow")).click();
    driver.findElement(By.cssSelector("area.minusArrow")).click();
    driver.findElement(By.cssSelector("area.minusArrow")).click();
    driver.findElement(By.cssSelector("area.minusArrow")).click();
    driver.findElement(By.cssSelector("area.minusArrow")).click();
    driver.findElement(By.cssSelector("area.minusArrow")).click();
    driver.findElement(By.cssSelector("area.minusArrow")).click();
    driver.findElement(By.cssSelector("area.minusArrow")).click();
    Thread.sleep(15000);
    String setpoint1 = driver.findElement(By.id("multiThermoTempDecimal_0")).getText();
    String setpointDecimal1 = driver.findElement(By.id("multiThermoTempFractional_0")).getText();
    System.out.println("my NEW setpoint is " + setpoint1 + setpointDecimal1);
    
    Thread.sleep(15000);
	Thermostat_State_DAO_Impl.moForComcastUserThermostatID();
	Assert.assertEquals(Thermostat_State_DAO_Impl.coolHVACModeComcast, t_id);
	Thermostat_State_DAO_Impl.moForComcastUserSetpoint();
	//new setpoint string
	String concatNewSetpoint = new StringBuilder().append(setpoint1).append(setpointDecimal1).toString();
	
	System.out.println("concat value " + concatNewSetpoint);
	//convert string to int
	int newUISetpoint = (int) Double.parseDouble(concatNewSetpoint);
	System.out.println(newUISetpoint);
	//rounding new setpoint
	int newUISetpointRounded = (int) Math.round(newUISetpoint);
	System.out.println(newUISetpoint);
	System.out.println(newUISetpointRounded);
	Assert.assertEquals(Thermostat_State_DAO_Impl.coolHVACModeComcastSetpointRounded, newUISetpointRounded);

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
