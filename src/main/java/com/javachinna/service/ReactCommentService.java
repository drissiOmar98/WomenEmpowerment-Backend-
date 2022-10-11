package com.javachinna.service;

import com.javachinna.model.*;
import com.javachinna.repo.ICommentRepo;
import com.javachinna.repo.IReactRepo;
import com.javachinna.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
@Service
public class ReactCommentService implements IReactCommentService {

    @Autowired
    IReactRepo iReactRepo;
    @Autowired
    ICommentRepo iCommentRepo;
    @Autowired
    UserRepository userrepo;


    @Override
    public void addReact(ReactComment reactComment) {
        iReactRepo.save(reactComment);
    }


    @Override
    @Transactional
    public void addReactAndAffectToUserAndComment(TypeRating typeRating, Long idUser, Integer idComment) {
        ReactComment reactComment = new ReactComment();
        reactComment.setTypeRating(typeRating);

        User user = userrepo.findById(idUser).orElse(null);
        Comment comment = iCommentRepo.findById(idComment).orElse(null);

        reactComment.setUser(user);
        reactComment.setComment(comment);
        iReactRepo.save(reactComment);

        }

    @Override
    public List<ReactComment> getReactByCommentAndUser(Integer idComment, Long idUser) {
        return iReactRepo.getReactByCommentAndUser(idComment,idUser);
    }


}
