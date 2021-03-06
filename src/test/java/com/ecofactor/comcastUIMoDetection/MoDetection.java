package com.ecofactor.comcastUIMoDetection;

import java.util.concurrent.TimeUnit;

import org.junit.*;

import static org.junit.Assert.*;

import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;

import com.ecofactorqa.dao.MO_Detection_DAO_Impl;
import com.ecofactorqa.util.PlatDataProp;

public class MoDetection {
  private WebDriver driver;
  private String baseUrl;
  private StringBuffer verificationErrors = new StringBuffer();
  final int t_id = PlatDataProp.MODetectionAppsComcastUIThermostatId;
  final String userPass= PlatDataProp.ComcastUIUserPassSP;
  final String userEmail= PlatDataProp.ComcastUIUserEmailSP;


  @Before
  public void setUp() throws Exception {
    driver = new FirefoxDriver();
    baseUrl = PlatDataProp.ComcastEnv03SPUrl;
    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    driver.get(baseUrl);
    driver.findElement(By.id("passwd")).clear();
    driver.findElement(By.id("passwd")).sendKeys(userPass);
    driver.findElement(By.id("user")).clear();
    driver.findElement(By.id("user")).sendKeys(userEmail);
    driver.findElement(By.id("sign_in")).click();
    Thread.sleep(2000);
  }

  @Test
  public void moDetectionCoolSetpointDown() throws Exception {
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
    Thread.sleep(22000);
    String setpoint1 = driver.findElement(By.id("multiThermoTempDecimal_0")).getText();
    String setpointDecimal1 = driver.findElement(By.id("multiThermoTempFractional_0")).getText();
    System.out.println("my NEW setpoint is " + setpoint1 + setpointDecimal1);
    
    Thread.sleep(15000);
	MO_Detection_DAO_Impl.moForComcastUserThermostatID();
	Assert.assertEquals(MO_Detection_DAO_Impl.coolHVACModeComcast, t_id);
	MO_Detection_DAO_Impl.moForComcastUserSetpoint();
	//new setpoint string
	String concatNewSetpoint = new StringBuilder().append(setpoint1).append(setpointDecimal1).toString();
	
	System.out.println("concat value " + concatNewSetpoint);
	//convert string to int
	double newUISetpoint = (int) Double.parseDouble(concatNewSetpoint);
	//rounding new setpoint
	int newUISetpointRounded = (int) Math.round(newUISetpoint);
	System.out.println("concat ui setpoint " + newUISetpoint);
	System.out.println("rounded ui setpint " + newUISetpointRounded);
	Assert.assertEquals(MO_Detection_DAO_Impl.coolHVACModeComcastSetpointRounded, newUISetpointRounded);

  }
  
  @Test
  public void moDetectionCoolSetpointUp() throws Exception {
    driver.findElement(By.xpath("//button[text() = 'OK']")).click();
    driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    driver.findElement(By.xpath("//button[text() = 'Devices']")).click();
    Thread.sleep(2000);
    driver.findElement(By.className("x-menu-item-text")).click();
    String setpoint = driver.findElement(By.id("multiThermoTempDecimal_0")).getText();
    String setpointDecimal = driver.findElement(By.id("multiThermoTempFractional_0")).getText();
    System.out.println("my setpoint is " + setpoint + setpointDecimal);
    driver.findElement(By.cssSelector("area.plusArrow")).click();
    driver.findElement(By.cssSelector("area.plusArrow")).click();
    driver.findElement(By.cssSelector("area.plusArrow")).click();
    driver.findElement(By.cssSelector("area.plusArrow")).click();
    Thread.sleep(20000);
    String setpoint1 = driver.findElement(By.id("multiThermoTempDecimal_0")).getText();
    String setpointDecimal1 = driver.findElement(By.id("multiThermoTempFractional_0")).getText();
    System.out.println("my NEW setpoint is " + setpoint1 + setpointDecimal1);
    
    Thread.sleep(15000);
	MO_Detection_DAO_Impl.moForComcastUserThermostatID();
	Assert.assertEquals(MO_Detection_DAO_Impl.coolHVACModeComcast, t_id);
	MO_Detection_DAO_Impl.moForComcastUserSetpoint();
	//new setpoint string
	String concatNewSetpoint = new StringBuilder().append(setpoint1).append(setpointDecimal1).toString();
	
	System.out.println("concat value " + concatNewSetpoint);
	//convert string to int
	double newUISetpoint = (int) Double.parseDouble(concatNewSetpoint);
	//rounding new setpoint
	int newUISetpointRounded = (int) Math.round(newUISetpoint);
	System.out.println("concat ui setpoint " + newUISetpoint);
	System.out.println("rounded ui setpint " + newUISetpointRounded);
	Assert.assertEquals(MO_Detection_DAO_Impl.coolHVACModeComcastSetpointRounded, newUISetpointRounded);

  }
  
