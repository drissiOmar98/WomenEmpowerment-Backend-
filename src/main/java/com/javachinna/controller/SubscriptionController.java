package com.javachinna.controller;


import com.javachinna.model.Subscription;
import com.javachinna.service.ISubscriptionService;
import com.javachinna.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/subscription")
public class SubscriptionController {
	@Autowired
    ISubscriptionService subscriptionService;
	@Autowired
    UserService userService;


    //http://localhost:8089/api/subscription/addSubscription
    @PostMapping("/addSubscription")
    @ResponseBody
    public Subscription addSubscription (@RequestBody Subscription subscription){
        return subscriptionService.addSubscription(subscription);
    }
    
  //http://localhost:8089/api/subscription/updateSubscription/{id_subscription}
    @PutMapping("/updateSubscription/{id_subscription}")
    @ResponseBody
    public void updateSubscription (@RequestBody Subscription s){
       subscriptionService.updateSubscription(s);
    }

    
  //http://localhost:8089/api/subscription/retrieveSubscription/{id_subscription}
    @GetMapping("/retrieveSubscription/{id_subscription}")
    @ResponseBody
    public Subscription retrieveSubscription(@PathVariable("id_subscription") Integer id_subscription){
         return subscriptionService.retrieveSubscription(id_subscription);
     }
    
    //http://localhost:8089/api/subscription/retrieveAllSubscriptions
    @GetMapping("/retrieveAllSubscriptions")
    @ResponseBody
    public List<Subscription> getSubscriptions(){
        List<Subscription> listSubscription= subscriptionService.retrieveAllSubscriptions();

        return listSubscription;
    }
  //http://localhost:8089/api/subscription/deleteSubscription/{id_subscription}
    @DeleteMapping("/deleteUser/{id_user}")
    @ResponseBody
    public void deleteSubscription(@PathVariable("id_subscription") Integer id_subscription){
    	subscriptionService.deleteSubscription(id_subscription);
    }
//

    //http://localhost:8089/api/subscription/assignSubscriptionToUser/{id_subs}/{id_user}
    @GetMapping("/assignSubscriptionToUser/{id_subs}/{id_user}")
    @ResponseBody
    public void assignSubscriptionToUser(@PathVariable("id_subs") Integer id_subs, @PathVariable("id_user") Integer id_user )  {
        subscriptionService.assignSubscriptionToUser(id_subs, Long.valueOf(id_user));



    }
    @ApiOperation(value = "get Nbr Subscription By User")
    @GetMapping("/getNbrSubscriptionUser/{idU}")
    @ResponseBody
    public int GetNbrSubscriptionUser(@PathVariable(name = "idU") Long idUser){
        return subscriptionService.getNbreSubsByUser(idUser);
    }
    @GetMapping(value  ="/getNumberOfUserInThisSubscription/{id}")
    @ResponseBody
    public Integer  getNumberOfUserInThisSubscription(@PathVariable("id") Integer idSub){
        return subscriptionService.getNumberOfUserInThisSubscription(idSub);
    }
    @PostMapping("/Surprise/{idU}")
    @ResponseBody
    public void  Surprise(@PathVariable("idU") Long idUser)
    {
        subscriptionService.Surprise(idUser);
    }

    @PutMapping("/addLikes/{idS}")
    @ApiOperation(value = " add Likes ")
    public void likeSubsc(@PathVariable(name = "idS") Long idS){
        subscriptionService.likeSub(idS);
    }


    @PutMapping("/addDisLikes/{idS}")
    @ApiOperation(value = " add DisLikes ")
    public void dislikeSubsc(@PathVariable(name = "idS") Long idS )
    {subscriptionService.dislikeSubs(idS);
    }



}



