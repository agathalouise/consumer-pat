package br.com.alelo.consumer.consumerpat.models.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.validation.constraints.NotBlank;
import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseRequest implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  @NonNull
  @Schema(description = "Código do tipo de estabelecimento", example = "1")
  private Integer establishmentTypeCode;

  @NotBlank
  @Schema(description = "Nome do estabelecimento", example = "Supermercado XYZ")
  private String establishmentName;

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
