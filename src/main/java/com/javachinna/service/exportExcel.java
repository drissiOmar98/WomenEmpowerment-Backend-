package com.javachinna.service;


import com.javachinna.model.Appointment;
import com.javachinna.model.CandidacyUniversity;
import com.javachinna.model.PartnerInstitution;
import com.javachinna.model.Result;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Service
public class exportExcel {

   public  ByteArrayInputStream universityExcelFile(List<PartnerInstitution> partnerInstitutions) {
       try(Workbook workbook = new XSSFWorkbook()){
           Sheet sheet = workbook.createSheet("Universities");

           Row row = sheet.createRow(0);
           CellStyle headerCellStyle = workbook.createCellStyle();
           headerCellStyle.setFillForegroundColor(IndexedColors.AQUA.getIndex());
           headerCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
           // Creating header
           Cell cell = row.createCell(0);
           cell.setCellValue("Id");
           cell.setCellStyle(headerCellStyle);

           cell = row.createCell(1);
           cell.setCellValue(" Name");
           cell.setCellStyle(headerCellStyle);

           cell = row.createCell(2);
           cell.setCellValue("Country");
           cell.setCellStyle(headerCellStyle);

           cell = row.createCell(3);
           cell.setCellValue("CapacityReception");
           cell.setCellStyle(headerCellStyle);

           cell = row.createCell(4);
           cell.setCellValue("Area");
           cell.setCellStyle(headerCellStyle);

           cell = row.createCell(5);
           cell.setCellValue("Language");
           cell.setCellStyle(headerCellStyle);

           cell = row.createCell(6);
           cell.setCellValue("Fees");
           cell.setCellStyle(headerCellStyle);

           cell = row.createCell(7);
           cell.setCellValue("Specialty");
           cell.setCellStyle(headerCellStyle);

           cell = row.createCell(8);
           cell.setCellValue("Available");
           cell.setCellStyle(headerCellStyle);

           cell = row.createCell(9);
           cell.setCellValue("Email");
           cell.setCellStyle(headerCellStyle);



           // Creating data rows for each customer
           for(int i = 0; i < partnerInstitutions.size(); i++) {
               Row dataRow = sheet.createRow(i + 1);
               dataRow.createCell(0).setCellValue(partnerInstitutions.get(i).getIdPartner());
               dataRow.createCell(1).setCellValue(partnerInstitutions.get(i).getName());
               dataRow.createCell(2).setCellValue(partnerInstitutions.get(i).getCountry());
               dataRow.createCell(3).setCellValue(partnerInstitutions.get(i).getCapacityReception());
               dataRow.createCell(4).setCellValue(String.valueOf(partnerInstitutions.get(i).getGeographicalArea()));
               dataRow.createCell(5).setCellValue(partnerInstitutions.get(i).getLanguage());
               dataRow.createCell(6).setCellValue(partnerInstitutions.get(i).getFees());
               dataRow.createCell(7).setCellValue(String.valueOf(partnerInstitutions.get(i).getSpecial()));
               dataRow.createCell(8).setCellValue(partnerInstitutions.get(i).isActive());
               dataRow.createCell(9).setCellValue(partnerInstitutions.get(i).getEmail());


           }

           // Making size of column auto resize to fit with data
           sheet.autoSizeColumn(0);
           sheet.autoSizeColumn(1);
           sheet.autoSizeColumn(2);
           sheet.autoSizeColumn(3);
           sheet.autoSizeColumn(4);
           sheet.autoSizeColumn(5);
           sheet.autoSizeColumn(6);
           sheet.autoSizeColumn(7);
           sheet.autoSizeColumn(8);
           sheet.autoSizeColumn(9);


           ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
           workbook.write(outputStream);
           return new ByteArrayInputStream(outputStream.toByteArray());
       } catch (IOException ex) {
           ex.printStackTrace();
           return null;
       }
   }

