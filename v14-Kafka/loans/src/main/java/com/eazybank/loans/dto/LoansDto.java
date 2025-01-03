package com.eazybank.loans.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Schema(name = "Loans",
        description = "Schema to hold Loan information"
)

@Data
public class LoansDto {

    @NotEmpty(message = "Mobile number cannot be empty or null")
    @Pattern(regexp = "^[0-9]{10}$",message = "Mobile number should be of 10 digits")
    @Schema(example = "9991118140", description = "Mobile number of the customer")
    private String mobileNumber;

    @NotEmpty(message = "Loan number cannot be empty or null")
    @Pattern(regexp = "^[0-9]{12}$",message = "Loan number should be of 12 digits")
    @Schema(example = "983458120943", description = "Loan number of the customer")
    private String loanNumber;

    @NotEmpty(message = "Loan type cannot be empty or null")
    @Schema(example = "Home Loan", description = "Loan type of the customer")
    private String loanType;

    @Positive(message = "Total loan amount should be greater than zero")
    @Schema(
            description = "Total loan amount", example = "10000"
    )
    private int totalLoan;


    @PositiveOrZero(message = "Total loan amount paid should be equal or greater than zero")
    @Schema(
            description = "Total loan amount paid", example = "1000"
    )
    private int amountPaid;


    @PositiveOrZero(message = "Total outstanding amount should be equal or greater than zero")
    @Schema(
            description = "Total outstanding amount against a loan", example = "99000"
    )
    private int outstandingAmount;
}
