package com.eazybytes.accounts.dto;

// record is a new type of java class in java 17 act as a data carrier
// only provide getter method and ayone can read when create the object of that

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;
import java.util.Map;

@ConfigurationProperties(prefix="accounts")
public record AccountContactInfoDto(String message, Map<String,String> contactDetails , List<String> onCallSupport) {


}
