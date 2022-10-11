package com.javachinna.repo;

import com.javachinna.model.Domain;
import com.javachinna.model.Formation;
import com.javachinna.model.Subscription;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface SubscriptionRepo extends CrudRepository<Subscription, Integer> {

    @Query("select count(subs) from Subscription subs join subs.users u where u.id=:id")
    int GetNbrSubscriptionByUser(@Param("id") Long idUser);

    @Query(value = "select COUNT (u.id) from User  u join u.subscs sub where sub.idSubscription=:id")
    Integer getNberOfUserInThisSubscription(@Param("id") Integer idSubscription);

   // List<Subscription> reaserch(String keyword);






}

