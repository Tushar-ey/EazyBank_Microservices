package com.eazybytes.accounts.controller;

import com.eazybytes.accounts.dto.CustomerDetailsDto;
import com.eazybytes.accounts.dto.CustomerDto;
import com.eazybytes.accounts.dto.ErrorResponseDto;
import com.eazybytes.accounts.service.impl.ICustomerService;
import com.netflix.discovery.converters.Auto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(name=" REST APIs for Customers in EazyBank",
        description = "REST APIs in EazyBank to fetch customer details")
@RestController
@RequestMapping(path="/api",produces = MediaType.APPLICATION_JSON_VALUE)
@Validated
public class CustomerController {

    private static final Logger logger= LoggerFactory.getLogger(CustomerController.class);

    @Autowired
    private ICustomerService icutomerService;

    @Operation(summary = "FETCH Customer DETAILS REST API", description = "REST API to fetch Customer details based on mobile number")

    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content=@Content(schema = @Schema(implementation = ErrorResponseDto.class))
            )
    })
    @GetMapping("/fetchCustomerDetails")
    public ResponseEntity<CustomerDetailsDto> fetcheustomerDetails(@RequestHeader("eazybank-correlation-id")
                                                                       String correlationId,
                                                                 @RequestParam @Pattern(regexp="(^$|[0-9]{10})",message = "Mobile number must be 10 digits") String mobileNumber){

        logger.debug("eazyBank-correlation-id found in CustomerController : {}", correlationId);
        CustomerDetailsDto customerDetailsDto = icutomerService.fetchCustomerDetails(mobileNumber,correlationId);
         return ResponseEntity.status(HttpStatus.OK).body(customerDetailsDto);

    }

}
