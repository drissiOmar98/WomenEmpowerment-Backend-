package com.javachinna.service;

import com.javachinna.model.DataPoint;
import com.javachinna.model.Emoji;
import com.javachinna.model.React;

import java.util.List;

public interface IReactService {
    public React save(Long id, React react);
    public React addReactForUniversity(long id , React react , Integer idUniversity);
    List<React> findAllByCommentId(Long id);
    List<React> findAllByCommentIdAndEmoji(Long idComment, Emoji em);
    Long  countAllByCommentId(Long idComment) ;
    public List<DataPoint> statNumberHappyReactsByUniversity();
    public List<DataPoint> statNumberAngryReactsByUniversity();
    public List<DataPoint> statNumberLikeReactsByUniversity();
    public List<DataPoint> statNumberDislikeReactsByUniversity();
    public List<DataPoint> statNumberSadReactsByUniversity();
}
