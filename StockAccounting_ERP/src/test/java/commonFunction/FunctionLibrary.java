package commonFunction;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import utilities.PropertyFileUtil;

public class FunctionLibrary {
  public static WebDriver driver;
//Method for launching browser
  public static WebDriver startBrowser() throws Throwable
  {
	  if(PropertyFileUtil.getValueForKey("browser").equalsIgnoreCase("chrome"))
	  {
		  driver = new ChromeDriver();
		  driver.manage().window().maximize();
	  }
	  else if(PropertyFileUtil.getValueForKey("browser").equalsIgnoreCase("firefox"))
	  {
		  driver = new FirefoxDriver();
	  }
	  else
	  {
		  System.out.println("Browser value is not matching");
	  }
	return driver;
  }

//Method for launching url
  public static void openApplication(WebDriver driver) throws Throwable
  {
	  driver.get(PropertyFileUtil.getValueForKey("url"));
  }
  
//Method for wait for element
  public static void waitForElement(WebDriver driver,String locatorType,String locatorValue,String myWait)
  {
	  WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(Integer.parseInt(myWait)));
	  if(locatorType.equalsIgnoreCase("name"))
	  {
		  wait.until(ExpectedConditions.visibilityOfElementLocated(By.name(locatorValue)));
	  }
	  else if(locatorType.equalsIgnoreCase("xpath"))
	  {
		  wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(locatorValue)));
	  }
	  else if(locatorType.equalsIgnoreCase("id"))
	  {
		  wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(locatorValue)));
	  }
  }

//Method for textboxes
  public static void typeAction(WebDriver driver,String locatorType,String locatorValue,String testData)
  {
	if(locatorType.equalsIgnoreCase("name"))
	{
		driver.findElement(By.name(locatorValue)).clear();
		driver.findElement(By.name(locatorValue)).sendKeys(testData);
	}
	else if(locatorType.equalsIgnoreCase("xpath"))
	{
		driver.findElement(By.xpath(locatorValue)).clear();
		driver.findElement(By.xpath(locatorValue)).sendKeys(testData);
	}
	else if(locatorType.equalsIgnoreCase("id"))
	{
		driver.findElement(By.id(locatorValue)).clear();
		driver.findElement(By.id(locatorValue)).sendKeys(testData);
	}
  }

//Method for buttons,links,checkboxes,radio buttons,images
  public static void clickAction(WebDriver driver,String locatorType,String locatorValue)
  {
	  if(locatorType.equalsIgnoreCase("name"))
	  {
		  driver.findElement(By.name(locatorValue)).click();
	  }
	  else  if(locatorType.equalsIgnoreCase("xpath"))
	  {
		  driver.findElement(By.xpath(locatorValue)).click();
	  }
	  else  if(locatorType.equalsIgnoreCase("id"))
	  {
		  driver.findElement(By.id(locatorValue)).sendKeys(Keys.ENTER);
	  }
  }

//Method to validate Page title
  public static void validateTitle(WebDriver driver,String expectedTitle)
  {
	  String actualTitle = driver.getTitle();
	  try {
	  Assert.assertEquals(actualTitle,expectedTitle," Title not matching:"+expectedTitle);
	  }
	  catch(Throwable t)
	  {
		  System.out.println(t.getMessage());
	  }
  }

//Method to close Browser
  public static void closeBrowser(WebDriver driver)
  {
	  driver.quit();
  }
  
//method for mouse click
public static void mouseClick(WebDriver driver) throws Throwable
{
	Actions ac = new Actions(driver);
	ac.moveToElement(driver.findElement(By.xpath("//a[starts-with(text(),'Stock Items ')]"))).perform();
	Thread.sleep(3000);
	ac.moveToElement(driver.findElement(By.xpath("(//a[contains(text(),'Stock Categories')])[2]"))).click().perform();
}
//method for category table
public static void categoryTable(WebDriver driver,String Exp_Data) throws Throwable
{
	if(!driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("search-textbox"))).isDisplayed())
		//if search textbox is not displayed click search panel button
	driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("search-panel"))).click();
	//enter category name
	driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("search-textbox"))).sendKeys(Exp_Data);
	driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("search-button"))).click();
	Thread.sleep(3000);
	String Act_Data =driver.findElement(By.xpath("//table[@class='table ewTable']/tbody/tr[1]/td[4]/div/span/span")).getText();
	System.out.println(Exp_Data+"        "+Act_Data);
	Assert.assertEquals(Exp_Data, Act_Data,"Category Name is Not Matching");
}
//method for drop down
public static void dropDownAction(WebDriver driver, String locatorType, String locatorValue, String testdata) throws Exception
{
	if(locatorType.equalsIgnoreCase("xpath"))
	{
		int value = Integer.parseInt(testdata);
		WebElement element = driver.findElement(By.xpath(locatorValue));
		Select select = new Select(element);
		select.selectByIndex(value);
		
	}
	if(locatorType.equalsIgnoreCase("id"))
	{
		int value = Integer.parseInt(testdata);
		WebElement element = driver.findElement(By.id(locatorValue));
		Select select = new Select(element);
		select.selectByIndex(value);
		
	}
	if(locatorType.equalsIgnoreCase("name"))
	{
		int value = Integer.parseInt(testdata);
		WebElement element = driver.findElement(By.name(locatorValue));
		Select select = new Select(element);
		select.selectByIndex(value);
		
	}
}

