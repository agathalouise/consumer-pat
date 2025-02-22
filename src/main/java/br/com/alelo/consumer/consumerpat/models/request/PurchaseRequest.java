package br.com.alelo.consumer.consumerpat.models.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;

import static br.com.alelo.consumer.consumerpat.constants.ConsumerConstants.DEVE_TER_16_DIGITOS;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseRequest {

  @NonNull
  @Schema(description = "Código do tipo de estabelecimento", example = "1")
  private Integer establishmentTypeCode;

  @NotBlank
  @Schema(description = "Nome do estabelecimento", example = "Supermercado XYZ")
  private String establishmentName;

  @Min(value = 1000000000000000L, message = DEVE_TER_16_DIGITOS)
  @Max(value = 9999999999999999L, message = DEVE_TER_16_DIGITOS)
  @NonNull
  @Schema(description = "Número do cartão", example = "1234567812345678")
  private Long cardNumber; // mudando p/ long pois cartoes possuem 16 dígitos (int só armazena 11 dígitos)

  @NotBlank
  @Schema(description = "Descrição do produto", example = "Produto A")
  private String productDescription;

  @NonNull
  @Schema(description = "Valor da compra", example = "100")
  private BigDecimal value;

}
