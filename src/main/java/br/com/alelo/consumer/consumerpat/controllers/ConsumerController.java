package br.com.alelo.consumer.consumerpat.controllers;

import br.com.alelo.consumer.consumerpat.services.BalanceService;
import br.com.alelo.consumer.consumerpat.services.ConsumerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import models.request.ConsumerRequest;
import models.response.ConsumerResponse;
import models.response.PaginatedResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Log4j2
@Controller
@RequestMapping("/v1/consumer")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class ConsumerController {

  private final ConsumerService consumerService;

  /* Listar todos os clientes (obs.: tabela possui cerca de 50.000 registros) */

  @GetMapping("/consumerList")
  public ResponseEntity<PaginatedResponse<ConsumerResponse>> listAllConsumers(
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "10") int size) {
    var response = consumerService.listAllConsumers(page, size);
    return ResponseEntity.ok(response);
  }

  /* Cadastrar novos clientes */
  @RequestMapping(value = "/createConsumer", method = RequestMethod.POST)
  public void createConsumer(@RequestBody ConsumerRequest consumer) {

    consumerService.createConsumer(consumer);

  }

  // Atualizar cliente, lembrando que não deve ser possível alterar o saldo do cartão
  @PostMapping("/updateConsumer/{id}")
  public void updateConsumer(@RequestBody ConsumerRequest consumer,
                             @PathVariable Long id) {

    consumerService.updateConsumer(consumer, id);
  }


  //todo arrumar o controller com swagger e os response entities
  //todo criar um novo controller pra regarga de cartao, e dar um novo nome pro service
  // todo salvar o extrato em cada transacao envolvendo dinheiro
  // arrumar as entidades do banco
  // criar uma anotacao para validar os numeros do cartao
  // ver se o enum do type e a classe fazem sentido
  // adicionar logs info e de erros
  // formatar o codigo
  // testar o codigo inlusive o cache
  // ver se cria uma composicao pra classe consumer
  // swagger
  // mapper
  //exception

}
