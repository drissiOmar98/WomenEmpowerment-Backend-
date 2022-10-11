package com.javachinna.service;


import com.javachinna.model.*;
import com.javachinna.QrCode.QRCodeGenerator;
import com.javachinna.repo.*;
import com.google.zxing.WriterException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.transaction.Transactional;
import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;


@Slf4j
@Service
public class ServiceFormation implements IServiceFormation {

    @Autowired
    private UserRepository iUserRepo;

    @Autowired
    private IFormationRepo iFormationRepo;
    @Autowired
    private IResultRepo iResultRepo;
    @Autowired
    private ISearchRepo iSearchRepo;
    @Autowired
    private ICommentsRepo iCommentsRepo;
    @Autowired
    private ILikesRepo iLikesRepo;
    @Autowired
    private IDislikesRepo iDislikesRepo;
    @Autowired
    private ICertificatRepo iCertificatRepo;
    @Autowired
    private exportPdf export;
    @Autowired
    exportExcel exportExcelservice;

    private Domain d;




    @Autowired
    private SendEmailService emailSenderService;

    private static final String QR_CODE_IMAGE_PATH = "./src/main/resources/static/img/QRCode.png";
    private static  Integer nbr = 0 ;



    @Override
    public void ajouterFormateur(User formateur) {
        iUserRepo.save(formateur);
    }

    @Override
    public void addFormation(Formation formation) {
        iFormationRepo.save(formation);
    }

    @Override
    public void updateFormation(Formation formation, Integer idFormateur) {
        Formation f = iFormationRepo.findById(idFormateur).orElse(null);

        f.setTitle(formation.getTitle());
        f.setDomain(formation.getDomain());
        f.setStart(formation.getStart());
        f.setEnd(formation.getEnd());
        f.setFrais(formation.getFrais());
        f.setNbrHeures(formation.getNbrHeures());
        f.setNbrMaxParticipant(formation.getNbrMaxParticipant());

        //  formation.setFormateur(formateur);
        iFormationRepo.save(f);
    }

    @Override
    public void deleteFormation(Integer idFormation) {
        Formation f = iFormationRepo.findById(idFormation).orElse(null);
        iFormationRepo.delete(f);
    }


    @Override
    public List<Formation> afficherFormation() {

        List<Formation> f =   (List<Formation>)iFormationRepo.findAll();
            return  f;
    }



    @Override
    public List<User> afficherFormateur() {

       return iUserRepo.getFormateur();
    }

    @Override
    public List<User> afficherApprenant() {
        return iUserRepo.getApprenant();
    }

    @Override
    public User FormateurwithMaxHo() {
        return iUserRepo.FormateurwithMaxHo();
    }


