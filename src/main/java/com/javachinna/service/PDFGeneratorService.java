package com.javachinna.service;

import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Service
public class PDFGeneratorService {
    public void export(HttpServletResponse response) throws IOException {
        Document document=new Document(PageSize.A4);
        PdfWriter.getInstance(document,response.getOutputStream());
        document.open();
        document.open();
        Font fontTitle = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        fontTitle.setSize(18);
        Paragraph paragraph=new Paragraph("This is a title.", fontTitle);
        paragraph.setAlignment(Paragraph.ALIGN_CENTER);

        Font fontParagraph = FontFactory.getFont(FontFactory.HELVETICA);
        fontParagraph.setSize(12);

        Paragraph paragraph2 = new Paragraph("This is a paragraph.", fontParagraph);
        paragraph2.setAlignment(Paragraph.ALIGN_LEFT);

        document.add(paragraph);
        document.add(paragraph2);
        document.close();
    }


    public void exportFor(HttpServletResponse response,String p1 ,String p2,String qrcode) throws IOException, com.itextpdf.text.DocumentException {
        com.itextpdf.text.Document document = new com.itextpdf.text.Document(com.itextpdf.text.PageSize.A4);
        com.itextpdf.text.pdf.PdfWriter.getInstance(document, response.getOutputStream());

        document.open();
        com.itextpdf.text.Font fontTitle = com.itextpdf.text.FontFactory.getFont(com.itextpdf.text.FontFactory.HELVETICA_BOLD);
        fontTitle.setSize(18);

        com.itextpdf.text.Paragraph paragraph = new com.itextpdf.text.Paragraph(p1, fontTitle);
        paragraph.setAlignment(com.itextpdf.text.Paragraph.ALIGN_CENTER);

        com.itextpdf.text.Font fontParagraph = com.itextpdf.text.FontFactory.getFont(com.itextpdf.text.FontFactory.HELVETICA);
        fontParagraph.setSize(12);

        //   Image img = Image.getInstance("./src/main/resources/static/img/QRCode.png");
        // img.scalePercent(50, 50);
        //img.setAlignment(Element.ALIGN_RIGHT);


        com.itextpdf.text.Paragraph paragraph2 = new com.itextpdf.text.Paragraph(p2, fontParagraph);
        paragraph2.setAlignment(com.itextpdf.text.Paragraph.ALIGN_LEFT);

        document.add(paragraph);
        //  document.add(img);
        document.add(paragraph2);
        document.close();
    }
}
