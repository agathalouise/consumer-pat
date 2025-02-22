package br.com.alelo.consumer.consumerpat.entities;

import br.com.alelo.consumer.consumerpat.enums.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Table(name = "EXTRACT")
public class ExtractEntity implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  @Id
  @NotNull
  private String id;

  private Integer establishmentId;
  private String establishmentName;
  private String description;
  @NotNull private Date dateOfEvent;
  @NotNull private Long cardNumber;
  @NotNull private BigDecimal amount;

  @Enumerated(EnumType.STRING)
  @NotNull private TransactionType transactionType;

}