    @Override
   // @Scheduled(cron = "0 0/1 * * * *")
    @Scheduled(cron = "0 0 9 28 * ?")
    public User getFormateurRemunerationMaxSalaire() throws MessagingException {


        int max = 0;

        TreeMap<Integer, String> map = new TreeMap<>();

        User u = new User();

        LocalDate currentdDate1 = LocalDate.now();
        Date date = new Date();

        boolean status = false;


        Calendar calLast = Calendar.getInstance();
        Calendar calFirst = Calendar.getInstance();
        calLast.set(Calendar.DATE, calLast.getActualMaximum(Calendar.DATE));
        calFirst.set(Calendar.DATE, calFirst.getActualMinimum(Calendar.DATE));

        Date lastDayOfMonth = calLast.getTime();
        Date firstDayOfMonth = calFirst.getTime();


        ZoneId defaultZoneId = ZoneId.systemDefault();

        Date dd = Date.from(currentdDate1.plusMonths(1).atStartOfDay(defaultZoneId).toInstant());
        Date df = Date.from(currentdDate1.plusDays(15).atStartOfDay(defaultZoneId).toInstant());


        log.info("start : " + firstDayOfMonth);
        log.info("last : " + lastDayOfMonth);

        if (firstDayOfMonth.equals(firstDayOfMonth)) {
            status = true;
        }


      //   if(lastDayOfMonth.equals(lastDayOfMonth))
       //  {
        for (Formation f : this.iFormationRepo.findAll()) {
            if (f.getStart().after(firstDayOfMonth) && f.getEnd().before(lastDayOfMonth)) {

                map.put(this.iFormationRepo.getFormateurRemunerationByDate(f.getFormateur().getId(), firstDayOfMonth, lastDayOfMonth), f.getFormateur().getId().toString());
                if (this.iFormationRepo.getFormateurRemunerationByDate(f.getFormateur().getId(), firstDayOfMonth, lastDayOfMonth) > max) {
                    max = this.iFormationRepo.getFormateurRemunerationByDate(f.getFormateur().getId(), firstDayOfMonth, lastDayOfMonth);
                }
            }
        }

        if (status) {


            for (Formation f : this.iFormationRepo.findAll()) {

                for (User user : iUserRepo.getFormateurByFormation(f.getIdFormation())) {
                    user.setSalary(this.iFormationRepo.getFormateurRemunerationByDate(user.getId(), firstDayOfMonth, lastDayOfMonth));
                    iUserRepo.save(user);
                }
            }
            log.info(" liste" + map);
            log.info(" Max Salaire " + max);

            for (Formation f : this.iFormationRepo.findAll()) {
                if (f.getStart().after(firstDayOfMonth) && f.getEnd().before(lastDayOfMonth)) {
                    if (this.iFormationRepo.getFormateurRemunerationByDate(f.getFormateur().getId(), firstDayOfMonth, lastDayOfMonth) == max) {

                        u = this.iUserRepo.findById(f.getFormateur().getId()).orElse(null);
                    }
                }
            }

            u.setSalary(max + 200 );
            iUserRepo.save(u);
            this.emailSenderService.sendSimpleEmailWithFils(u.getEmail(), "we have max houre of travel ", "we have max houre of travel we elevate salary with 200 $   Your salary  " + u.getSalary()+ "$  Name " + u.getLastName() + "--" + u.getFirstName() + " . ","/Users/macos/IdeaProjects/springPidev/src/main/resources/static/mybadges/goldbadge.png");

            return u;
        }


     // }
      return null;

    }


    public TreeMap<Integer, User> getFormateurRemunerationMaxSalaireTrie() {

        TreeMap<Integer, User> map = new TreeMap<>();



        LocalDate currentdDate1 =  LocalDate.now();

        ZoneId defaultZoneId = ZoneId.systemDefault();

        Date dd =Date.from(currentdDate1.minusDays(15).atStartOfDay(defaultZoneId).toInstant());
        Date df =Date.from(currentdDate1.plusDays(15).atStartOfDay(defaultZoneId).toInstant());

        for (Formation f: this.iFormationRepo.findAll()) {
            if (f.getStart().after(dd) && f.getEnd().before(df) )
            {
                map.put(this.iFormationRepo.getFormateurRemunerationByDate(f.getFormateur().getId(),dd,df),f.getFormateur());

            }

        }
      //  List<Map.Entry<Integer, User>> singleList = map.entrySet().stream().collect(Collectors.toList());
        return map;
    }

    @Override
    public List<Object> getFormateurRemunerationByDateTrie() {
        LocalDate currentdDate1 =  LocalDate.now();



        ZoneId defaultZoneId = ZoneId.systemDefault();

        Date dd =Date.from(currentdDate1.minusDays(15).atStartOfDay(defaultZoneId).toInstant());
        Date df =Date.from(currentdDate1.plusDays(15).atStartOfDay(defaultZoneId).toInstant());

        return this.iFormationRepo.getFormateurRemunerationByDateTrie(dd,df);
    }

