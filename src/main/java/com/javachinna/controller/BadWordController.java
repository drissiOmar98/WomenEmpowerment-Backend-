package com.javachinna.controller;


import com.javachinna.config.BadWordConfig;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/dict")
public class BadWordController {

    BadWordConfig badWordConfig  = new BadWordConfig();

    @PostMapping("/{in}")
    public String badWord(@PathVariable("in") String input){

        return  badWordConfig.filterText(input);

    }


 /*

    public void bad() throws IOException {

       OkHttpClient client = new OkHttpClient();
       MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
       RequestBody body = RequestBody.create(mediaType, "content=This%20text%20does%20not%20actually%20contain%20any%20bad%20words!&censor-character=*");
       Request request = new Request.Builder()
               .url("https://neutrinoapi-bad-word-filter.p.rapidapi.com/bad-word-filter")
               .post(body)
               .addHeader("content-type", "application/x-www-form-urlencoded")
               .addHeader("x-rapidapi-host", "neutrinoapi-bad-word-filter.p.rapidapi.com")
               .addHeader("x-rapidapi-key", "7f8e20a1e0msh9e963f92e0f9429p1ddde1jsn748e5d38b7c2")
               .build();

       Response response = client.newCall(request).execute();
   }

  */
}
