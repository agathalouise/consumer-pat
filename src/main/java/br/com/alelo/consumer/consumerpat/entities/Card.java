package br.com.alelo.consumer.consumerpat.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

@Embeddable
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Card implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  @Column(unique = true)
  private Long cardNumber;

  @Column(precision = 15, scale = 2)
  private BigDecimal cardBalance;

  public void addBalance(BigDecimal value) {
    this.cardBalance = this.cardBalance.add(value);
  }

  public void deductBalance(BigDecimal value) {
    this.cardBalance = this.cardBalance.subtract(value);
  }

}