    @Override
    @Scheduled(cron = "0 0/2 * * * *")
    public void CertifactionStudents() {

        boolean status = false;
        boolean fin;

        String Path="";


        try {


            for (Formation f : iFormationRepo.findAll())
            {
                for (User u : iUserRepo.getApprenantByFormation(f.getIdFormation()))
                {
                    if(iResultRepo.getScore(u.getId()) >= 200 && iResultRepo.getScore(u.getId()) <=250 && iResultRepo.getNbrQuiz(u.getId()) == 5 )
                    {
                        log.info( " Status"+iResultRepo.getNbrQuiz(u.getId()));
                        fin=false;

                        for (Result r : iResultRepo.getResultByIdUAndAndIdF(u.getId(),f.getIdFormation()))
                        {

                            if (!r.isStatus())
                            {

                                r.setStatus(true);
                                iResultRepo.save(r);
                                status=true;
                            }

                            if (status && !fin)
                            {
                                log.info( " Status  true ");

                                Certificat certificat = new Certificat();

                                certificat.setFormation(f);
                                certificat.setDate(new Date());
                                certificat.setName(u.getDisplayName());
                                certificat.setPath("/Users/macos/IdeaProjects/springPidev/src/main/resources/static/Certif/C"+u.getId()+".pdf");
                                certificat.setUser(u);

                                iCertificatRepo.save(certificat);

                                if(this.iUserRepo.getApprenantWithScore(f.getIdFormation()).get(0).getId().equals(u.getId()))
                                {
                                    Path = "/Users/macos/IdeaProjects/springPidev/src/main/resources/static/mybadges/goldbadge.png";
                                }else if(this.iUserRepo.getApprenantWithScore(f.getIdFormation()).get(1).getId().equals(u.getId())){
                                    Path = "/Users/macos/IdeaProjects/springPidev/src/main/resources/static/mybadges/silverbadge.png";
                                }else if(this.iUserRepo.getApprenantWithScore(f.getIdFormation()).get(2).getId().equals(u.getId()))
                                {
                                    Path = "/Users/macos/IdeaProjects/springPidev/src/main/resources/static/mybadges/bronzebadge.png";
                                }

                                log.info(Path);


                                export.pdfReader(f,u,Path);
                                QRCodeGenerator.generateQRCodeImage(f.getDomain().toString(),150,150,QR_CODE_IMAGE_PATH);
                                this.emailSenderService.sendSimpleEmailWithFils(u.getEmail()," Congratulations Mr's : "+u.getLastName()+" "+u.getFirstName()+" you have finished your Courses  " ," Certification At : "+ new Date()+"  in Courses of Domain "+f.getDomain()+" "+" And Niveau : " +f.getLevel() +" .","/Users/macos/IdeaProjects/springPidev/src/main/resources/static/Certif/C"+u.getId()+".pdf");
                                fin=true; /// return /////
                            }


                        }

                    }

                }

            }

        } catch (WriterException | IOException | MessagingException e) {

            e.printStackTrace();
        }



    }

    @Override
    public List<Formation> SearchMultiple(String key) {

        if (key.equals(""))
        {
            return (List<Formation>) iFormationRepo.findAll();
        }else
        {
            return iFormationRepo.rech(key);
        }

    }



    @Override
    public void ajouterApprenant(User apprenant) {
            iUserRepo.save(apprenant);
    }

    @Override
    public void ajouterEtAffecterFormationAFormateur(Formation formation, Long idFormateur) {

        User formateur = iUserRepo.findById(idFormateur).orElse(null);

        LocalDate currentdDate1 =  LocalDate.now();

        ZoneId defaultZoneId = ZoneId.systemDefault();

        Date dd =Date.from(currentdDate1.minusMonths(3).atStartOfDay(defaultZoneId).toInstant());
        Date df =Date.from(currentdDate1.plusMonths(3).atStartOfDay(defaultZoneId).toInstant());

            if (this.iFormationRepo.nbrCoursesParFormateur(idFormateur,dd,df) <2 && formateur.getProfession() == Profession.FORMER.FORMER)
            {
                this.emailSenderService.sendSimpleEmail(formateur.getEmail(),"Congratulations Mr's : NAME : "+formateur.getLastName() +" "+formateur.getFirstName() +" ." ," We accepted you to teach with us this courses of "+formation.getDomain()+" this courses start at : "+formation.getStart()+" and finish "+formation.getEnd()+" .");
                formation.setRating(0.0);

                if(formation.getNbrMaxParticipant()>20 && !formation.getDomain().equals(d))
                {
                    formation.setNbrMaxParticipant(20);
                    formation.setFormateur(formateur);
                    iFormationRepo.save(formation);
                    this.emailSenderService.sendSimpleEmail(formateur.getEmail(),"Max number of leaner in this domain "+formation.getDomain()+" " ,"we have 20 (Max number of leaner ) Mr's  : "+formateur.getLastName() +" "+formateur.getFirstName() +" .");

                }else if (formation.getNbrMaxParticipant()<=30 && formation.getDomain().equals(d)){
                    formation.setFormateur(formateur);
                    iFormationRepo.save(formation);
                    this.emailSenderService.sendSimpleEmail(formateur.getEmail(),"in this domain We access 30 leaner "+formation.getDomain()+" " ,"we have 30 (Max number of leaner ) Mr's  : "+formateur.getLastName() +" "+formateur.getFirstName() +" .");
                }else {
                    formation.setFormateur(formateur);
                    iFormationRepo.save(formation);
                }


            }else
            {
                this.emailSenderService.sendSimpleEmail(formateur.getEmail(),"we don't have acces to have two coursus in same semester " ,"we have 2 (MAX formation in this semester) NAME : "+formateur.getLastName() +" "+formateur.getFirstName() +" .");
                log.info("we have 2 (MAX formation in this Semester ");
            }

    }


