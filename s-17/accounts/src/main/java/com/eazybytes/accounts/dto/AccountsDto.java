package com.eazybytes.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.Getter;

@Data
@Schema(
        name = "Accounts",
        description = "Schema to hold Account Information"
)
public class AccountsDto {

    @Schema(
            description = "Account Number of the Eazy Bank Account"

    )
    @Pattern(regexp="(^$|[0-9]{10})",message = "AccountNumber must be 10 digits")
   @NotEmpty(message = "Account number cant't be empty")
   private Long accountNumber;

    @Schema(
            description = "Eazy Bank Account Type",
            example = "Savings"
    )
    @NotEmpty(message = "Account type can't be null or empty")
    private String accountType;

    @Schema(
            description = "Eazy Bank Branch Address",
            example = "123 New York"
    )
    @NotEmpty(message = "Branch address can't be null or empty")
    private String branchAddress;
}