    public  ByteArrayInputStream studentsExcelFile(List<CandidacyUniversity> candidacyUniversities){
        try(Workbook workbook = new XSSFWorkbook()){
            Sheet sheet = workbook.createSheet("Students");

            Row row = sheet.createRow(0);
            CellStyle headerCellStyle = workbook.createCellStyle();
            headerCellStyle.setFillForegroundColor(IndexedColors.AQUA.getIndex());
            headerCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            // Creating header
            Cell cell = row.createCell(0);
            cell.setCellValue("Id");
            cell.setCellStyle(headerCellStyle);

            cell = row.createCell(1);
            cell.setCellValue(" FirstName");
            cell.setCellStyle(headerCellStyle);

            cell = row.createCell(2);
            cell.setCellValue("lastName");
            cell.setCellStyle(headerCellStyle);

            cell = row.createCell(3);
            cell.setCellValue("phoneNumber");
            cell.setCellStyle(headerCellStyle);

            cell = row.createCell(4);
            cell.setCellValue("Nationality");
            cell.setCellStyle(headerCellStyle);

            cell = row.createCell(5);
            cell.setCellValue("email");
            cell.setCellStyle(headerCellStyle);

            cell = row.createCell(6);
            cell.setCellValue("StatusOfDemand");
            cell.setCellStyle(headerCellStyle);

            cell = row.createCell(7);
            cell.setCellValue("idUniversity");
            cell.setCellStyle(headerCellStyle);




            // Creating data rows for each customer
            for(int i = 0; i < candidacyUniversities.size(); i++) {
                Row dataRow = sheet.createRow(i + 1);
                dataRow.createCell(0).setCellValue(candidacyUniversities.get(i).getUser().getId());
                dataRow.createCell(1).setCellValue(candidacyUniversities.get(i).getUser().getFirstName());
                dataRow.createCell(2).setCellValue(candidacyUniversities.get(i).getUser().getLastName());
                dataRow.createCell(3).setCellValue(candidacyUniversities.get(i).getUser().getPhoneNumber());
                dataRow.createCell(4).setCellValue(String.valueOf(candidacyUniversities.get(i).getUser().getNationality()));
                dataRow.createCell(5).setCellValue(candidacyUniversities.get(i).getUser().getEmail());
                dataRow.createCell(6).setCellValue(String.valueOf(candidacyUniversities.get(i).getStatus()));
                dataRow.createCell(7).setCellValue(candidacyUniversities.get(i).getPartnerInstitution().getIdPartner());



            }

            // Making size of column auto resize to fit with data
            sheet.autoSizeColumn(0);
            sheet.autoSizeColumn(1);
            sheet.autoSizeColumn(2);
            sheet.autoSizeColumn(3);
            sheet.autoSizeColumn(4);
            sheet.autoSizeColumn(5);
            sheet.autoSizeColumn(6);
            sheet.autoSizeColumn(7);



            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            workbook.write(outputStream);
            return new ByteArrayInputStream(outputStream.toByteArray());
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }

    }