    public Formation getFile(Integer fileId) throws FileNotFoundException {
        return iFormationRepo.findById(fileId).orElseThrow(() -> new FileNotFoundException("File not found with id " + fileId));
    }


    @Override
    public void affecterApprenantFormationWithMax(Long idApprenant, Integer idFormation) {

        Formation formation = iFormationRepo.findById(idFormation).orElse(null);

        User apprenant = iUserRepo.findById(idApprenant).orElse(null);

        LocalDate currentdDate1 =  LocalDate.now();
        User user = new User();

        ZoneId defaultZoneId = ZoneId.systemDefault();

        Date dd =Date.from(currentdDate1.minusMonths(3).atStartOfDay(defaultZoneId).toInstant());
        Date df =Date.from(currentdDate1.plusMonths(3).atStartOfDay(defaultZoneId).toInstant());

        ///User with gifts Free for Max Score



    if(apprenant.getState() == State.DISCIPLINED)
    {
        if(iResultRepo.getScore(idApprenant)==null)
        {
            if (iFormationRepo.getNbrApprenantByFormationId(idFormation) < formation.getNbrMaxParticipant() && apprenant.getProfession() == Profession.LEARNER) {

                if (iFormationRepo.getNbrFormationByApprenant(idApprenant, formation.getDomain(), dd, df) < 2 ) {

                    log.info("nbr "+iFormationRepo.getNbrFormationByApprenant(idApprenant, formation.getDomain(), dd, df));
                    formation.getApprenant().add(apprenant);
                    iFormationRepo.save(formation);


                } else {
                    this.emailSenderService.sendSimpleEmail(apprenant.getEmail(), "we don't have acces to add two coursus in same domain ", "we have 2 (MAX formation in this domain) NAME : " + apprenant.getLastName() + " " + apprenant.getFirstName() + " .");
                    log.info("this apprenant we have 2 (MAX formation in this domain ");
                }
            } else {
                this.emailSenderService.sendSimpleEmail(apprenant.getEmail(), "Learner list complete  ", " We have in this courses " + formation.getNbrMaxParticipant() + " number of learner Maximum" + apprenant.getLastName() + " - " + apprenant.getFirstName() + "  .");
                log.info(" Learner list complete Max learner " + formation.getNbrMaxParticipant());
            }

        }else
        {
            for(Formation form : iFormationRepo.findAll()) {
                if(iUserRepo.getApprenantWithScoreForGifts(form.getIdFormation()).size()!=0)
                {
                    user = iUserRepo.getApprenantWithScoreForGifts(form.getIdFormation()).get(0);
                    //}


                    if (iFormationRepo.getNbrApprenantByFormationId(idFormation) < formation.getNbrMaxParticipant() && apprenant.getProfession() == Profession.LEARNER) {

                        if (iFormationRepo.getNbrFormationByApprenant(idApprenant, formation.getDomain(), dd, df) < 2 || apprenant.getId().equals(user.getId())) {
                            if (iFormationRepo.getNbrFormationByApprenant(idApprenant, formation.getDomain(), dd, df) < 3) {

                                log.info("nbr "+iFormationRepo.getNbrFormationByApprenant(idApprenant, formation.getDomain(), dd, df));
                                formation.getApprenant().add(apprenant);
                                iFormationRepo.save(formation);
                            } else {
                                log.info("this apprenant we have 3 (MAX formation in this domain ");
                                this.emailSenderService.sendSimpleEmail(apprenant.getEmail(), "we don't have acces to add two coursus in same domain ", "we have 2 (MAX formation in this domain) NAME : " + apprenant.getLastName() + " " + apprenant.getFirstName() + " .");
                            }

                        } else {
                            this.emailSenderService.sendSimpleEmail(apprenant.getEmail(), "we don't have acces to add two coursus in same domain ", "we have 2 (MAX formation in this domain) NAME : " + apprenant.getLastName() + " " + apprenant.getFirstName() + " .");
                            log.info("this apprenant we have 2 (MAX formation in this domain ");
                        }
                    } else {
                        this.emailSenderService.sendSimpleEmail(apprenant.getEmail(), "Learner list complete  ", " We have in this courses " + formation.getNbrMaxParticipant() + " number of learner Maximum" + apprenant.getLastName() + " - " + apprenant.getFirstName() + "  .");
                        log.info(" Learner list complete Max learner " + formation.getNbrMaxParticipant());
                    }
                }


            }
        }

    }else
    {
        formation.getApprenant().remove(apprenant);
        iFormationRepo.save(formation);
        this.emailSenderService.sendSimpleEmail(apprenant.getEmail(), "You are (PUNISHED/EXCLUDED) ", " we don't have access to add new courses you are (PUNISHED/EXCLUDED)" + apprenant.getLastName() + " - " + apprenant.getFirstName() + "  .");
        log.info(" no add  " + apprenant.getState());
    }

    }


