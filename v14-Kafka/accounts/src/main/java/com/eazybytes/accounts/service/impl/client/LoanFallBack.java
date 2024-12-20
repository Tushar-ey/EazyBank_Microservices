package com.eazybytes.accounts.service.impl.client;

import com.eazybytes.accounts.dto.LoansDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class LoanFallBack implements  LoansFeignClient{
    @Override
    public ResponseEntity<LoansDto> fetchLoan(String correlationId, String mobileNumber) {
        return null;
    }
}
