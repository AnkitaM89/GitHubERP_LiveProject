package driverFactory;

import org.openqa.selenium.WebDriver;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import commonFunction.FunctionLibrary;
import utilities.ExcelFileUtil;

public class DriverScript {
	WebDriver driver;
	String inputPath = "./FileInput/DataEngine.xlsx";
	String outputPath = "./FileOutput/HybridResults.xlsx";
	String testCases = "MasterTestCases";
	ExtentReports report;
	ExtentTest logger;
	public void startTest() throws Throwable
	{
		String moduleStatus = "";
		//Create reference object to call all Excel Methods
		ExcelFileUtil xl = new ExcelFileUtil(inputPath);
		//Iterate all rows in Master Test Cases Sheet
		for(int i=1;i<=xl.rowCount(testCases);i++)
		{
			if(xl.getCellData(testCases, i, 2).equalsIgnoreCase("Y"))
			{
				//Reading corresponding sheet names
				String tcModule = xl.getCellData(testCases, i, 1);
				report = new ExtentReports("./target/Reports/"+tcModule+FunctionLibrary.generateDate()+".html");
				logger = report.startTest(tcModule);
				//Iterate all rows in TCModule
				for(int j=1;j<=xl.rowCount(tcModule);j++)
				{
					//Read cell from TCModule
					String description = xl.getCellData(tcModule, j, 0);
					String objType = xl.getCellData(tcModule, j, 1);
					String locatorType = xl.getCellData(tcModule, j, 2);
					String locatorValue = xl.getCellData(tcModule, j, 3);
					String testData = xl.getCellData(tcModule, j, 4);
					try 
					{
						if(objType.equalsIgnoreCase("startBrowser"))
						{
                           driver = FunctionLibrary.startBrowser();
                           logger.log(LogStatus.INFO, description);
						}
						if(objType.equalsIgnoreCase("openApplication"))
						{
                           FunctionLibrary.openApplication(driver);
                           logger.log(LogStatus.INFO, description);
						}
						if(objType.equalsIgnoreCase("waitForElement"))
						{
                           FunctionLibrary.waitForElement(driver,locatorType,locatorValue,testData);
                           logger.log(LogStatus.INFO, description);
						}
						if(objType.equalsIgnoreCase("typeAction"))
						{
                           FunctionLibrary.typeAction(driver,locatorType,locatorValue,testData);
                           logger.log(LogStatus.INFO, description);
						}
						if(objType.equalsIgnoreCase("clickAction"))
						{
                           FunctionLibrary.clickAction(driver,locatorType,locatorValue);
                           logger.log(LogStatus.INFO, description);
						}
						if(objType.equalsIgnoreCase("validateTitle"))
						{
                           FunctionLibrary.validateTitle(driver,testData);
                           logger.log(LogStatus.INFO, description);
						}
						if(objType.equalsIgnoreCase("closeBrowser"))
						{
                           FunctionLibrary.closeBrowser(driver);
                           logger.log(LogStatus.INFO, description);
						}
						if(objType.equalsIgnoreCase("mouseClick"))
						{
							FunctionLibrary.mouseClick(driver);
							logger.log(LogStatus.INFO, description);
						}
						if(objType.equalsIgnoreCase("categoryTable"))
						{
							FunctionLibrary.categoryTable(driver, testData);
							logger.log(LogStatus.INFO, description);
						}
						if(objType.equalsIgnoreCase("dropDownAction"))
						{
							FunctionLibrary.dropDownAction(driver, locatorType, locatorValue, testData);
							logger.log(LogStatus.INFO, description);
						}
						if(objType.equalsIgnoreCase("capturestock"))
						{
							FunctionLibrary.capturestock(driver, locatorType, locatorValue);
							logger.log(LogStatus.INFO, description);
						}
						if(objType.equalsIgnoreCase("stockTable"))
						{
							FunctionLibrary.stockTable(driver);
							logger.log(LogStatus.INFO, description);
						}
						if(objType.equalsIgnoreCase("captureSupp"))
						{
							FunctionLibrary.captureSupp(driver, locatorType, locatorValue);
							logger.log(LogStatus.INFO, description);
						}
						if(objType.equalsIgnoreCase("supplierTable"))
						{
							FunctionLibrary.supplierTable(driver);
							logger.log(LogStatus.INFO, description);
						}
						//Write as Pass into TCModule
						xl.setCellData(tcModule, j, 5, "Pass", outputPath);
						logger.log(LogStatus.PASS, description);
						moduleStatus = "True";
						
					}
					catch(Exception e) 
					{
						System.out.println(e.getMessage());
						//write as Fail into TCModule
						xl.setCellData(tcModule, j, 5, "Fail", outputPath);
						logger.log(LogStatus.FAIL, description);
						moduleStatus = "False";
					}
					if(moduleStatus.equalsIgnoreCase("True"))
					{
						xl.setCellData(testCases, i, 3, "Pass", outputPath);
					}
					else
					{
						xl.setCellData(testCases, i, 3, "Fail", outputPath);
					}
					report.endTest(logger);
					report.flush();
				}
			}
			else
			{
				//Write as Blocked which test cases are flagged to N
				xl.setCellData(testCases, i, 3, "Blocked", outputPath);
			}
		}
	}
}