   // @Scheduled(cron = "0 0/1 * * * *")
    public void ListComplete()
    {
        for(Formation f : iFormationRepo.findAll())
        {
            if (iFormationRepo.getNbrApprenantByFormationId(f.getIdFormation()) < f.getNbrMaxParticipant())
            {
                this.emailSenderService.sendSimpleEmail("mahdi.arfaoui1@esprit.tn", "Learner list no complete   ", " we have in this courses "+iFormationRepo.getNbrApprenantByFormationId(f.getIdFormation()) +" learner in this formation "+f.getTitle() +" " + f.getDomain());
                log.info(" we have access to add this courses " + f.getNbrMaxParticipant());
            }else {
                this.emailSenderService.sendSimpleEmail("mahdi.arfaoui1@esprit.tn", "Learner list complete Max    ", " we have in this courses "+iFormationRepo.getNbrApprenantByFormationId(f.getIdFormation()) +" learner  in this formation "+f.getTitle() +" " + f.getDomain());
                log.info(" Learner list complete Max learner " + f.getNbrMaxParticipant());
            }
        }
    }

/*
    @EventListener(ApplicationReadyEvent.class)
    public void sendMail() throws MessagingException {
        emailSenderService.sendSimpleEmailWithFils("mahdijr2015@gmail.com","we don't add two coursus in same domain " ,"this apprenant we have 2 (MAX formation in this domain","/Users/macos/Downloads/Examen-SOA-Révision.pdf");
    }
*/


    ///////////////  Affectation 3adiya  ////////////////////
    @Override
    public void affecterApprenantFormation(Long idApprenant, Integer idFormation) {
        User apprenant = iUserRepo.findById(idApprenant).orElse(null);
        Formation formation = iFormationRepo.findById(idFormation).orElse(null);

        formation.getApprenant().add(apprenant);
        iFormationRepo.save(formation);
    }



    @Override
    public Integer nbrCoursesParFormateur(Long idF,Date dateDebut, Date dateFin) {
        return this.iFormationRepo.nbrCoursesParFormateur(idF, dateDebut, dateFin);
    }

    @Override
    public Integer getNbrApprenantByFormation(String title) {
        return  iFormationRepo.getNbrApprenantByFormation(title);
    }


    @Override
   // @Scheduled(cron = "*/30 * * * * *")
    public void getNbrApprenantByFormationn() {

        log.info("La formation : Spring contient : " +iFormationRepo.getNbrApprenantByFormation("Spring") + " Apprenant ");
        log.info("La formation : Devops contient : " +iFormationRepo.getNbrApprenantByFormation("DevOps") + " Apprenant ");

    }

