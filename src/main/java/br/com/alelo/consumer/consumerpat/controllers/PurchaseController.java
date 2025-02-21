package br.com.alelo.consumer.consumerpat.controllers;

import br.com.alelo.consumer.consumerpat.services.PurchaseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import models.request.PurchaseRequest;
import models.response.PurchaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Log4j2
@Controller
@RequestMapping("/v1/purchase")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PurchaseController {

  private final PurchaseService purchaseService;

  @PostMapping("/process")
  public ResponseEntity<PurchaseResponse> processPurchase(@Valid @RequestBody PurchaseRequest request) {
    log.info("Processando compra: {}", request);

    PurchaseResponse response = purchaseService.processPurchase(request);

    return ResponseEntity.ok(response);
  }
}

