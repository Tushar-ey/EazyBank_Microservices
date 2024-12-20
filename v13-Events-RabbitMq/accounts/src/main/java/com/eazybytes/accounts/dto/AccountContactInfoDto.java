package com.eazybytes.accounts.dto;

// record is a new type of java class in java 17 act as a data carrier
// only provide getter method and ayone can read when create the object of that

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;
import java.util.Map;

@Getter@Setter
@ConfigurationProperties(prefix="accounts")
public class AccountContactInfoDto{

    private String message;
    private Map<String,String> contactDetails;
    private List<String> onCallSupport;

}