    @Override
    public Integer getNbrFormationByApprenant(Long idApp , Domain domain, Date dateDebut, Date dateFin) {
        return iFormationRepo.getNbrFormationByApprenant(idApp,domain, dateDebut, dateFin);
    }

    @Override
    public List<Object[]> getNbrApprenantByFormation() {

        return iUserRepo.getNbrApprenantByFormation();
    }

    @Override
    public List<User> getApprenantByFormation(Integer idF) {
        return iUserRepo.getApprenantByFormation(idF);
    }

    @Override
    public Integer getFormateurRemunerationByDate(Long idFormateur, Date dateDebut, Date dateFin) {

        return iFormationRepo.getFormateurRemunerationByDate(idFormateur, dateDebut, dateFin);

    }


    @Override
    public Integer getRevenueByFormation(Integer idFormation) {
        Formation f = iFormationRepo.findById(idFormation).orElse(null);

        Integer revenue =  (f.getFrais()*iUserRepo.getRevenueByFormation(idFormation).size());
        return  revenue;
    }

    ///////////       Comments ///////////////
    @Override
    public void likeComments(Integer idC ){
        PostComments post = iCommentsRepo.findById(idC).orElse(null);
      //  User user = iUserRepo.findById(idU).orElse(null);
        Likes likes = new Likes();


        if(post.getLikes().size() == 0)
        {
            likes.setPostComments(post);
          //  likes.setUser(user);
            likes.setNbrLikes(1);
            likes.setCreateAt(new Date());
            iLikesRepo.save(likes);
        }
        else{
            Likes l = iLikesRepo.findById(post.getLikes().stream().findFirst().get().getId()).orElse(null);
                l.setNbrLikes(l.getNbrLikes()+1);
                l.setCreateAt(new Date());
                iLikesRepo.save(l);
        }

    }



    @Override
    public void dislikeComments(Integer idC ) {
        PostComments post = iCommentsRepo.findById(idC).orElse(null);
      //  User user = iUserRepo.findById(idU).orElse(null);
        Dislikes dislikes = new Dislikes();

        if(post.getDislikes().size() == 0)
        {

            dislikes.setPostComments(post);
          //  dislikes.setUser(user);
            dislikes.setNbrDislikes(1);
            dislikes.setCreateAt(new Date());
            iDislikesRepo.save(dislikes);
        }
        else{
            Dislikes d = iDislikesRepo.findById(post.getDislikes().stream().findFirst().get().getId()).orElse(null);
            d.setNbrDislikes(d.getNbrDislikes()+1);
            d.setCreateAt(new Date());
            iDislikesRepo.save(d);

        }

    }


    //////////////////// Courses //////////////////////

    @Override
    public void FormationWithRate(Integer idF, Double rate) {
        Formation formation = iFormationRepo.findById(idF).orElse(null);

        if(formation.getRating()==0)
        {
            formation.setRating(rate);
        }else {
            formation.setRating(((formation.getRating()+rate)/2.0));
        }
       
        iFormationRepo.save(formation);
    }




    //////////////// Search historique ////////////////

    @Override
    public void SearchHistorique(String keyword) {

        if(keyword!=null)
        {
            String s = iSearchRepo.keyWord(keyword);
            Search search = new Search();
            search.setKeyword(s);
            search.setAtDate(new Date());
            iSearchRepo.save(search);
        }

    }


    @Override
    public void addComments(PostComments postComments,Integer idF,Long idUser) {

        Formation formation = iFormationRepo.findById(idF).orElse(null);
        User user = iUserRepo.findById(idUser).orElse(null);
        postComments.setUserC(user);
        postComments.setFormation(formation);
        iCommentsRepo.save(postComments);
    }

    @Override
    public void deleteComments(Integer idC) {
        log.info("In methode deleteComment");
        log.warn("Are you sure you want to delete Comment");
        iCommentsRepo.deleteById(idC);
        log.error("exeption");
    }