public static void capturestock(WebDriver driver, String Locator_Type,String Locator_Value)throws Throwable
{
	String Expected_Num="";
	if(Locator_Type.equalsIgnoreCase("xpath"))
	{
		Expected_Num =driver.findElement(By.xpath(Locator_Value)).getAttribute("value");
	}
	else if(Locator_Type.equalsIgnoreCase("id"))
	{
		Expected_Num =driver.findElement(By.id(Locator_Value)).getAttribute("value");
	}
	else if(Locator_Type.equalsIgnoreCase("name"))
	{
		Expected_Num =driver.findElement(By.name(Locator_Value)).getAttribute("value");
	}
	FileWriter fw = new FileWriter("./CaptureData/stocknum.txt");
	BufferedWriter bw = new BufferedWriter(fw);
	bw.write(Expected_Num);
	bw.flush();
	bw.close();
	
}
public static void stockTable(WebDriver driver)throws Throwable
{
	FileReader fr = new FileReader("./CaptureData/stocknum.txt");
	BufferedReader br = new BufferedReader(fr);
	String Exp_stocknumber =br.readLine();
	if(!driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("search-textbox"))).isDisplayed())
		//if search textbox is not displayed click search panel button
	driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("search-panel"))).click();
	//enter category name
	driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("search-textbox"))).sendKeys(Exp_stocknumber);
	driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("search-button"))).click();
	Thread.sleep(3000);
	String Act_StockNum = driver.findElement(By.xpath("//table[@class='table ewTable']/tbody/tr[1]/td[8]/div/span/span")).getText();
	System.out.println(Exp_stocknumber+"     "+Act_StockNum);
	Assert.assertEquals(Exp_stocknumber, Act_StockNum, "Stock Number Not Matching");
}
public static void captureSupp(WebDriver driver,String Locator_Type,String Locator_Value)throws Throwable
{
	String Expected_Data ="";
	if(Locator_Type.equalsIgnoreCase("xpath"))
	{
		Expected_Data = driver.findElement(By.xpath(Locator_Value)).getAttribute("value");
	}
	else if(Locator_Type.equalsIgnoreCase("id"))
	{
		Expected_Data = driver.findElement(By.id(Locator_Value)).getAttribute("value");
	}
	else if(Locator_Type.equalsIgnoreCase("name"))
	{
		Expected_Data = driver.findElement(By.name(Locator_Value)).getAttribute("value");
	}
	FileWriter fw = new FileWriter("./CaptureData/suppliernum.txt");
	BufferedWriter bw = new BufferedWriter(fw);
	bw.write(Expected_Data);
	bw.flush();
	bw.close();
	
}
public static void supplierTable(WebDriver driver)throws Throwable
{
	FileReader fr = new FileReader("./CaptureData/suppliernum.txt");
	BufferedReader br = new BufferedReader(fr);
	String Exp_suppliernumber =br.readLine();
	if(!driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("search-textbox"))).isDisplayed())
		//if search textbox is not displayed click search panel button
	driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("search-panel"))).click();
	//enter category name
	driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("search-textbox"))).sendKeys(Exp_suppliernumber);
	driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("search-button"))).click();
	Thread.sleep(3000);
	String Act_suppliernumber = driver.findElement(By.xpath("//table[@class='table ewTable']/tbody/tr[1]/td[6]/div/span/span")).getText();
	System.out.println(Exp_suppliernumber+"     "+Act_suppliernumber);
	Assert.assertEquals(Exp_suppliernumber, Act_suppliernumber, "Supplier number Not Matching");
}
public static String generateDate()
{
	Date date = new Date();
	DateFormat df = new SimpleDateFormat("YYYY_MM_hh dd_mm_ss");
	return df.format(date);
}

}