  @Test
  public void moDetectionCoolModeTurnONHoldMode() throws Exception {
    driver.findElement(By.xpath("//button[text() = 'OK']")).click();
    driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    driver.findElement(By.xpath("//button[text() = 'Devices']")).click();
    Thread.sleep(2000);
    driver.findElement(By.className("x-menu-item-text")).click();
    driver.findElement(By.id("multiThermoSettingDiv_0")).click();
    Thread.sleep(2000);
    driver.findElement(By.id("holdOnOffBtn")).click();
    Thread.sleep(30000);
    MO_Detection_DAO_Impl.moForComcastUserPermModeOn();
	Assert.assertEquals(MO_Detection_DAO_Impl.coolHVACModeComcastPermModeOn, t_id);


  }
  
  @Test
  public void moDetectionCoolModeTurnOFFHoldMode() throws Exception {
    Thread.sleep(2000);
    driver.findElement(By.xpath("//button[text() = 'OK']")).click();
    driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    driver.findElement(By.xpath("//button[text() = 'Devices']")).click();
    Thread.sleep(2000);
    driver.findElement(By.className("x-menu-item-text")).click();
    driver.findElement(By.id("multiThermoSettingDiv_0")).click();
    Thread.sleep(2000);
    driver.findElement(By.id("holdOnOffBtn")).click();
    Thread.sleep(30000);
    MO_Detection_DAO_Impl.moForComcastUserPermModeOff();
	Assert.assertEquals(MO_Detection_DAO_Impl.coolHVACModeComcastPermModeOff, t_id);

  }
  
  @Test
  public void moDetectionCoolModeMoWithPermMode() throws Exception {
    driver.findElement(By.xpath("//button[text() = 'OK']")).click();
    driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    driver.findElement(By.xpath("//button[text() = 'Devices']")).click();
    Thread.sleep(2000);
    driver.findElement(By.className("x-menu-item-text")).click();
    Thread.sleep(2000);
    driver.findElement(By.id("multiThermoSettingDiv_0")).click();
    Thread.sleep(2000);
    driver.findElement(By.id("holdOnOffBtn")).click();
    Thread.sleep(10000);
    String setpoint = driver.findElement(By.id("tempDecimalDiv")).getText();
    String setpointDecimal = driver.findElement(By.id("tempFractionalDiv")).getText();
    System.out.println("my setpoint is " + setpoint + setpointDecimal);
    driver.findElement(By.cssSelector("map[name=\"minusmap\"] > area.minusArrow")).click();
    driver.findElement(By.cssSelector("map[name=\"minusmap\"] > area.minusArrow")).click();
    driver.findElement(By.cssSelector("map[name=\"minusmap\"] > area.minusArrow")).click();
    driver.findElement(By.cssSelector("map[name=\"minusmap\"] > area.minusArrow")).click();
    driver.findElement(By.cssSelector("map[name=\"minusmap\"] > area.minusArrow")).click();
    driver.findElement(By.cssSelector("map[name=\"minusmap\"] > area.minusArrow")).click();
    Thread.sleep(15000);
    String setpoint1 = driver.findElement(By.id("tempDecimalDiv")).getText();
    String setpointDecimal1 = driver.findElement(By.id("tempFractionalDiv")).getText();
    System.out.println("my NEW setpoint is " + setpoint1 + setpointDecimal1);
    
    Thread.sleep(15000);
	MO_Detection_DAO_Impl.moForComcastUserThermostatIDPermOn();
	Assert.assertEquals(MO_Detection_DAO_Impl.coolHVACModeComcastPermOn, t_id);
	MO_Detection_DAO_Impl.moForComcastUserSetpointPermOn();
	//new setpoint string
	String concatNewSetpoint = new StringBuilder().append(setpoint1).append(setpointDecimal1).toString();
	
	System.out.println("concat value " + concatNewSetpoint);
	//convert string to int
	double newUISetpoint = (int) Double.parseDouble(concatNewSetpoint);
	//rounding new setpoint
	int newUISetpointRounded = (int) Math.round(newUISetpoint);
	System.out.println("concat ui setpoint " + newUISetpoint);
	System.out.println("rounded ui setpint " + newUISetpointRounded);
	Assert.assertEquals(MO_Detection_DAO_Impl.coolHVACModeComcastSetpointRoundedPermOn, newUISetpointRounded);

  }
  
