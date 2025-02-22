package br.com.alelo.consumer.consumerpat.controllers;

import br.com.alelo.consumer.consumerpat.services.ConsumerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import br.com.alelo.consumer.consumerpat.models.request.ConsumerRequest;
import br.com.alelo.consumer.consumerpat.models.response.ConsumerResponse;
import br.com.alelo.consumer.consumerpat.models.response.PaginatedResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/v1/consumer")
@RequiredArgsConstructor
@Tag(name = "Consumer", description = "Endpoints para gerenciamento de clientes")
public class ConsumerController {

  private final ConsumerService consumerService;


  @Operation(summary = "Listar todos os clientes", description = "Retorna uma lista paginada de clientes")
  @ApiResponse(responseCode = "200", description = "Lista de clientes retornada com sucesso",
      content = @Content(schema = @Schema(implementation = PaginatedResponse.class)))
  @GetMapping("/consumerList")
  public ResponseEntity<PaginatedResponse<ConsumerResponse>> listAllConsumers(
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "10") int size) {

    return ResponseEntity.ok(consumerService.listAllConsumers(page, size));
  }

  @Operation(summary = "Cadastrar um novo cliente", description = "Cria um novo cliente com as informações fornecidas")
  @ApiResponse(responseCode = "201", description = "Cliente cadastrado com sucesso")
  @ApiResponse(responseCode = "400", description = "Requisição inválida")
  @ApiResponse(responseCode = "409", description = "Cliente já cadastrado")
  @PostMapping("/createConsumer")
  @ResponseStatus(HttpStatus.CREATED)
  public ResponseEntity<Void> createConsumer(@Valid @RequestBody ConsumerRequest consumer) {
    consumerService.createConsumer(consumer);
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  @Operation(summary = "Atualizar um cliente", description = "Atualiza os dados do cliente, exceto o saldo do cartão")
  @ApiResponse(responseCode = "200", description = "Cliente atualizado com sucesso")
  @ApiResponse(responseCode = "400", description = "Requisição inválida")
  @ApiResponse(responseCode = "404", description = "Cliente não encontrado")
  @PutMapping("/updateConsumer/{id}")
  public ResponseEntity<Void> updateConsumer(@Valid @RequestBody ConsumerRequest consumer,
                                             @PathVariable String id) {
    consumerService.updateConsumer(consumer, id);
    return ResponseEntity.ok().build();
  }
}
