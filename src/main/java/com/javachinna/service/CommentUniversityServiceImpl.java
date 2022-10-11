package com.javachinna.service;

import com.javachinna.model.CommentUniversity;
import com.javachinna.model.PartnerInstitution;
import com.javachinna.model.Profession;
import com.javachinna.model.User;
import com.javachinna.repo.CommentUniversityRepository;
import com.javachinna.repo.IPartnerRepository;
import com.javachinna.repo.UserRepository;
import com.javachinna.sentimentAnalyzer.Sentiment;
import com.javachinna.sentimentAnalyzer.SentimentResult;
import com.javachinna.util.FilterBW;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class CommentUniversityServiceImpl implements ICommentUniversityService{
    @Autowired
    CommentUniversityRepository commentrepo;
    @Autowired
    UserRepository userrepo;

    @Autowired
    IPartnerRepository partnerrepo;

    @Autowired
    SendEmailService emailService;
    @Autowired
    Sentiment sentimentService;

    @Override
    public CommentUniversity save(CommentUniversity commentUniversity) {
        return commentrepo.save(commentUniversity);
    }

    @Override
    @Transactional
    public void addCommentAndAffectToStudentAndUniversity(CommentUniversity commentUniversity, Long idStudent, Integer idUniversity) {
        User student = userrepo.findById(idStudent).orElse(null);
        PartnerInstitution university=partnerrepo.findById(idUniversity).orElse(null);
        FilterBW.loadConfigs();
        assert student != null;
        Date date = new Date();
        int b = student.getProfession().compareTo(Profession.STUDENT);
        if(b==0 && student.getBanned()<3){
            if(FilterBW.filterText(commentUniversity.getComment()).size() > 0){
                commentUniversity.setComment(FilterBW.filterWord(commentUniversity.getComment()));
                commentUniversity.setUser(student);
                commentUniversity.setDateComment(date);
                commentUniversity.setPartnerInstitution(university);
                commentrepo.save(commentUniversity);
                student.setBanned(student.getBanned()+1);
                userrepo.save(student);
                if(student.getBanned()<3){
                    String text = "Hello your comment:"+ commentUniversity.getIdComment() +"contain bad words so attention next time you will be banned";
                    emailService.sendSimpleEmail(student.getEmail(),text,"Warning Bad Words Detected");
                }
                else {
                    String text = "you are banned ";
                    emailService.sendSimpleEmail(student.getEmail(),text,"Banned ");
                }
            }else{
                commentUniversity.setUser(student);
                commentUniversity.setDateComment(date);
                commentUniversity.setPartnerInstitution(university);
                commentrepo.save(commentUniversity);
                SentimentResult text =sentimentService.analyze(commentUniversity.getComment());
                String text1 = "this added comment :\n"+ commentUniversity.getComment()+"\n"+" " +
                        "sent : \n "+ commentUniversity.getDateComment()+"\n"+" "+
                        "has Score : "+text.getScore()+"\n"+" "+
                        "and State : "+text.getState()+"\n"+" "+
                        "and Detected words are : \n "+text.getDetectedWords()+"\n";
                assert university != null;
                emailService.sendSimpleEmail(university.getEmail(),text1, "Sentiment Analyzer for added Comment");

            }
        }
    }

    @Override
    public CommentUniversity Update(CommentUniversity commentUniversity) {
        return commentrepo.save(commentUniversity);
    }

    @Override
    public List<CommentUniversity> findAll() {
        return commentrepo.findAll();
    }

    @Override
    public CommentUniversity findById(Long id) {
        return commentrepo.findById(id).get();
    }

    @Override
    public void deleteById(Long id) {
       commentrepo.deleteById(id);

    }

    @Override
    public List<CommentUniversity> findAllByUniversityId(Integer id) {
        return commentrepo.findByUniversityId(id);
    }

    @Override
    public void addSignal(CommentUniversity com,Long IdComment) {
        if (this.existId(IdComment)){
            CommentUniversity comm = this.getCommentById(IdComment);
            comm.setNbrSignal(comm.getNbrSignal()+1);
            commentrepo.save(comm);
            if(checkSignaledComment(IdComment)==true){
                commentrepo.deleteById(IdComment);
            }
        }




    }

    @Override
    public boolean existId(Long idComment) {
        return commentrepo.existsById(idComment);
    }

    @Override
    public CommentUniversity getCommentById(Long IdComment) {
        CommentUniversity comment=commentrepo.findById(IdComment).orElse(null);
        return comment;
    }


    public boolean checkSignaledComment(Long IdComment) {
        CommentUniversity commentUniversity =commentrepo.findById(IdComment).orElse(null);
        assert commentUniversity != null;
        if(commentUniversity.getNbrSignal()>4){
            String text = "your comment has been deleted due to many signal buy other students";
            emailService.sendSimpleEmail(commentUniversity.getUser().getEmail(),"Comment Signaled Deleted",text);
            return true;
        }
        return false;

    }


}
