package br.com.alelo.consumer.consumerpat.models;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;


import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

import static br.com.alelo.consumer.consumerpat.constants.ConsumerConstants.DEVE_TER_16_DIGITOS;

@Data
public class CardInfo implements Serializable {
  @Serial private static final long serialVersionUID = 1L;

  @Min(value = 1000000000000000L, message = DEVE_TER_16_DIGITOS)
  @Max(value = 9999999999999999L, message = DEVE_TER_16_DIGITOS)
  @Schema(description = "Número do cartão com 16 dígitos")
  private Long cardNumber;

  @Positive(message = "O saldo do cartão deve ser um valor positivo")
  @Schema(description = "Saldo do cartão")
  private BigDecimal cardBalance;
}
