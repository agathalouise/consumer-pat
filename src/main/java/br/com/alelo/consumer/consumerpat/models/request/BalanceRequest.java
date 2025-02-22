package br.com.alelo.consumer.consumerpat.models.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BalanceRequest implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  @Schema(description = "Amount to be added to the card", example = "100.50")
  private BigDecimal amount;

}