    public  ByteArrayInputStream Appointmentexportexcel(List<Appointment> appointmentList) {
        try(Workbook workbook = new XSSFWorkbook()){
            Sheet sheet = workbook.createSheet("AppointmentList");

            Row row = sheet.createRow(0);
            CellStyle headerCellStyle = workbook.createCellStyle();
            headerCellStyle.setFillForegroundColor(IndexedColors.AQUA.getIndex());
            headerCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            // Creating header
            Cell cell = row.createCell(0);
            cell.setCellValue("Id App");
            cell.setCellStyle(headerCellStyle);

            cell = row.createCell(1);
            cell.setCellValue(" Date App");
            cell.setCellStyle(headerCellStyle);

            cell = row.createCell(2);
            cell.setCellValue("Remark");
            cell.setCellStyle(headerCellStyle);





            // Creating data rows for each customer
            for(int i = 0; i < appointmentList.size(); i++) {
                Row dataRow = sheet.createRow(i + 1);
                dataRow.createCell(0).setCellValue(appointmentList.get(i).getIdApp());
                dataRow.createCell(1).setCellValue(String.valueOf(appointmentList.get(i).getDateApp()));
                dataRow.createCell(2).setCellValue(appointmentList.get(i).getRemark());



            }

            // Making size of column auto resize to fit with data
            sheet.autoSizeColumn(0);
            sheet.autoSizeColumn(1);
            sheet.autoSizeColumn(2);



            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            workbook.write(outputStream);
            return new ByteArrayInputStream(outputStream.toByteArray());
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }


    public  ByteArrayInputStream quizexportexcel(List<Result> resultList) {
        try(Workbook workbook = new XSSFWorkbook()){
            Sheet sheet = workbook.createSheet("Quiz Result List");

            Row row = sheet.createRow(0);
            CellStyle headerCellStyle = workbook.createCellStyle();
            headerCellStyle.setFillForegroundColor(IndexedColors.AQUA.getIndex());
            headerCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            // Creating header
            Cell cell = row.createCell(0);
            cell.setCellValue("Id Quiz");
            cell.setCellStyle(headerCellStyle);

            cell = row.createCell(1);
            cell.setCellValue(" correct answer ");
            cell.setCellStyle(headerCellStyle);

            cell = row.createCell(2);
            cell.setCellValue(" Incorrect answer ");
            cell.setCellStyle(headerCellStyle);

            cell = row.createCell(3);
            cell.setCellValue(" Total Correct ");
            cell.setCellStyle(headerCellStyle);

            cell = row.createCell(4);
            cell.setCellValue("Username");
            cell.setCellStyle(headerCellStyle);

            cell = row.createCell(5);
            cell.setCellValue("Quiz Id");
            cell.setCellStyle(headerCellStyle);

            cell = row.createCell(6);
            cell.setCellValue("User Id");
            cell.setCellStyle(headerCellStyle);






            // Creating data rows for each customer
            for(int i = 0; i < resultList.size(); i++) {
                Row dataRow = sheet.createRow(i + 1);
                dataRow.createCell(0).setCellValue(resultList.get(i).getId());
                dataRow.createCell(1).setCellValue(String.valueOf(resultList.get(i).getCorrectAnswer()));
                dataRow.createCell(2).setCellValue(resultList.get(i).getInCorrectAnswer());
                dataRow.createCell(3).setCellValue(resultList.get(i).getTotalCorrect());
                dataRow.createCell(4).setCellValue(resultList.get(i).getUsername());
                dataRow.createCell(5).setCellValue(resultList.get(i).getQuiz().getTitle());
                dataRow.createCell(6).setCellValue(resultList.get(i).getSUser().getDisplayName());

            }

            // Making size of column auto resize to fit with data
            sheet.autoSizeColumn(0);
            sheet.autoSizeColumn(1);
            sheet.autoSizeColumn(2);
            sheet.autoSizeColumn(3);
            sheet.autoSizeColumn(4);
            sheet.autoSizeColumn(5);
            sheet.autoSizeColumn(6);



            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            workbook.write(outputStream);
            return new ByteArrayInputStream(outputStream.toByteArray());
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public  ByteArrayInputStream percentageExportExcel(List<Double> pourcentage) {

        try(Workbook workbook = new XSSFWorkbook()){
            Sheet sheet = workbook.createSheet("Quiz Result List");

            Row row = sheet.createRow(0);
            CellStyle headerCellStyle = workbook.createCellStyle();
            headerCellStyle.setFillForegroundColor(IndexedColors.AQUA.getIndex());
            headerCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            // Creating header
            Cell cell = row.createCell(0);
            cell.setCellValue("IT % ");
            cell.setCellStyle(headerCellStyle);

            cell = row.createCell(1);
            cell.setCellValue(" ART % ");
            cell.setCellStyle(headerCellStyle);

            cell = row.createCell(2);
            cell.setCellValue(" CINEMA % ");
            cell.setCellStyle(headerCellStyle);

            cell = row.createCell(3);
            cell.setCellValue(" DANCE % ");
            cell.setCellStyle(headerCellStyle);

            cell = row.createCell(4);
            cell.setCellValue("PHY % ");
            cell.setCellStyle(headerCellStyle);

            cell = row.createCell(5);
            cell.setCellValue(" ECONOMIC % ");
            cell.setCellStyle(headerCellStyle);

            cell = row.createCell(6);
            cell.setCellValue(" MARKETING % ");
            cell.setCellStyle(headerCellStyle);


            cell = row.createCell(7);
            cell.setCellValue(" MUSIC % ");
            cell.setCellStyle(headerCellStyle);






            // Creating data rows for each customer
           // for(int i = 0; i < pourcentage.size(); i++) {

                Row dataRow = sheet.createRow(1);
                dataRow.createCell(0).setCellValue(pourcentage.get(0));
                dataRow.createCell(1).setCellValue(pourcentage.get(1));
                dataRow.createCell(2).setCellValue(pourcentage.get(2));
                dataRow.createCell(3).setCellValue(pourcentage.get(3));
                dataRow.createCell(4).setCellValue(pourcentage.get(4));
                dataRow.createCell(5).setCellValue(pourcentage.get(5));
                dataRow.createCell(6).setCellValue(pourcentage.get(6));
                dataRow.createCell(7).setCellValue(pourcentage.get(7));

           // }

            // Making size of column auto resize to fit with data
            sheet.autoSizeColumn(0);
            sheet.autoSizeColumn(1);
            sheet.autoSizeColumn(2);
            sheet.autoSizeColumn(3);
            sheet.autoSizeColumn(4);
            sheet.autoSizeColumn(5);
            sheet.autoSizeColumn(6);
            sheet.autoSizeColumn(7);



            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            workbook.write(outputStream);
            return new ByteArrayInputStream(outputStream.toByteArray());
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }




}
