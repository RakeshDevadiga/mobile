package base;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Utility {
	public static FileInputStream fis;
	public static FileOutputStream fos;
	public static XSSFWorkbook wb;
	public static XSSFSheet sheet;
	public static XSSFRow row;
	public static XSSFCell cell;

	public static List<String> getRowData(String xlsxFileName, String xlsheet, int rownum) {
		List<String> list = new ArrayList<String>();
		try {
			fis = new FileInputStream(
					System.getProperty("user.dir") + "/src/test/resources/TestData/" + xlsxFileName + ".xlsx");
			wb = new XSSFWorkbook(fis);
			sheet = wb.getSheet(xlsheet);
			row = sheet.getRow(0);

			int cellcount = row.getLastCellNum();
			for (int i = 0; i < cellcount; i++) {
				try {
					DataFormatter formatter = new DataFormatter();
					String cellData = formatter.formatCellValue(sheet.getRow(rownum).getCell(i));
					list.add(cellData);

				} catch (Exception e) {
					list.add(null);
				}
			}

			wb.close();
			fis.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public static void setCellData(String xlsxFileName, String xlsheet, int rownum, int colnum, String data)
			throws IOException {
		fis = new FileInputStream(System.getProperty("user.dir") + "/src/test/resources/" + xlsxFileName + ".xlsx");
		wb = new XSSFWorkbook(fis);
		sheet = wb.getSheet(xlsheet);
		row = sheet.getRow(rownum);
		cell = row.createCell(colnum);
		cell.setCellValue(data);
		fos = new FileOutputStream(
				System.getProperty("user.dir") + "/src/test/resources/TestData/" + xlsxFileName + ".xlsx");
		wb.write(fos);
		wb.close();
		fis.close();
		fos.close();
	}

	public static int getRowCount(String xlsxFileName, String xlsheet) throws IOException {
		fis = new FileInputStream(
				System.getProperty("user.dir") + "/src/test/resources/TestData/" + xlsxFileName + ".xlsx");
		wb = new XSSFWorkbook(fis);
		sheet = wb.getSheet(xlsheet);
		int rowcount = sheet.getLastRowNum();
		wb.close();
		fis.close();
		return rowcount;
	}
}
