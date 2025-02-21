package br.com.alelo.consumer.consumerpat.controllers;

import br.com.alelo.consumer.consumerpat.services.BalanceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import models.request.BalanceRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/balance")
@Tag(name = "Balance", description = "Operations related to card balance")
public class BalanceController {

  private final BalanceService balanceService;

  @Operation(summary = "Add balance to a beneficiary's card", description = "Credits a specified amount to a given card.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Balance added successfully"),
      @ApiResponse(responseCode = "400", description = "Invalid request parameters", content = @Content),
      @ApiResponse(responseCode = "404", description = "Beneficiary or card not found", content = @Content),
      @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
  })
  @PostMapping("/add/{beneficiaryId}/cards/{cardId}")
  public ResponseEntity<Void> addBalance(
      @Parameter(description = "ID of the beneficiary", example = "123") @PathVariable Long beneficiaryId,
      @Parameter(description = "ID of the card", example = "456") @PathVariable Long cardId,
      @RequestBody BalanceRequest request) {

    balanceService.addBalance(beneficiaryId, cardId, request.getAmount());
    return ResponseEntity.ok().build();
  }
}