  @Test
  public void moDetectionCoolModeFanAuto() throws Exception {
    driver.findElement(By.xpath("//button[text() = 'OK']")).click();
    driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    driver.findElement(By.xpath("//button[text() = 'Devices']")).click();
    Thread.sleep(2000);
    driver.findElement(By.className("x-menu-item-text")).click();
    driver.findElement(By.id("multiThermoSettingDiv_0")).click();
    Thread.sleep(2000);
    driver.findElement(By.id("fanOnOffBtn")).click();
    Thread.sleep(60000);
    MO_Detection_DAO_Impl.moForComcastUserSetpointFanModeAuto();
	Assert.assertEquals(MO_Detection_DAO_Impl.coolHVACModeComcastSetpointFanAuto, t_id);

  }
  
  @Test
  public void moDetectionCoolModeFanOff() throws Exception {
    driver.findElement(By.xpath("//button[text() = 'OK']")).click();
    driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    driver.findElement(By.xpath("//button[text() = 'Devices']")).click();
    Thread.sleep(2000);
    driver.findElement(By.className("x-menu-item-text")).click();
    driver.findElement(By.id("multiThermoSettingDiv_0")).click();
    Thread.sleep(2000);
    driver.findElement(By.id("fanOnOffBtn")).click();
    Thread.sleep(60000);
    MO_Detection_DAO_Impl.moForComcastUserSetpointFanModeOff();
	Assert.assertEquals(MO_Detection_DAO_Impl.coolHVACModeComcastSetpointFanOff, t_id);

  }
  
  @Test
  public void moDetectionChangeHVACToHEAT() throws Exception {
    driver.findElement(By.xpath("//button[text() = 'OK']")).click();
    driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    driver.findElement(By.xpath("//button[text() = 'Devices']")).click();
    Thread.sleep(2000);
    driver.findElement(By.className("x-menu-item-text")).click();
    driver.findElement(By.id("multiThermoSettingDiv_0")).click();
    Thread.sleep(2000);
    driver.findElement(By.id("heatBtn")).click();
    Thread.sleep(20000);
    MO_Detection_DAO_Impl.moForComcastUserHVACHeat();
	Assert.assertEquals(MO_Detection_DAO_Impl.coolHVACModeComcastHeat, t_id);
  }
    
  @Test
  public void moDetectionHeatSetpointUp() throws Exception {
    driver.findElement(By.xpath("//button[text() = 'OK']")).click();
    driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    driver.findElement(By.xpath("//button[text() = 'Devices']")).click();
    Thread.sleep(2000);
    driver.findElement(By.className("x-menu-item-text")).click();
    driver.findElement(By.id("multiThermoSettingDiv_0")).click();
    Thread.sleep(2000);
    driver.findElement(By.id("heatBtn")).click();
    Thread.sleep(20000);
    String setpoint = driver.findElement(By.id("tempDecimalDiv")).getText();
    String setpointDecimal = driver.findElement(By.id("tempFractionalDiv")).getText();
    System.out.println("my setpoint is " + setpoint + setpointDecimal);
    driver.findElement(By.cssSelector("map[name=\"plusmap\"] > area.plusArrow")).click();
    driver.findElement(By.cssSelector("map[name=\"plusmap\"] > area.plusArrow")).click();
    driver.findElement(By.cssSelector("map[name=\"plusmap\"] > area.plusArrow")).click();
    driver.findElement(By.cssSelector("map[name=\"plusmap\"] > area.plusArrow")).click();
    Thread.sleep(15000);
    String setpoint1 = driver.findElement(By.id("tempDecimalDiv")).getText();
    String setpointDecimal1 = driver.findElement(By.id("tempFractionalDiv")).getText();
    System.out.println("my NEW setpoint is " + setpoint1 + setpointDecimal1);
    
    Thread.sleep(15000);
	MO_Detection_DAO_Impl.moForComcastUserHeatThermostatID();
	Assert.assertEquals(MO_Detection_DAO_Impl.heatHVACModeComcast, t_id);
	MO_Detection_DAO_Impl.moForComcastUserHeatSetpoint();
	//new setpoint string
	String concatNewSetpoint = new StringBuilder().append(setpoint1).append(setpointDecimal1).toString();
	
	System.out.println("concat value " + concatNewSetpoint);
	//convert string to int
	double newUISetpoint = (int) Double.parseDouble(concatNewSetpoint);
	//rounding new setpoint
	int newUISetpointRounded = (int) Math.round(newUISetpoint);
	System.out.println("concat ui setpoint " + newUISetpoint);
	System.out.println("rounded ui setpint " + newUISetpointRounded);
	Assert.assertEquals(MO_Detection_DAO_Impl.heatHVACModeComcastSetpointRounded, newUISetpointRounded);
  }
  
