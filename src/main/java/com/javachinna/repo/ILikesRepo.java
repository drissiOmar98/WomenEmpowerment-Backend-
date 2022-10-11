package com.javachinna.repo;

import com.javachinna.model.Likes;
import org.springframework.data.repository.CrudRepository;

public interface ILikesRepo extends CrudRepository<Likes,Integer> {
}
