package br.com.alelo.consumer.consumerpat.models.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Positive;
import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CardInfoRequest implements Serializable {

  @Serial private static final long serialVersionUID = 1L;

  @Schema(description = "Gerar um novo cartão")
  private Boolean generateNewCard;

  @Positive(message = "O saldo do cartão deve ser um valor positivo")
  @Schema(description = "Saldo do cartão")
  private BigDecimal cardBalance;

  @JsonIgnore
  private Long cardNumber;

}