  @Test
  public void moDetectionHeatSetpointDown() throws Exception {
    driver.findElement(By.xpath("//button[text() = 'OK']")).click();
    driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    driver.findElement(By.xpath("//button[text() = 'Devices']")).click();
    Thread.sleep(2000);
    driver.findElement(By.className("x-menu-item-text")).click();
    driver.findElement(By.id("multiThermoSettingDiv_0")).click();
    Thread.sleep(2000);
    driver.findElement(By.id("heatBtn")).click();
    Thread.sleep(30000);
    String setpoint = driver.findElement(By.id("tempDecimalDiv")).getText();
    String setpointDecimal = driver.findElement(By.id("tempFractionalDiv")).getText();
    System.out.println("my setpoint is " + setpoint + setpointDecimal);
    driver.findElement(By.cssSelector("map[name=\"minusmap\"] > area.minusArrow")).click();
    driver.findElement(By.cssSelector("map[name=\"minusmap\"] > area.minusArrow")).click();
    driver.findElement(By.cssSelector("map[name=\"minusmap\"] > area.minusArrow")).click();
    driver.findElement(By.cssSelector("map[name=\"minusmap\"] > area.minusArrow")).click();
    driver.findElement(By.cssSelector("map[name=\"minusmap\"] > area.minusArrow")).click();
    driver.findElement(By.cssSelector("map[name=\"minusmap\"] > area.minusArrow")).click();
    Thread.sleep(25000);
    String setpoint1 = driver.findElement(By.id("tempDecimalDiv")).getText();
    String setpointDecimal1 = driver.findElement(By.id("tempFractionalDiv")).getText();
    System.out.println("my NEW setpoint is " + setpoint1 + setpointDecimal1);
    
    Thread.sleep(20000);
	MO_Detection_DAO_Impl.moForComcastUserHeatThermostatID();
	Assert.assertEquals(MO_Detection_DAO_Impl.heatHVACModeComcast, t_id);
	MO_Detection_DAO_Impl.moForComcastUserHeatSetpoint();
	//new setpoint string
	String concatNewSetpoint = new StringBuilder().append(setpoint1).append(setpointDecimal1).toString();
	
	System.out.println("concat value " + concatNewSetpoint);
	//convert string to int
	double newUISetpoint = (int) Double.parseDouble(concatNewSetpoint);
	//rounding new setpoint
	int newUISetpointRounded = (int) Math.round(newUISetpoint);
	System.out.println("concat ui setpoint " + newUISetpoint);
	System.out.println("rounded ui setpint " + newUISetpointRounded);
	Assert.assertEquals(MO_Detection_DAO_Impl.heatHVACModeComcastSetpointRounded, newUISetpointRounded);

  }

  @Test
  public void moDetectionChangeHVACToOFF() throws Exception {
    driver.findElement(By.xpath("//button[text() = 'OK']")).click();
    driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    driver.findElement(By.xpath("//button[text() = 'Devices']")).click();
    Thread.sleep(2000);
    driver.findElement(By.className("x-menu-item-text")).click();
    driver.findElement(By.id("multiThermoSettingDiv_0")).click();
    Thread.sleep(2000);
    driver.findElement(By.id("offBtn")).click();
    Thread.sleep(30000);
    MO_Detection_DAO_Impl.moForComcastUserHVACOff();
	Assert.assertEquals(MO_Detection_DAO_Impl.coolHVACModeComcastOff, t_id);
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
