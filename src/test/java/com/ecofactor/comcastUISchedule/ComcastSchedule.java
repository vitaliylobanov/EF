package com.ecofactor.comcastUISchedule;

import java.util.concurrent.TimeUnit;

import org.junit.*;

import static org.junit.Assert.*;

import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;

import com.ecofactorqa.dao.Comcast_Schedule_DAO_Impl;

public class ComcastSchedule {
  private WebDriver driver;
  private String baseUrl;
  private StringBuffer verificationErrors = new StringBuffer();
  final int t_id = 24818;
  final int numberOfScheduleRowsSimpleModeTwoTstats = 14;
  final int numberOfScheduleRowsCustomModeModeTwoTstats = 70;



  @Before
  public void setUp() throws Exception {
    driver = new FirefoxDriver();
    baseUrl = "https://sp03.qa.xfinityhome.com/sp";
    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
  }

  @Test
  public void comcastScheduleSetSimpleModeByDisableSchedule() throws Exception {
    driver.get(baseUrl);
    driver.findElement(By.id("passwd")).clear();
    driver.findElement(By.id("passwd")).sendKeys("CakeRamp1");
    driver.findElement(By.id("user")).clear();
    driver.findElement(By.id("user")).sendKeys("xheco16@comcast.net");
    driver.findElement(By.id("sign_in")).click();
    Thread.sleep(2000);
    driver.findElement(By.xpath("//button[text() = 'OK']")).click();
    driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    driver.findElement(By.xpath("//button[text() = 'Rules']")).click();
    Thread.sleep(2000);
    driver.findElement(By.linkText("Rules")).click();
    Thread.sleep(5000);
    driver.findElement(By.xpath("//div[4]/table/tbody/tr/td[13]/div/div/div[3]")).click();
    Thread.sleep(5000);
    driver.findElement(By.xpath("//div[5]/table/tbody/tr/td[13]/div/div/div[3]")).click();
    Thread.sleep(10000);
    Comcast_Schedule_DAO_Impl.comcastSchedule();
	Assert.assertEquals(Comcast_Schedule_DAO_Impl.numberOfActiveRowsForSchedule, numberOfScheduleRowsSimpleModeTwoTstats);
  }
  
  @Test
  public void comcastScheduleSetCustomModeSchedule() throws Exception {
    driver.get(baseUrl);
    driver.findElement(By.id("passwd")).clear();
    driver.findElement(By.id("passwd")).sendKeys("CakeRamp1");
    driver.findElement(By.id("user")).clear();
    driver.findElement(By.id("user")).sendKeys("xheco16@comcast.net");
    driver.findElement(By.id("sign_in")).click();
    Thread.sleep(2000);
    driver.findElement(By.xpath("//button[text() = 'OK']")).click();
    driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    driver.findElement(By.xpath("//button[text() = 'Rules']")).click();
    Thread.sleep(2000);
    driver.findElement(By.linkText("Rules")).click();
    Thread.sleep(5000);
    driver.findElement(By.xpath("//div[4]/table/tbody/tr/td[13]/div/div/div[3]")).click();
    Thread.sleep(5000);
    driver.findElement(By.xpath("//div[5]/table/tbody/tr/td[13]/div/div/div[3]")).click();
    Thread.sleep(20000);
    Comcast_Schedule_DAO_Impl.comcastSchedule();
	Assert.assertEquals(Comcast_Schedule_DAO_Impl.numberOfActiveRowsForSchedule, numberOfScheduleRowsCustomModeModeTwoTstats);
  }
 
  @Test
  public void comcastScheduleSetSimpleModeByDeletingSchecule() throws Exception {
    driver.get(baseUrl);
    driver.findElement(By.id("passwd")).clear();
    driver.findElement(By.id("passwd")).sendKeys("CakeRamp1");
    driver.findElement(By.id("user")).clear();
    driver.findElement(By.id("user")).sendKeys("xheco16@comcast.net");
    driver.findElement(By.id("sign_in")).click();
    Thread.sleep(2000);
    driver.findElement(By.xpath("//button[text() = 'OK']")).click();
    driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    driver.findElement(By.xpath("//button[text() = 'Rules']")).click();
    Thread.sleep(2000);
    driver.findElement(By.linkText("Rules")).click();
    Thread.sleep(5000);
    driver.findElement(By.xpath("//div[4]/table/tbody/tr/td[13]/div/div/div[2]/img")).click();
    driver.findElement(By.xpath("//button[text() = 'Yes']")).click();
    Thread.sleep(5000);
    driver.findElement(By.xpath("//div[4]/table/tbody/tr/td[13]/div/div/div[2]/img")).click();
    driver.findElement(By.xpath("//button[text() = 'Yes']")).click();
    Thread.sleep(10000);
    Comcast_Schedule_DAO_Impl.comcastSchedule();
	Assert.assertEquals(Comcast_Schedule_DAO_Impl.numberOfActiveRowsForSchedule, numberOfScheduleRowsSimpleModeTwoTstats);
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
