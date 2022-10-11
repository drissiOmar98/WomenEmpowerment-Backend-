package com.javachinna.repo;

import com.javachinna.model.Comment;
import com.javachinna.model.PostComments;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface ICommentRepo extends CrudRepository<Comment, Integer> {


}
