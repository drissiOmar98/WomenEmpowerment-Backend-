package com.javachinna.controller;


import com.javachinna.model.Complaint;
import com.javachinna.model.Topic;
import com.javachinna.service.ITopicService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping("/Topic")
public class TopicRestController

{

    @Autowired
    ITopicService iTopicService;


    @PostMapping("/addTopic")
    public void AddTopic(@RequestBody Topic topic)
    {iTopicService.AddTopic(topic);}

    @PostMapping("/addTopic/{idUser}")
   public void AddAffectTopicList(@RequestBody Topic topic, @PathVariable("idUser") Long idUser) {
        iTopicService.AddAffectTopicList(topic,idUser);
    }

    @ApiOperation(value = "Update Topic")
    @PutMapping("/updateTopic/{idUser}")
    @ResponseBody

    public Topic updateTopic(@RequestBody Topic topic, @PathVariable("idUser") Long idUser)
    {
        return iTopicService.upDateTopic(topic,idUser);
    }


    @ApiOperation(value = "See All topics list")
    @GetMapping("/getAllTopics")
    @ResponseBody
    public List<Topic> getAllTopics()
        { return iTopicService.getAllTopics(); }


        @ApiOperation(value = "Delete Topic")
        @DeleteMapping("/deleteTopic")
        @ResponseBody

        public void deleteTopic(Long idTopic)
        {
            iTopicService.deleteTopic(idTopic);
        }



        @ApiOperation(value ="Give the nbr of comment per topic")
        @GetMapping("/getNbrCommentTopic/{idTopic}")
        @ResponseBody
        public Integer getNbrCommentTopic(@PathVariable("idTopic") Long idTopic){
        return iTopicService.getNbrCommentTopic(idTopic);
    }

    @PutMapping("/TopicWIthRate/{idTopic}/{nbr}")
    @ApiOperation(value = " add Rating ")
    public void TopicWIthRate(@PathVariable(name = "idTopic") Long idTopic,@PathVariable(name = "nbr") Double rate)
    {
        iTopicService.TopicWithRate(idTopic, rate);
    }


    @ApiOperation(value = " Search Topic Multiple  ")
    @GetMapping("/SearchMultiple/{keyword}")
    @ResponseBody
    public List<Topic> SearchTopictMultiple(@PathVariable("keyword")  String key){
        return  iTopicService.SearchTopicMultiple(key);

    }
}
