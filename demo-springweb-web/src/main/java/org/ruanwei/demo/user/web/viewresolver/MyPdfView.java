package org.ruanwei.demo.user.web.viewresolver;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ruanwei.demo.user.entity.User;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
 
public class MyPdfView extends AbstractPdfView {

	@Override
	protected void buildPdfDocument(Map<String, Object> model,
			Document document,PdfWriter writer, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		User pizza = (User) model.get("user");
		 
        PdfPTable table = new PdfPTable(3);
        table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        table.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
        table.getDefaultCell().setBackgroundColor(BaseColor.LIGHT_GRAY);
 
        table.addCell("Name");
        table.addCell("Age");
        table.addCell("Birthday");
 
        table.addCell(pizza.getName());
        table.addCell(pizza.getAge()+"");
        table.addCell(pizza.getBirthday().toString());
 
        document.add(table);
		
	}

}
