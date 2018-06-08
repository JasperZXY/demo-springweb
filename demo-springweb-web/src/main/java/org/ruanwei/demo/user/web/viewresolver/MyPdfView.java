package org.ruanwei.demo.user.web.viewresolver;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections4.CollectionUtils;
import org.ruanwei.demo.user.entity.User;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;


public class MyPdfView extends AbstractPdfView {

    @Override
    protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer, HttpServletRequest request, HttpServletResponse response) throws Exception {
        //        User pizza = (User) model.get("user");
        //
        //        PdfPTable table = new PdfPTable(3);
        //        table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        //        table.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
        //        table.getDefaultCell().setBackgroundColor(BaseColor.LIGHT_GRAY);
        //
        //        table.addCell("Name");
        //        table.addCell("Age");
        //        table.addCell("Birthday");
        //
        //        table.addCell(pizza.getName());
        //        table.addCell(pizza.getAge() + "");
        //        table.addCell(pizza.getBirthday().toString());
        //
        //        document.add(table);
        Object obj = model.get("data");
        if (obj != null) {
            if (obj instanceof List) {
                List list = (List) obj;
                if (CollectionUtils.isEmpty(list)) {
                    return;
                }
                Object first = list.get(0);
                Field[] fields = first.getClass().getDeclaredFields();
                PdfPTable tableHead = new PdfPTable(fields.length);
                tableHead.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
                tableHead.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
                tableHead.getDefaultCell().setBackgroundColor(BaseColor.LIGHT_GRAY);

                for (Field field : fields) {
                    field.setAccessible(true);
                    tableHead.addCell(field.getName());
                }
                document.add(tableHead);

                for (Object item : list) {
                    PdfPTable tableItem = new PdfPTable(fields.length);
                    for (Field field : fields) {
                        Object itemFild = field.get(item);
                        if (itemFild == null) {
                            tableItem.addCell("");
                        }else {
                            tableItem.addCell(itemFild.toString());
                        }
                    }
                    document.add(tableItem);
                }
            }else {
                Object first = obj;
                Field[] fields = first.getClass().getDeclaredFields();
                PdfPTable tableHead = new PdfPTable(fields.length);
                tableHead.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
                tableHead.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
                tableHead.getDefaultCell().setBackgroundColor(BaseColor.LIGHT_GRAY);

                for (Field field : fields) {
                    field.setAccessible(true);
                    tableHead.addCell(field.getName());
                }
                document.add(tableHead);

                PdfPTable tableItem = new PdfPTable(fields.length);
                for (Field field : fields) {
                    Object itemFild = field.get(obj);
                    if (itemFild == null) {
                        tableItem.addCell("");
                    }else {
                        tableItem.addCell(itemFild.toString());
                    }
                }
                document.add(tableItem);
            }
        }

    }

}
