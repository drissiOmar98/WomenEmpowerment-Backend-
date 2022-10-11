package com.javachinna.repo;



import com.javachinna.model.Emoji;
import com.javachinna.model.React;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReactRepository extends JpaRepository<React, Long > {

    @Query("select e from React e where e.commentUniversity.idComment=:id")
    List<React> findAllByCommentId(@Param("id") Long id);

    @Query("select e from React e where e.commentUniversity.idComment=:id and e.emoji=:em")
    List<React> findAllByCommentIdAndEmoji(@Param("id") Long idComment, @Param("em") Emoji em);

    @Query("select count(e.id) from React e where e.commentUniversity.idComment=:id")
    Long  countAllByCommentId(@Param("id") Long idComment) ;

    @Query("select  u.Name,count (r.id) from React r join r.partnerInstitution u where  r.emoji='HAPPY' group by u.idPartner ")
    public List<Object> numberHappyReactByUniversity();

    @Query("select  u.Name,count (r.id) from React r join r.partnerInstitution u where  r.emoji='ANGRY' group by u.idPartner ")
    public List<Object> numberAngryReactByUniversity();

    @Query("select  u.Name,count (r.id) from React r join r.partnerInstitution u where  r.emoji='LIKE' group by u.idPartner ")
    public List<Object> numberLikeReactByUniversity();

    @Query("select  u.Name,count (r.id) from React r join r.partnerInstitution u where  r.emoji='DISLIKE' group by u.idPartner ")
    public List<Object> numberDisLikeReactByUniversity();

    @Query("select  u.Name,count (r.id) from React r join r.partnerInstitution u where  r.emoji='SAD' group by u.idPartner ")
    public List<Object> numberSadReactByUniversity();


}
