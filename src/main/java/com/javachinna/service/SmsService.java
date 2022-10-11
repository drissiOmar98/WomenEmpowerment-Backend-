package com.javachinna.service;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;

@Component
public class SmsService {
    private final String ACCOUNT_SID ="AC0a2a1d62cd9b8271f58ed642dc166d2c";

    private final String AUTH_TOKEN = "7fd43e0db2f1e65dd9cc36b5aea7ab91";

    private final String FROM_NUMBER = "+15612508509";

    public void send(String to , String message) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

        Message message1 = Message.creator(new PhoneNumber(to), new PhoneNumber(FROM_NUMBER), message)
                .create();
        System.out.println("here is my id:"+message1.getSid());// Unique resource ID created to manage this transaction*/



    }

    public void receive(MultiValueMap<String, String> smscallback) {
    }
}