    @Override
    public PostComments upDateComment(PostComments postComments,Integer idF,Long idUser) {

        User user =  iUserRepo.findById(idUser).orElse(null);
        Formation f = iFormationRepo.findById(idF).orElse(null);
        postComments.setFormation(f);
        postComments.setUserC(user);
        return iCommentsRepo.save(postComments);
    }

    @Override
    public List<PostComments> getAllComments() {
        return (List<PostComments>) iCommentsRepo.findAll();
    }


    @Override
    @Scheduled(cron = "0 0/2 * * * *")
    @Transactional
    public void LeanerStatus() {

        for(User user: iUserRepo.findAll())
        {
            if (user != iUserRepo.findById(1L).orElse(null) )
            {
                if(iUserRepo.nbrCommentsBadByUser(user.getId())==1 && user.getState()!=State.WARNED)
                {
                    this.emailSenderService.sendSimpleEmail(user.getEmail(), " You Are create bad Comment in this Courses ", " You Are create bad Comment in this Courses next comment with bad word we punished 20 day please Mr's  "+user.getLastName() +" " + user.getLastName() +" this web site is for association of women empowerment not to write this type of comment !!! ");
                    user.setState(State.WARNED);
                    iUserRepo.save(user);
                }else if(iUserRepo.nbrCommentsBadByUser(user.getId())==2 && user.getState()!=State.PUNISHED) {
                    this.emailSenderService.sendSimpleEmail(user.getEmail(), " You Are create bad Comment in this Courses ", " You Are create Comment with bad word in this Courses we punished in all Courses   Mr's  "+user.getLastName() +" " + user.getLastName()+" this web site is for association of women empowerment not to write this type of comment !!!! ");
                    user.setState(State.PUNISHED);
                    iUserRepo.save(user);
                }else if(iUserRepo.nbrCommentsBadByUser(user.getId())>=3 && user.getState()!=State.EXCLUDED) {

                    this.emailSenderService.sendSimpleEmail(user.getEmail(), " You Are create bad Comment in this Courses ", " You Are create Comment with bad word in this Courses we excluded in all Courses   Mr's  "+user.getLastName() +" " + user.getLastName()+" this web site is for association of women empowerment not to write this type of comment !!!! ");

                    for (Formation f : iFormationRepo.listFormationParApprenant(user.getId()))
                    {
                        if(user.getState() == State.EXCLUDED)
                        {
                            f.getApprenant().remove(user);
                            iFormationRepo.save(f);
                        }
                    }
                    for (PostComments p : iCommentsRepo.listeCommentParUser(user.getId()))
                    {
                        iCommentsRepo.deleteById(p.getIdComn());
                    }

                    user.setState(State.EXCLUDED);
                    iUserRepo.save(user);

                }
            }
        }
    }


    @Override
    @Scheduled(cron = "0 0/1 * * * *")
    public void decisionUserPUNISHED() {

        Date date = new Date();

        for (User user : iUserRepo.findAll())
        {
            if (user.getState()==State.PUNISHED)
            {
            for (PostComments p : iUserRepo.CommentsByUser(user.getId()))
            {
                Date period20J = new Date(p.getCreateAt().getTime() + (1000 * 60 * 60 * 48));
                if(date.after(period20J) && iUserRepo.nbrCommentsBadByUser(user.getId()) >=1)
                {
                    user.setState(State.DISCIPLINED);
                    iUserRepo.save(user);
                    iCommentsRepo.deleteById(p.getIdComn());
                }
            }
            }
        }
    }

