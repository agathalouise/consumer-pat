package br.com.alelo.consumer.consumerpat.entities;

import br.com.alelo.consumer.consumerpat.enums.TransactionType;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;


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
  @GeneratedValue(generator = "UUID")
  @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
  private UUID id;

  private int establishmentId;
  private String establishmentName;
  private String description;
  @NotNull private Date dateOfEvent;
  @NotNull private Long cardNumber;
  @NotNull private BigDecimal amount;

  @Enumerated(EnumType.STRING)
  @NotNull private TransactionType transactionType;

}
