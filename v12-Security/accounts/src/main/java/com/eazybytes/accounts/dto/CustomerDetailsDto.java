package com.eazybytes.accounts.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(
        name = "CustomerDetailsDto",
        description = "Schema to hold Customer, Account, Cards, Loans Information"
)
public class CustomerDetailsDto {


    @Schema(
            description = "Name of the customer",
            example = "EazyBytes"
    )
    @NotEmpty(message = "Name can't be null or empty")
    @Size(min=5,max=30,message = "The length of the customer name should be between 5 and 30 characters")
    private String name;

    @Schema(
            description = "Email of the customer",
            example = "eazyBytes@gmail.com"
    )
    @NotEmpty(message= "Email address can't be null or empty")
    @Email(message = "Email address should have valid format")
    private String email;

    @Schema(
            description = "Mobile Number of the customer",
            example = "9991118140"
    )
    // @Pattern(regexp = "^[0-9]{10}$",message = "Mobile number should be of 10 digits")
    private String mobileNumber;

    @Schema(
            description = "Accounts Details of the customer"
    )
    private AccountsDto accountsDto;

    @Schema(
            description = "Cards Details of the customer"
    )
    private CardsDto cardsDto;

    @Schema(
            description = "Loans Details of the customer"
    )
    private LoansDto loansDto;


}
