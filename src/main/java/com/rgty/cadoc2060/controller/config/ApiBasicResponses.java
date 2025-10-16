package com.rgty.cadoc2060.controller.config;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@ApiResponses(value = {
        @ApiResponse(responseCode = "400", description = "Bad Request",
                content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorPresenter.class))),
        @ApiResponse(responseCode = "404", description = "Not Found",
                content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorPresenter.class))),
        @ApiResponse(responseCode = "408", description = "Request Timeout",
                content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorPresenter.class))),
        @ApiResponse(responseCode = "422", description = "Unprocessable Entity",
                content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorPresenter.class))),
        @ApiResponse(responseCode = "500", description = "Internal Server Error",
        content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorPresenter.class)))
})
public interface ApiBasicResponses {
}
