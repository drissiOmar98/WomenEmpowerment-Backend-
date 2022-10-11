package com.javachinna.service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.javachinna.model.Certificat;
import com.javachinna.model.Formation;
import com.javachinna.model.User;
import com.javachinna.repo.ICertificatRepo;
import com.javachinna.repo.IFormationRepo;
import com.javachinna.repo.UserRepository;
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.List;

@Service
public class CertificatService implements ICrudRepository<Certificat>  {

    @Autowired
    ICertificatRepo certificatRepository;
    @Autowired
    UserRepository userRepository;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    IFormationRepo formationRepository;


    @Override
    public Certificat save(Certificat certificat) {
        return certificatRepository.save(certificat) ;
    }

    @Override
    public Certificat update(Certificat certificat) {

        return certificatRepository.save(certificat) ;
    }

    @Override
    public Certificat findById(Long id) {
        return certificatRepository.findById(id).get() ;
    }

    @Override
    public void delete(Long id) {
        certificatRepository.deleteById(id);
    }

    @Override
    public List<Certificat> findAll() {
        return (List<Certificat>) certificatRepository.findAll();
    }

    public void affecterCertifUser(Long idCertif, Long idUser) {
        Certificat certif = certificatRepository.findById(idCertif).orElse(null);
        User user = userRepository.findById(idUser).orElse(null);
        user.getCertificats().add(certif);
        userRepository.save(user);
    }


//------------Export File----------------------------
private static final String QR_CODE_IMAGE_PATH = "./src/main/resources/QRCode.png";

    public void export(HttpServletResponse response, Long userId, Integer FormationId ) throws IOException, com.itextpdf.text.BadElementException {

        Formation f = formationRepository.findById(FormationId).orElse(null);
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());

        String Nom="nom:"+userRepository.getOne(userId).getLastName();
        String Prenom="Prenom:"+userRepository.getOne(userId).getFirstName();
        String Email="Email:"+userRepository.getOne(userId).getEmail();
        String Formation="Formation:"+f.getTitle();

        document.open();
        Font fontTitle = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        fontTitle.setSize(20);

        Paragraph paragraph = new Paragraph("Certificat of Achivement ", fontTitle);
        paragraph.setAlignment(Paragraph.ALIGN_CENTER);

        Font fontParagraph = FontFactory.getFont(FontFactory.HELVETICA);
        fontParagraph.setSize(16);

        Paragraph paragraph2 = new Paragraph( Nom, fontParagraph);
        paragraph2.setAlignment(Paragraph.ALIGN_CENTER);

        Paragraph paragraph3 = new Paragraph(Prenom, fontParagraph);
        paragraph3.setAlignment(Paragraph.ALIGN_CENTER);

        Paragraph paragraph4 = new Paragraph(Email, fontParagraph);
        paragraph4.setAlignment(Paragraph.ALIGN_CENTER);

        Paragraph paragraph5 = new Paragraph(Formation, fontParagraph);
        paragraph5.setAlignment(Paragraph.ALIGN_CENTER);

        String url = "./src/main/resources/static/img/QRCode.png";
        Image image = Image.getInstance(url);
        document.add(image);
        document.add(paragraph);
        document.add(paragraph2);
        document.add(paragraph3);
        document.add(paragraph4);
        document.add(paragraph5);

        document.close();

    }
    //-----------------------send email----------------------------
    public void sendSimpleEmail(String toEmail,
                                String body,
                                String subject) {
        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom("mahdijr2015@gmail.com");
        message.setTo(toEmail);
        message.setText(body);
        message.setSubject(subject);

        mailSender.send(message);
        System.out.println("Mail Send...");
    }


    //----------------send mail  with attachement-------------------------
    public void sendEmailWithAttachment(
            String body,
            String subject,
            String attachment,User user) throws MessagingException {
        String email= user.getEmail();

        MimeMessage mimeMessage = mailSender.createMimeMessage();

        MimeMessageHelper mimeMessageHelper
                = new MimeMessageHelper(mimeMessage, true);

        mimeMessageHelper.setFrom("hajjej.farouk6@gmail.com");
        mimeMessageHelper.setTo(email);
        mimeMessageHelper.setText(body);
        mimeMessageHelper.setSubject(subject);

        FileSystemResource fileSystem
                = new FileSystemResource(new File(attachment));

        mimeMessageHelper.addAttachment(fileSystem.getFilename(),
                fileSystem);

        mailSender.send(mimeMessage);
        System.out.println("Mail Send...");

    }

//-------------------Generation de QR code-----------------------------



    public static void generateQRCodeImage(String url, int width, int height, String filePath)
            throws WriterException, IOException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(url, BarcodeFormat.QR_CODE, width, height);
        Path path = FileSystems.getDefault().getPath(filePath);
        MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);

    }



}
