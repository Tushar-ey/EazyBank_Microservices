package com.eazybytes.accounts.service.impl.client;


import com.eazybytes.accounts.dto.CardsDto;
import com.eazybytes.accounts.dto.LoansDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

//logical name of microservice is loans

@FeignClient(name = "loans", fallback = LoanFallBack.class)
public interface LoansFeignClient {

    @GetMapping(value = "/api/fetch", consumes = "application/json")
    public ResponseEntity<LoansDto> fetchLoan(@RequestHeader("eazybank-correlation-id")String correlationId, @RequestParam String mobileNumber);

}
