package com.eazybytes.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
@Schema(name="Cards",
    description = "Cards details"
)
public class CardsDto {

    @Schema(example = "9991118140",
            description = "Mobile number of the customer")
    @NotEmpty(message = "Mobile number cannot be empty or null")
   @Pattern(regexp = "^[0-9]{10}$",message = "Mobile number should be of 10 digits")
    private String mobileNumber;


    @Schema(example = "98556789443214", description = "Card number of the customer")
    @NotEmpty(message= "cardNumber cannot be a null or empty")
    @Pattern(regexp = "^[0-9]{12}$",message = "Card number should be of 12 digits")
    private String cardNumber;


    @Schema(example = "Credit Card", description = "Card type of the customer")
    @NotEmpty(message= "cardType cannot be a null or empty")
    private String cardType;


    @Schema(example = "10000", description = "Total card limit of the customer")
    @Positive(message = "Total card limit should be greater than zero")
    private int totalLimit;


    @Schema(example = "10000",description = "Amount used by the customer")
    @PositiveOrZero(message = "Amount used should be greater than or equal to zero")
    private int amountUsed;


    @Schema(example ="90000", description = "Available amount of the customer" )
    @PositiveOrZero(message = "Available amount should be greater than or equal to zero")
    private int availableAmount;

}
