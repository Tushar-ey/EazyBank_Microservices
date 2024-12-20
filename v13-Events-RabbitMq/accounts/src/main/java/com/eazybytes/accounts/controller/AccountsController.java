package com.eazybytes.accounts.controller;


import com.eazybytes.accounts.constants.AccountsConstants;
import com.eazybytes.accounts.dto.*;
import com.eazybytes.accounts.service.IAccountsService;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.TimeoutException;


@Tag(name="CRUD REST APIs for Accounts in EazyBank",
      description = "CRUD REST APIs in EazyBank to CREATE,UPDATE,FETCH,AND DELETE account details")
@RestController
@RequestMapping(path="/api",produces = MediaType.APPLICATION_JSON_VALUE)
@Validated
public class AccountsController {

    private static final Logger logger = LoggerFactory.getLogger(AccountsController.class);

    private final IAccountsService iAccountsService;

    @Autowired
    public AccountsController(IAccountsService iAccountsService) {
        this.iAccountsService = iAccountsService;
    }
    //--------- inject build version property ------------

    @Value("${build.version}")
    private String buildVersion;

    //------------------ reading properties using environment variables----------------------

    @Autowired
    private Environment environment;

    //---------------------- autowiring AccountContactInfoDto----------------------

    @Autowired
    private AccountContactInfoDto accountContactInfoDto;


  // ------------------------  CREATE API FOR CUSTOMER & ACCOUNTS ------------------------



    @Operation(summary = "Create Account REST API", description = "REST API to create new Customer & Account inside EazyBank")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "HTTP Status Created"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content=@Content(schema = @Schema(implementation = ErrorResponseDto.class))
            )
    })



    @PostMapping("/create")
    public ResponseEntity<ResponseDto> createAccount(@Valid @RequestBody CustomerDto customerDto){
       iAccountsService.createAccount(customerDto);
      return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto(AccountsConstants.STATUS_201,AccountsConstants.MESSAGE_201));
    }


    // ------------------------  FETCH API FOR CUSTOMER & ACCOUNTS ------------------------

    @Operation(summary = "FETCH Account DETAILS REST API", description = "REST API to fetch Customer & Account details based on mobile number")

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

    @GetMapping("/fetch")
    public ResponseEntity<CustomerDto> getCustomerDetails(@RequestParam
                                                              @Pattern(regexp="(^$|[0-9]{10})",message = "Mobile number must be 10 digits") String mobileNumber
                                                           )
    {
        CustomerDto customerDto = iAccountsService.fetchAccount(mobileNumber);
       return ResponseEntity.status(HttpStatus.OK).body(customerDto);
    }

//------------------------  UPDATE API FOR CUSTOMER & ACCOUNTS ------------------------

    @Operation(summary="UPDATE ACCOUNT Details Rest API", description = "Update operation of Accounts and Customer")
    @ApiResponses({
        @ApiResponse(
                responseCode = "200",
                description = "HTTP Status OK"
        ),
            @ApiResponse(
                    responseCode = "417",
                    description = "HTTP Status Expectation Failed"
            ),
        @ApiResponse(
                responseCode = "500",
                description = "HTTP Status Internal Server Error",
                content=@Content(schema = @Schema(implementation = ErrorResponseDto.class))
        )
    })
    @PutMapping("/update")
    public ResponseEntity<ResponseDto> updateAccountDetails(@Valid @RequestBody CustomerDto customerDto)
    {
        boolean isUpdated = iAccountsService.updateAccount(customerDto);
        if(isUpdated) {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto(AccountsConstants.STATUS_200, AccountsConstants.MESSAGE_200));
        }
            else{
                return ResponseEntity
                        .status(HttpStatus.EXPECTATION_FAILED).body(new ResponseDto(AccountsConstants.STATUS_417, AccountsConstants.MESSAGE_417_UPDATE));
            }
    }


    // ------------------------  DELETE API FOR CUSTOMER & ACCOUNTS ------------------------

    @Operation(summary = "DELETE ACCOUNT REST API", description = "Delete operation of Accounts and Customer")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "417",
                    description = "HTTP Status Expectation Failed"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error"
            )
    })

    @DeleteMapping("/delete")
    public ResponseEntity<ResponseDto> deleteAccountDetails(@RequestParam String mobileNumber){
        boolean isDeleted = iAccountsService.deleteAccount(mobileNumber);
        if(isDeleted){
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDto(AccountsConstants.STATUS_200,AccountsConstants.MESSAGE_200));
        }
        else{
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseDto(AccountsConstants.STATUS_417,AccountsConstants.MESSAGE_417_DELETE));
        }

    }

    //------------------ Rest APi for Build Version and using  ----------------------

    @Operation(summary = "Get Build Information", description = "Get Build information that is deployed into account service")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "HTTP Status Created"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content=@Content(schema = @Schema(implementation = ErrorResponseDto.class))
            )
    })

    @Retry(name="getBuildInfo", fallbackMethod = "getBuildInfoFallback")
    @GetMapping("/build-info")
    public ResponseEntity<String> getBuildInfo() throws TimeoutException {
        logger.debug("getBuildInfo() method is invoked");
       // throw new TimeoutException();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(buildVersion);
    }

    //--------------------FallBackmethod for retry pattern--------------------
    public ResponseEntity<String> getBuildInfoFallback(Throwable throwable){
        logger.debug("getBuildInfoFallBack() method is invoked");
        return ResponseEntity
                .status(HttpStatus.OK)
                .body("0.9");
    }


    //----------------Rest API for JAVA Version using Environment variables------------------------

    @Operation(summary = "Get JAVA version information", description = "Get JAVA Version information that is insatlled into account service")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "HTTP Status Created"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content=@Content(schema = @Schema(implementation = ErrorResponseDto.class))
            )
    })
    @RateLimiter(name = "getJavaVersion", fallbackMethod = "getJavaVersionFallback")
    @GetMapping("/java-version")
    public ResponseEntity<String> getJavaVersion(){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(environment.getProperty("jAVA_HOME"));
    }

    public ResponseEntity<String> getJavaVersionFallback(Throwable throwable){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body("jAVA_17");
    }

    //---------------------------Rest api for contact info using the @configured properties------------

    @Operation(summary = "Get contact info", description = "Contact info details that can be reached out in case of any issue")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "HTTP Status Created"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content=@Content(schema = @Schema(implementation = ErrorResponseDto.class))
            )
    })
    @GetMapping("/contact-info")
    public ResponseEntity<AccountContactInfoDto> getContactInfo(){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(accountContactInfoDto);
    }


}
