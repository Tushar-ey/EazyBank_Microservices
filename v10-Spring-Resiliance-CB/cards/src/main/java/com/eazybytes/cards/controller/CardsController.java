package com.eazybytes.cards.controller;


import com.eazybytes.cards.constants.CardsConstant;
import com.eazybytes.cards.dto.CardsContactInfoDto;
import com.eazybytes.cards.dto.CardsDto;
import com.eazybytes.cards.dto.ErrorResponseDto;
import com.eazybytes.cards.dto.ResponseDto;
import com.eazybytes.cards.service.ICardService;
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
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@Tag(
        name = "CRUD REST APIs for Cards in EazyBank",
        description = "CRUD REST APIs in EazyBank to CREATE, UPDATE, FETCH AND DELETE card details"
)
@RestController
@RequestMapping(path = "/api", produces= {MediaType.APPLICATION_JSON_VALUE})
@Validated
public class CardsController {

    private static  final Logger logger = LoggerFactory.getLogger(CardsController.class);
    private final ICardService iCardsService;

    @Autowired
    public CardsController(ICardService iCardsService) {
        this.iCardsService = iCardsService;
    }

    @Value("${build.version}")
    private String buildVersion;

    @Autowired
    private Environment environment;

    @Autowired
    private CardsContactInfoDto cardsContactInfoDto;


    //----------------Create Card Rest API

    @Operation(
            summary = "Create Card REST API",
            description = "REST API to create new Card inside EazyBank"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "HTTP Status CREATED"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    }
    )
    @PostMapping("/create")
    public ResponseEntity<ResponseDto> createCard(@Valid @Pattern(regexp="(^$|[0-9]{10})",message = "Mobile number must be 10 digits") @RequestParam String mobileNumber){

        iCardsService.createCard(mobileNumber);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseDto(CardsConstant.STATUS_201,CardsConstant.MESSAGE_201));

    }

    //------------------------fetch Api of Cards------------------------------------------

    @Operation(
            summary = "Fetch Card Details REST API",
            description = "REST API to fetch card details based on a mobile number"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    })

    @GetMapping("/fetch")
    public ResponseEntity<CardsDto> fetchCardDetails(@RequestHeader("eazybank-correlation-id")String correlationId,
                                                        @Valid
                                                        @Pattern(regexp="(^$|[0-9]{10})",message = "Mobile number must be 10 digits")
                                                        @RequestParam String mobileNumber) {
        logger.debug("eazyBank-correlation-id found in CardsController : {}", correlationId);
       CardsDto cardsDto = iCardsService.fetchCard(mobileNumber);
       return ResponseEntity.status(HttpStatus.OK).body(cardsDto);
    }



    //----------------------Update Card Api---------------------------------------------

    @Operation(
            summary = "Update Card Details REST API",
            description = "REST API to update card details based on a card number"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "417",
                    description = "Expectation Failed"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    })

    @PutMapping("/update")
    public ResponseEntity<ResponseDto> updateCardDetails(@Valid @RequestBody CardsDto cardsDto)
    {
        boolean isUpdated = iCardsService.updateCard(cardsDto);
        if (isUpdated) {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto(CardsConstant.STATUS_200, CardsConstant.MESSAGE_200));
        } else {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseDto(CardsConstant.STATUS_417, CardsConstant.MESSAGE_417_UPDATE));
        }

    }


    //------------------------Delete Card Api--------------------------------------------

    @Operation(
            summary = "Delete Card Details REST API",
            description = "REST API to delete Card details based on a mobile number"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "417",
                    description = "Expectation Failed"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    })

    @DeleteMapping("/delete")
    public ResponseEntity<ResponseDto> deleteCardDetails(@RequestParam String mobileNumber)
    {
        boolean isDeleted = iCardsService.deleteCard(mobileNumber);
        if (isDeleted) {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto(CardsConstant.STATUS_200, CardsConstant.MESSAGE_200));
        } else {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseDto(CardsConstant.STATUS_417, CardsConstant.MESSAGE_417_DELETE));
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
    @GetMapping("/build-info")
    public ResponseEntity<String> getBuildInfo(){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(buildVersion);
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
    @GetMapping("/java-version")
    public ResponseEntity<String> getJavaVersion(){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(environment.getProperty("jAVA_HOME"));
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
    public ResponseEntity<CardsContactInfoDto> getContactInfo(){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(cardsContactInfoDto);
    }

}
