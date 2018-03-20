package org.ruanwei.demo.user.web.viewresolver;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.ruanwei.demo.user.entity.User;
 
// Compatible with Apache POI 3.5 and higher.
public class MyXlsxView extends AbstractXlsxView {

	@Override
	protected void buildExcelDocument(Map<String, Object> model,
			Workbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

        User pizza = (User) model.get("user");
 
        Sheet sheet = workbook.createSheet("sheet 1");
        CellStyle style = workbook.createCellStyle();
        style.setFillForegroundColor(IndexedColors.GREY_40_PERCENT.index);
        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
        style.setAlignment(CellStyle.ALIGN_CENTER);
        Row row = null;
        Cell cell = null;
        int rowCount = 0;
        int colCount = 0;
 
        // Create header cells
        row = sheet.createRow(rowCount++);
 
        cell = row.createCell(colCount++);
        cell.setCellStyle(style);
        cell.setCellValue("Name");
 
        cell = row.createCell(colCount++);
        cell.setCellStyle(style);
        cell.setCellValue("Age");
 
        cell = row.createCell(colCount++);
        cell.setCellStyle(style);
        cell.setCellValue("Birthday");
 
        // Create data cells
        row = sheet.createRow(rowCount++);
        colCount = 0;
        row.createCell(colCount++).setCellValue(pizza.getName());
        row.createCell(colCount++).setCellValue(pizza.getAge());
        row.createCell(colCount++).setCellValue(pizza.getBirthday().toString());
 
        for (int i = 0; i < 3; i++)
            sheet.autoSizeColumn(i, true);
	}

}
