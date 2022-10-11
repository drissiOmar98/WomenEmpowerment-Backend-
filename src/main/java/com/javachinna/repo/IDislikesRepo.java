package com.javachinna.repo;

import com.javachinna.model.Dislikes;
import com.javachinna.model.Likes;
import org.springframework.data.repository.CrudRepository;

public interface IDislikesRepo extends CrudRepository<Dislikes,Integer> {
}
