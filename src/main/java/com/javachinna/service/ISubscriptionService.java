package com.javachinna.service;

import com.javachinna.model.Subscription;

import java.util.List;


public interface ISubscriptionService {
	
	Subscription addSubscription(Subscription subsc);
	
	public void updateSubscription( Subscription subsc);
	
	Subscription retrieveSubscription( Integer id);
	
	List<Subscription> retrieveAllSubscriptions();
	 
	 void deleteSubscription ( Integer id);
	 
	 //affectation
    void assignSubscriptionToUser(Integer id_subs, Long id_user);
	int getNbreSubsByUser(Long idUser);


	Integer getNumberOfUserInThisSubscription(Integer idSubscription);
	void  Surprise ( Long idUser);

	//List<Object> getUserRemunerationByDateTrie();

	//List<Subscription>  SearchMultiple(String key);
	void likeSub(Long idS );
	void dislikeSubs (Long idS );
}



