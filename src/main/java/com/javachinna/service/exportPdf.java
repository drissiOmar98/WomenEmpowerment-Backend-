package com.javachinna.service;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import com.javachinna.model.Complaint;
import com.javachinna.model.Formation;
import com.javachinna.model.PartnerInstitution;
import com.javachinna.model.User;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

@Service
public class exportPdf {








    public static ByteArrayInputStream FormationPDFReport(List<Formation> formations) throws IOException {
        Document document=new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            PdfWriter.getInstance(document,out);
            document.open();
            //add text to pdf file
            Font font= FontFactory.getFont(FontFactory.COURIER,12,BaseColor.LIGHT_GRAY);
            Paragraph para = new Paragraph("Formation List ",font);
            para.setAlignment(Element.ALIGN_CENTER);
            document.add(para);
            document.add(Chunk.NEWLINE);

            PdfPTable table=new PdfPTable(9);
            //make the columns
            Stream.of("Id","Title","domain","Frais","Niveau","startDay","EndDay","nbrHeurs","NbrMax").forEach(headerTitle -> {
                PdfPCell header = new PdfPCell();
                Font headfont= FontFactory.getFont(FontFactory.HELVETICA_BOLD);
                header.setBackgroundColor(BaseColor.YELLOW);
                header.setBorderWidth(12);
                header.setHorizontalAlignment(Element.ALIGN_CENTER);
                header.setBorderWidth(1);
                header.setPhrase(new Phrase(headerTitle,headfont));
                table.addCell(header);


            });

            for (Formation f : formations){


                PdfPCell idCell = new PdfPCell(new Phrase((f.getIdFormation().toString())));
                idCell.setPaddingLeft(1);
                idCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                idCell.setHorizontalAlignment(Element.ALIGN_LEFT);
                table.addCell(idCell);

                PdfPCell nameCell = new PdfPCell(new Phrase(f.getTitle()));
                nameCell.setPaddingLeft(1);
                nameCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                nameCell.setHorizontalAlignment(Element.ALIGN_LEFT);
                table.addCell(nameCell);

                PdfPCell countryCell = new PdfPCell(new Phrase(f.getDomain().toString()));
                countryCell.setPaddingLeft(1);
                countryCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                countryCell.setHorizontalAlignment(Element.ALIGN_LEFT);
                table.addCell(countryCell);

                PdfPCell areaCell = new PdfPCell(new Phrase(String.valueOf(f.getFrais())));
                areaCell.setPaddingLeft(1);
                areaCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                areaCell.setHorizontalAlignment(Element.ALIGN_LEFT);
                table.addCell(areaCell);

                PdfPCell specialtyCell = new PdfPCell(new Phrase(String.valueOf(f.getLevel())));
                specialtyCell.setPaddingLeft(1);
                specialtyCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                specialtyCell.setHorizontalAlignment(Element.ALIGN_LEFT);
                table.addCell(specialtyCell);

                PdfPCell start = new PdfPCell(new Phrase(f.getStart().toString()));
                start.setPaddingLeft(1);
                start.setVerticalAlignment(Element.ALIGN_MIDDLE);
                start.setHorizontalAlignment(Element.ALIGN_LEFT);
                table.addCell(start);

                PdfPCell end = new PdfPCell(new Phrase(f.getEnd().toString()));
                end.setPaddingLeft(1);
                end.setVerticalAlignment(Element.ALIGN_MIDDLE);
                end.setHorizontalAlignment(Element.ALIGN_LEFT);
                table.addCell(end);



                PdfPCell CapacityReceptionCell = new PdfPCell(new Phrase(String.valueOf(f.getNbrHeures())));
                CapacityReceptionCell.setPaddingLeft(1);
                CapacityReceptionCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                CapacityReceptionCell.setHorizontalAlignment(Element.ALIGN_LEFT);
                table.addCell(CapacityReceptionCell);

                PdfPCell max = new PdfPCell(new Phrase(String.valueOf(f.getNbrMaxParticipant())));
                max.setPaddingLeft(1);
                max.setVerticalAlignment(Element.ALIGN_MIDDLE);
                max.setHorizontalAlignment(Element.ALIGN_LEFT);
                table.addCell(max);


            }
            // Creating an ImageData object
            String url = "./src/main/resources/static/img/QRCode.png";
            Image image = Image.getInstance(url);
            document.add(image);


            document.add(table);
            document.close();

        } catch ( DocumentException | MalformedURLException e) {
            e.printStackTrace();
        }
        return new ByteArrayInputStream(out.toByteArray());
    }
    public  ByteArrayInputStream universityPDFReport(List<PartnerInstitution> partnerInstitutions) {
        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            PdfWriter.getInstance(document, out);
            document.open();
            //add text to pdf file
            Font font = FontFactory.getFont(FontFactory.COURIER, 14, BaseColor.BLACK);
            Paragraph para = new Paragraph("University List ", font);
            para.setAlignment(Element.ALIGN_CENTER);
            document.add(para);
            document.add(Chunk.NEWLINE);
            PdfPTable table = new PdfPTable(9);
            //make the columns
            Stream.of("Id", "Name", "Country", "Area", "Specialty", "Language", "Available", "CapacityReception", "email").forEach(headerTitle -> {
                PdfPCell header = new PdfPCell();
                Font headfont = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
                header.setBackgroundColor(BaseColor.YELLOW);
                header.setBorderWidth(15);
                header.setHorizontalAlignment(Element.ALIGN_CENTER);
                header.setBorderWidth(1);
                header.setPhrase(new Phrase(headerTitle, headfont));
                table.addCell(header);


            });

            for (PartnerInstitution university : partnerInstitutions) {

                //Id university
                PdfPCell idCell = new PdfPCell(new Phrase((university.getIdPartner().toString())));
                idCell.setPaddingLeft(1);
                idCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                idCell.setHorizontalAlignment(Element.ALIGN_LEFT);
                table.addCell(idCell);
                //Name university
                PdfPCell nameCell = new PdfPCell(new Phrase(university.getName()));
                nameCell.setPaddingLeft(1);
                nameCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                nameCell.setHorizontalAlignment(Element.ALIGN_LEFT);
                table.addCell(nameCell);

                PdfPCell countryCell = new PdfPCell(new Phrase(university.getCountry()));
                countryCell.setPaddingLeft(1);
                countryCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                countryCell.setHorizontalAlignment(Element.ALIGN_LEFT);
                table.addCell(countryCell);

                PdfPCell areaCell = new PdfPCell(new Phrase(String.valueOf(university.getGeographicalArea())));
                areaCell.setPaddingLeft(1);
                areaCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                areaCell.setHorizontalAlignment(Element.ALIGN_LEFT);
                table.addCell(areaCell);

                PdfPCell specialtyCell = new PdfPCell(new Phrase(String.valueOf(university.getSpecial())));
                specialtyCell.setPaddingLeft(1);
                specialtyCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                specialtyCell.setHorizontalAlignment(Element.ALIGN_LEFT);
                table.addCell(specialtyCell);

                PdfPCell LanguageCell = new PdfPCell(new Phrase(university.getLanguage()));
                LanguageCell.setPaddingLeft(1);
                LanguageCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                LanguageCell.setHorizontalAlignment(Element.ALIGN_LEFT);
                table.addCell(LanguageCell);

                PdfPCell AvailableCell = new PdfPCell(new Phrase(String.valueOf(university.isActive())));
                AvailableCell.setPaddingLeft(1);
                AvailableCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                AvailableCell.setHorizontalAlignment(Element.ALIGN_LEFT);
                table.addCell(AvailableCell);
                int b = university.getCapacityReception();
                String a = String.valueOf(b);

                PdfPCell CapacityReceptionCell = new PdfPCell(new Phrase(a));
                CapacityReceptionCell.setPaddingLeft(1);
                CapacityReceptionCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                CapacityReceptionCell.setHorizontalAlignment(Element.ALIGN_LEFT);
                table.addCell(CapacityReceptionCell);

                PdfPCell descriptionCell = new PdfPCell(new Phrase(university.getEmail()));
                descriptionCell.setPaddingLeft(1);
                descriptionCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                descriptionCell.setHorizontalAlignment(Element.ALIGN_LEFT);
                table.addCell(descriptionCell);

            }

            document.add(table);
            document.close();


        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return new ByteArrayInputStream(out.toByteArray());
    }

    public  ByteArrayInputStream ComplaintPDFReport(List<Complaint> complaintList)  {
        Document document=new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            PdfWriter.getInstance(document,out);
            document.open();
            //add text to pdf file
            Font font= FontFactory.getFont(FontFactory.COURIER,12,BaseColor.LIGHT_GRAY);
            Paragraph para = new Paragraph("Formation List ",font);
            para.setAlignment(Element.ALIGN_CENTER);
            document.add(para);
            document.add(Chunk.NEWLINE);

            PdfPTable table=new PdfPTable(9);
            //make the columns
            Stream.of("Id","Date Compalint","Description","Type").forEach(headerTitle -> {
                PdfPCell header = new PdfPCell();
                Font headfont= FontFactory.getFont(FontFactory.HELVETICA_BOLD);
                header.setBackgroundColor(BaseColor.YELLOW);
                header.setBorderWidth(12);
                header.setHorizontalAlignment(Element.ALIGN_CENTER);
                header.setBorderWidth(1);
                header.setPhrase(new Phrase(headerTitle,headfont));
                table.addCell(header);


            });

            for (Complaint c : complaintList){


                PdfPCell idCell = new PdfPCell(new Phrase((c.getIdCom().toString())));
                idCell.setPaddingLeft(1);
                idCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                idCell.setHorizontalAlignment(Element.ALIGN_LEFT);
                table.addCell(idCell);

                PdfPCell nameCell = new PdfPCell(new Phrase(c.getDateCom().toString()));
                nameCell.setPaddingLeft(1);
                nameCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                nameCell.setHorizontalAlignment(Element.ALIGN_LEFT);
                table.addCell(nameCell);

                PdfPCell countryCell = new PdfPCell(new Phrase(c.getDescription()));
                countryCell.setPaddingLeft(1);
                countryCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                countryCell.setHorizontalAlignment(Element.ALIGN_LEFT);
                table.addCell(countryCell);

                PdfPCell areaCell = new PdfPCell(new Phrase(String.valueOf(c.getType())));
                areaCell.setPaddingLeft(1);
                areaCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                areaCell.setHorizontalAlignment(Element.ALIGN_LEFT);
                table.addCell(areaCell);

            }
            // Creating an ImageData object
           // String url = "./src/main/resources/static/img/QRCode.png";
           // Image image = Image.getInstance(url);
           // document.add(image);


            document.add(table);
            document.close();

        } catch ( DocumentException  e) {
            e.printStackTrace();
        }
        return new ByteArrayInputStream(out.toByteArray());
    }


    public void pdfReader(Formation f , User user,String path)
    {


        try
        {


            String  nbrheurs =" Courses  : "+f.getNbrHeures() +" hours total ";
            String Domain = "Domain : "+f.getDomain();
            String Formateur = f.getFormateur().getFirstName() + " " + f.getFormateur().getLastName();
            String Title = f.getTitle();
            String User = user.getFirstName() +" "+user.getLastName();

            //Read file using PdfReader
            PdfReader pdfReader = new PdfReader("/Users/macos/IdeaProjects/springPidev/src/main/resources/static/Certif/Certif.pdf");

            //Modify file using PdfReader
            PdfStamper pdfStamper = new PdfStamper(pdfReader, new FileOutputStream("/Users/macos/IdeaProjects/springPidev/src/main/resources/static/Certif/C"+user.getId()+".pdf"));


            Image image = Image.getInstance(path);
            Image image2 = Image.getInstance("/Users/macos/IdeaProjects/springPidev/src/main/resources/static/img/img.png");


            image.scaleAbsolute(100, 100);
            image.setAbsolutePosition(480f, 467f);




            image2.scaleAbsolute(90, 90);
            image2.setAbsolutePosition(710f, 480f);


            String url = "./src/main/resources/static/img/QRCode.png";
            Image qrCode = Image.getInstance(url);
            qrCode.scaleAbsolute(70, 70);
            qrCode.setAbsolutePosition(300f, 480f);

            PdfContentByte canvas = pdfStamper.getOverContent(1);
            canvas.addImage(image);
            canvas.addImage(image2);
            canvas.addImage(qrCode);

            Font fontTitle = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
            fontTitle.setSize(30);



            Paragraph paragraph = new Paragraph(Title, fontTitle);
            paragraph.setAlignment(Paragraph.ALIGN_CENTER);

            Font fontParagraph = FontFactory.getFont(FontFactory.HELVETICA);
            fontParagraph.setSize(16);

            Paragraph paragraph1 = new Paragraph(Domain, fontParagraph);
            paragraph1.setAlignment(Paragraph.ALIGN_CENTER);



            Paragraph paragraph2 = new Paragraph( "Username : "+User, fontParagraph);
            paragraph2.setAlignment(Paragraph.ALIGN_CENTER);

            Paragraph paragraph3 = new Paragraph( Formateur, fontParagraph);
            paragraph2.setAlignment(Paragraph.ALIGN_CENTER);

            Paragraph paragraph4 = new Paragraph( new Date().toString(), fontParagraph);
            paragraph2.setAlignment(Paragraph.ALIGN_CENTER);


            Paragraph paragraph5 = new Paragraph(nbrheurs, fontParagraph);
            paragraph2.setAlignment(Paragraph.ALIGN_CENTER);


            ColumnText.showTextAligned(canvas, Element.BODY, paragraph, 400, 350, 0);
            ColumnText.showTextAligned(canvas, Element.BODY, paragraph2, 400, 220, 0);
            ColumnText.showTextAligned(canvas, Element.BODY, paragraph3, 470, 182, 0);
            ColumnText.showTextAligned(canvas, Element.BODY, paragraph4, 460, 112, 0);
            ColumnText.showTextAligned(canvas, Element.BODY, paragraph5, 500, 140, 0);



            pdfStamper.close();

        } catch (IOException | DocumentException e) {
            e.printStackTrace();
        }
    }





}