    @Override
   // @Scheduled(cron = "0 0/2 * * * *")
    @Scheduled(cron = "0 0 9 28 * ?")
    public Map<String,Double> PourcentageCoursesByDomain() throws IOException, MessagingException {

        Map<String,Double> pourcentages=new HashMap<>();

        List<Double> pourcent = new ArrayList<>();

        Calendar calLast = Calendar.getInstance();
        Calendar calFirst = Calendar.getInstance();
        calLast.set(Calendar.DATE, calLast.getActualMaximum(Calendar.DATE));
        calFirst.set(Calendar.DATE, calFirst.getActualMinimum(Calendar.DATE));

        Date lastDayOfMonth = calLast.getTime();
        Date firstDayOfMonth = calFirst.getTime();


        double IT = 0;
        double ART=0;
        double CINEMA=0;
        double MUSIC=0;
        double DANCE=0;
        double PHY=0;
        double ECONOMIC=0;
        double MARKETING=0;

        List<Formation> formations=  (List<Formation>) iFormationRepo.findAll();

        for (Formation formation: iFormationRepo.listformationByDate(firstDayOfMonth,lastDayOfMonth)) {
            if(formation.getEnd().before(new Date()))
            {
                if (formation.getDomain().equals(Domain.IT)) {
                    IT++;
                }
                else if (formation.getDomain().equals(Domain.ART)) {
                    ART++;}

                else if (formation.getDomain().equals(Domain.CINEMA)) {
                    CINEMA++;}

                else if (formation.getDomain().equals(Domain.DANCE)) {
                    DANCE++;}
                else if (formation.getDomain().equals(Domain.PHY)) {
                    PHY++;}
                else if (formation.getDomain().equals(Domain.ECONOMIC)) {
                    ECONOMIC++;}
                else if (formation.getDomain().equals(Domain.MARKETING)) {
                    MARKETING++;}
                else if (formation.getDomain().equals(Domain.MUSIC)) {
                    MUSIC++;}
            }
        }

        if (formations.size() !=0) {

            System.out.println("Number Courses: "+formations.size());

            IT =  ((IT/(formations.size()))*100);

            ART = ((ART/formations.size()))*100;

            CINEMA = ((CINEMA/formations.size()))*100;

            DANCE = ((DANCE/formations.size()))*100;

            PHY = ((PHY/formations.size()))*100;

            ECONOMIC = ((ECONOMIC/formations.size()))*100;

            MARKETING = ((MARKETING/formations.size()))*100;

            MUSIC = ((MUSIC/formations.size()))*100;

        }

        pourcent.add(IT);
        pourcent.add(ART);
        pourcent.add(CINEMA);
        pourcent.add(DANCE);
        pourcent.add(PHY);
        pourcent.add(ECONOMIC);
        pourcent.add(MARKETING);
        pourcent.add(MUSIC);

        pourcentages.put("IT",IT);

        pourcentages.put("ART",ART);

        pourcentages.put("CINEMA",CINEMA);

        pourcentages.put("DANCE",DANCE);

        pourcentages.put("PHY",PHY);

        pourcentages.put("ECONOMIC",ECONOMIC);

        pourcentages.put("MARKETING",MARKETING);

        pourcentages.put("MUSIC",MUSIC);

        System.out.println(pourcentages);

        Random r = new Random();
        Integer RandNbr = r.nextInt(100);


        Double max = Collections.max(pourcent,null);



        System.out.println(max(pourcentages));

        String domain = max(pourcentages);

        d = Domain.valueOf(domain);



        for (Formation fr : iFormationRepo.listFormationByDomain(Domain.valueOf(domain)))
        {
            fr.setNbrMaxParticipant(30);
            iFormationRepo.save(fr);
        }

        nbr +=1;

        ByteArrayInputStream stream = exportExcelservice.percentageExportExcel(pourcent);

        FileOutputStream out = new FileOutputStream("/Users/macos/IdeaProjects/springPidev/src/main/resources/static/Result"+nbr+".xlsx");

        byte[] buf = new byte[1024];
        int len;
        while ((len = stream.read(buf)) > 0) {
            out.write(buf, 0, len);
        }
        this.emailSenderService.sendSimpleEmailWithFils("mahdijr2015@gmail.com","You have pourcentage of max domain Courses " ," you have in this month "+max+" % of leaner pick courses with domain : "+max(pourcentages)+" then we  access to augment  number max of leaner in all courses with domain "+max(pourcentages)+" .","/Users/macos/IdeaProjects/springPidev/src/main/resources/static/Result"+nbr+".xlsx");

        return pourcentages;
    }


    public <String, Double extends Comparable<Double>> String max(Map<String, Double> map) {
        Optional<Map.Entry<String, Double>> maxEntry = map.entrySet()
                .stream()
                .max((Map.Entry<String, Double> e1, Map.Entry<String, Double> e2) -> e1.getValue()
                        .compareTo(e2.getValue())
                );

        return  maxEntry.get().getKey();
    }





}
