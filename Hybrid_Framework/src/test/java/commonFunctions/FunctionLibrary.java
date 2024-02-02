package commonFunctions;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
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
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;

import utilities.PropertyFileUtil;

public class FunctionLibrary{
	public static WebDriver driver;
	//method for launching browser
	public static WebDriver startBrowser() throws Throwable
	{
		if(PropertyFileUtil.getValueForKey("Browser").equalsIgnoreCase("chrome"))
		{
			driver = new ChromeDriver();
			driver.manage().window().maximize();
		}
		else if(PropertyFileUtil.getValueForKey("Browser").equalsIgnoreCase("firefox"))
		{
			driver = new FirefoxDriver();
		}
		else
		{
			Reporter.log("Browser value is not Matching",true);
		}
		return driver;
	}
	//method for launching Url
	public static WebDriver openUrl() throws Throwable
	{
		driver.get(PropertyFileUtil.getValueForKey("Url"));
		return driver;
	}
	//method for wait for all webelement waitForElement
	public static void waitForElement(String Locator_Type,String Locator_Value,String Test_Data)
	{
		WebDriverWait mywait = new WebDriverWait(driver, Duration.ofSeconds(Integer.parseInt(Test_Data)));
		if(Locator_Type.equalsIgnoreCase("xpath"))
		{
			mywait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(Locator_Value)));
		}


		else if(Locator_Type.equalsIgnoreCase("id"))
		{
			mywait.until(ExpectedConditions.visibilityOfElementLocated(By.id(Locator_Value)));
		}
		else if(Locator_Type.equalsIgnoreCase("name"))
		{
			mywait.until(ExpectedConditions.visibilityOfElementLocated(By.name(Locator_Value)));
		}	
	}
	//method for textboxes
	public static void typeAction(String Locator_Type,String Locator_Value,String Test_Data)
	{
		if(Locator_Type.equalsIgnoreCase("xpath"))
		{
			driver.findElement(By.xpath(Locator_Value)).clear();
			driver.findElement(By.xpath(Locator_Value)).sendKeys(Test_Data);
		}
		else if(Locator_Type.equalsIgnoreCase("id"))
		{
			driver.findElement(By.id(Locator_Value)).clear();
			driver.findElement(By.id(Locator_Value)).sendKeys(Test_Data);
		}
		else if(Locator_Type.equalsIgnoreCase("name"))
		{
			driver.findElement(By.name(Locator_Value)).clear();
			driver.findElement(By.name(Locator_Value)).sendKeys(Test_Data);
		}
	}
	//method for button,links,radiobuttons,checkboxes,images
	public static void clickAction(String Locator_Type,String Locator_Value)
	{
		if(Locator_Type.equalsIgnoreCase("xpath"))
		{
			driver.findElement(By.xpath(Locator_Value)).click();
		}
		else if(Locator_Type.equalsIgnoreCase("name"))
		{
			driver.findElement(By.name(Locator_Value)).click();
		}
		else if(Locator_Type.equalsIgnoreCase("id"))
		{
			driver.findElement(By.id(Locator_Value)).sendKeys(Keys.ENTER);
		}
	}
	//method for validate page title
	public static void validateTitle(String Expected_Title)
	{
		String Actual_title = driver.getTitle();
		try {
			Assert.assertEquals(Actual_title, Expected_Title,"Title is not matching");
		}catch(AssertionError a)
		{
			System.out.println(a.getMessage());
		}
	}
	//method for close browser
	public static void closeBrowser()
	{
		driver.quit();
	}
	//method for date generation
	public static  String generateDate()
	{
		Date date = new Date();
		DateFormat df = new SimpleDateFormat("YYYY_MM_dd hh_mm_ss");
		return df.format(date);
	}
	//method for Listboxes
	public static void dropDownAction(String Locator_Type, String Locator_Value, String Test_Data)
	{
		if(Locator_Type.equalsIgnoreCase("id"))
		{
			int value = Integer.parseInt(Test_Data);
			WebElement element = driver.findElement(By.id(Locator_Value));
			Select select = new Select(element);
			select.selectByIndex(value);
		}
		else if(Locator_Type.equalsIgnoreCase("xpath"))
		{
			int value = Integer.parseInt(Test_Data);
			WebElement element = driver.findElement(By.xpath(Locator_Value));
			Select select = new Select(element);
			select.selectByIndex(value);

		}
		else if(Locator_Type.equalsIgnoreCase("name"))
		{
			int value = Integer.parseInt(Test_Data);
			WebElement element = driver.findElement(By.name(Locator_Value));
			Select select = new Select(element);
			select.selectByIndex(value);
		}
	}
	//method for capturing stock number
	public static void stockCapture(String Locator_Type, String Locator_Value) throws Throwable
	{
		String stocknum = "";
		//String Exp_Data = "";
		if(Locator_Type.equalsIgnoreCase("id"))
		{
			stocknum = driver.findElement(By.id(Locator_Value)).getAttribute("value");
		}
		else if(Locator_Type.equalsIgnoreCase("name"))
		{
			stocknum = driver.findElement(By.name(Locator_Value)).getAttribute("value");
		}
		else if(Locator_Type.equalsIgnoreCase("xpath"))
		{
			stocknum = driver.findElement(By.xpath(Locator_Value)).getAttribute("value");
		}
		//writing stock number into notepad
		FileWriter fw = new FileWriter("./CaptureData/stocknumber.txt");
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write(stocknum);
		bw.flush();
		bw.close();
	}
	//method for stock table
	public static void stockTable() throws Throwable
	{
		//method for stock number from notepad
		FileReader fr = new FileReader("./CaptureData/stocknumber.txt");
		BufferedReader br = new BufferedReader(fr);
		String Exp_data = br.readLine();
		if(!driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("search-textbox"))).isDisplayed())
			driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("search-panel"))).click();
		driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("search-textbox"))).clear();
		driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("search-textbox"))).sendKeys(Exp_data);
		driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("search-button"))).click();
		Thread.sleep(4000);
		String Act_Data = driver.findElement(By.xpath("//table[@class='table ewTable']/tbody/tr[1]/td[8]/div/span/span")).getText();
		Thread.sleep(4000);
		Reporter.log(Exp_data+"     "+Act_Data,true);
		try {
			Assert.assertEquals(Exp_data, Act_Data,"stock number is not matching");
		}catch(AssertionError a)
		{
			System.out.println(a.getMessage());
		}
	}
	//method for supplier number
	public static void captureSupplier(String Locator_Type, String Locator_Value) throws Throwable
	{
		String suppliernum = "";
		//String Exp_Data = "";
		if(Locator_Type.equalsIgnoreCase("id"))
		{
			suppliernum = driver.findElement(By.id(Locator_Value)).getAttribute("value");
		}
		else if(Locator_Type.equalsIgnoreCase("name"))
		{
			suppliernum = driver.findElement(By.name(Locator_Value)).getAttribute("value");
		}
		else if(Locator_Type.equalsIgnoreCase("xpath"))
		{
			suppliernum = driver.findElement(By.xpath(Locator_Value)).getAttribute("value");
		}
		//writing stock number into notepad
		FileWriter fw = new FileWriter("./CaptureData/suppliernumber.txt");
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write(suppliernum);
		bw.flush();
		bw.close();
	}
	//method for supplier table
	public static void suppliertable() throws Throwable
	{
		//method for stock number from notepad
		FileReader fr = new FileReader("./CaptureData/suppliernumber.txt");
		BufferedReader br = new BufferedReader(fr);
		String Exp_data = br.readLine();
		if(!driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("search-textbox"))).isDisplayed())
			driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("search-panel"))).click();
		driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("search-textbox"))).clear();
		driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("search-textbox"))).sendKeys(Exp_data);
		driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("search-button"))).click();
		Thread.sleep(4000);
		String Act_Data = driver.findElement(By.xpath("//table[@class='table ewTable']/tbody/tr[1]/td[6]/div/span/span")).getText();
		Reporter.log(Exp_data+"      "+Act_Data,true);
		try {
			Assert.assertEquals(Exp_data, Act_Data,"Supplier Number is not Matching");
		}catch(AssertionError a)
		{
			System.out.println(a.getMessage());
		}
	}
	//method for capture customer number 
	public static void captureCustomer(String Locator_Type,String Locator_Value) throws Throwable
	{
		String customernum = "";
		//String Exp_Data = "";
		if(Locator_Type.equalsIgnoreCase("id"))
		{
			customernum = driver.findElement(By.id(Locator_Value)).getAttribute("value");
		}
		else if(Locator_Type.equalsIgnoreCase("name"))
		{
			customernum = driver.findElement(By.name(Locator_Value)).getAttribute("value");
		}
		else if(Locator_Type.equalsIgnoreCase("xpath"))
		{
			customernum = driver.findElement(By.xpath(Locator_Value)).getAttribute("value");
		}
		//writing stock number into notepad
		FileWriter fw = new FileWriter("./CaptureData/customernumber.txt");
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write(customernum);
		bw.flush();
		bw.close();
	}
	//method for customer table
	public static void customerTable() throws Throwable
	{
		//method for customer number from notepad
		FileReader fr = new FileReader("./CaptureData/customernumber.txt");
		BufferedReader br = new BufferedReader(fr);
		String Exp_data = br.readLine();
		if(!driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("search-textbox"))).isDisplayed())
			driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("search-panel"))).click();
		driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("search-textbox"))).clear();
		driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("search-textbox"))).sendKeys(Exp_data);
		driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("search-button"))).click();
		Thread.sleep(4000);
		String Act_Data = driver.findElement(By.xpath("//table[@class='table ewTable']/tbody/tr[1]/td[5]/div/span/span")).getText();
		Reporter.log(Exp_data+"       "+Act_Data,true);
		try {
		Assert.assertEquals(Exp_data, Act_Data,"Customer Number is not Matching");
		}catch(AssertionError a )
		{
	System.out.println(a.getMessage());
}
	}
}






