package com.eazybytes.message.functions;

import com.eazybytes.message.dto.AccountMsgDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Function;


//In this class we define the business logic as functions

    @Configuration
    public class MessageFunctions {
    private static final Logger log = LoggerFactory.getLogger(MessageFunctions.class);

    @Bean
    public Function<AccountMsgDto,AccountMsgDto> email(){
        return (accountMsgDto)-> {
            log.info("Sending email with the details: "+accountMsgDto.toString());
            return accountMsgDto;
        };
    }


// this method send reverse async to accountMicroservice and update the status of communication.
        @Bean
        public Function<AccountMsgDto,Long> sms(){
            return (accountMsgDto)-> {
                log.info("Sending sms with the details: "+accountMsgDto.toString());
                return accountMsgDto.accountNumber();
            };
        }

}