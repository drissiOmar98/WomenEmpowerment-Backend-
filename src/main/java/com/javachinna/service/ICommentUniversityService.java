package com.javachinna.service;

import com.javachinna.model.CandidacyUniversity;
import com.javachinna.model.CommentUniversity;

import java.util.List;

public interface ICommentUniversityService {
    public CommentUniversity save (CommentUniversity commentUniversity);
    public void addCommentAndAffectToStudentAndUniversity(CommentUniversity commentUniversity, Long idStudent, Integer idUniversity);
    public CommentUniversity Update(CommentUniversity commentUniversity);
    public List<CommentUniversity> findAll();
    public CommentUniversity findById(Long id);
    public void deleteById(Long id);
    public List<CommentUniversity> findAllByUniversityId(Integer id);
    public void addSignal(CommentUniversity com ,Long IdComment);
    public boolean existId(Long idComment);

    public CommentUniversity getCommentById(Long IdComment);





}
