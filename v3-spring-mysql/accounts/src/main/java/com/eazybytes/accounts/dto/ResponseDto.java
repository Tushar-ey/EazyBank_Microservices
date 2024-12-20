package com.eazybytes.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(
        name="Response",
        description = "Schema to hold successful response information"
)
public class ResponseDto {

//    public ResponseDto(String statusCode, Object statusMsg) {
//        this.statusCode = statusCode;
//        this.statusMsg = statusMsg;
//    }

    @Schema(
            description = "Status code in the response",
            example = "200"
    )
    private String statusCode;

    @Schema(
            description = "Status message in the response",
            example = "Success"
    )
    private Object statusMsg;

}
