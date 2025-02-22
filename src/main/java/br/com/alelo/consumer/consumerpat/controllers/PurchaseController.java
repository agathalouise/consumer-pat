package br.com.alelo.consumer.consumerpat.controllers;

import br.com.alelo.consumer.consumerpat.services.PurchaseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import br.com.alelo.consumer.consumerpat.models.request.PurchaseRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Tag(name = "Purchase", description = "API para processar compras")
@RestController
@RequestMapping("/v1/purchase")
@RequiredArgsConstructor
public class PurchaseController {

  private final PurchaseService purchaseService;

  @Operation(summary = "Processar compra", description = "Processa uma nova compra e retorna a resposta correspondente.")
  @ApiResponse(responseCode = "200", description = "Compra processada com sucesso.")
  @ApiResponse(responseCode = "400", description = "Requisição inválida.")
  @PostMapping("/process")
  public ResponseEntity<Void> processPurchase(@Valid @RequestBody PurchaseRequest request) {
    purchaseService.processPurchase(request);
    return ResponseEntity.ok().build();
  }
}
