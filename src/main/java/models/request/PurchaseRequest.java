package models.request;

import lombok.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;

import static br.com.alelo.consumer.consumerpat.constants.ConsumerConstants.DEVE_TER_16_DÍGITOS;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseRequest {

  @NonNull
  private int establishmentTypeCode;
  @NotBlank
  private String establishmentName;

  @Min(value = 1000000000000000L, message = DEVE_TER_16_DÍGITOS)
  @Max(value = 9999999999999999L, message = DEVE_TER_16_DÍGITOS)
  @NonNull
  private Long cardNumber; // mudando long pois cartoes possuem 16 dígitos (int só armazena 11 dígitos)

  @NotBlank
  private String productDescription;

  @NotBlank(message = "O campo 'value' não pode estar em branco.")
  @Pattern(regexp = "^[0-9]+$", message = "O campo 'value' deve ser numérico.")
  private String value;

  public BigDecimal getValue() {
    return new BigDecimal(value);
  }
}
