package utilities;

import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ExcelFileUtil {
	Workbook wb;
	//Constructor for reading excel path
	public ExcelFileUtil(String excelPath) throws Throwable
	{
		FileInputStream fi = new FileInputStream(excelPath);
		wb = WorkbookFactory.create(fi);
	}
	//Count no. of rows in Sheet
	public int rowCount(String sheetName)
	{
		return wb.getSheet(sheetName).getLastRowNum();
	}
	//Get Cell Data
	
	public String getCellData(String sheetName,int row,int col)
	{
		String data="";
		if(wb.getSheet(sheetName).getRow(row).getCell(col).getCellType()==Cell.CELL_TYPE_NUMERIC)
		{
			int cellData =(int) wb.getSheet(sheetName).getRow(row).getCell(col).getNumericCellValue();
		    data = String.valueOf(cellData);
		}
		else
		{
			data = wb.getSheet(sheetName).getRow(row).getCell(col).getStringCellValue();
		}
		return data;
	}
	//Method for writing data
	public void setCellData(String sheetName,int row,int col,String status,String writeExcel) throws Throwable
	{
		//Get sheet from Workbook
		Sheet ws = wb.getSheet(sheetName);
		//Get row from Sheet
		//Row rowNum = ws.getRow(row);
		//Get cell from row
		//Cell cell = rowNum.createCell(col);
		//Write status
		//cell.setCellValue(status);
		 ws.getRow(row).createCell(col).setCellValue(status);
		if(status.equalsIgnoreCase("pass"))
		{
			CellStyle style = wb.createCellStyle();
			Font font = wb.createFont();
			
			//Colour text with green
			font.setColor(IndexedColors.GREEN.getIndex());
			font.setBold(true);
			font.setBoldweight(Font.BOLDWEIGHT_BOLD);
			style.setFont(font);
			ws.getRow(row).getCell(col).setCellStyle(style);
		}
		else if(status.equalsIgnoreCase("fail"))
		{
			CellStyle style = wb.createCellStyle();
			Font font = wb.createFont();
			
			//Colour text with green
			font.setColor(IndexedColors.RED.getIndex());
			font.setBold(true);
			font.setBoldweight(Font.BOLDWEIGHT_BOLD);
			style.setFont(font);
			ws.getRow(row).getCell(col).setCellStyle(style);
		}
		else if(status.equalsIgnoreCase("blocked"))
		{
			CellStyle style = wb.createCellStyle();
			Font font = wb.createFont();
			
			//Colour text with green
			font.setColor(IndexedColors.BLUE.getIndex());
			font.setBold(true);
			font.setBoldweight(Font.BOLDWEIGHT_BOLD);
			style.setFont(font);
			ws.getRow(row).getCell(col).setCellStyle(style);
		}
		FileOutputStream fo = new FileOutputStream(writeExcel);
		wb.write(fo);
	}
}

