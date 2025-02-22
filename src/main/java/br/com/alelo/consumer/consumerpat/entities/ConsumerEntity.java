package br.com.alelo.consumer.consumerpat.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import static br.com.alelo.consumer.consumerpat.enums.EstablishmentTypes.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "CONSUMER_DETAILS")
public class ConsumerEntity implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  @Id
  @NotNull
  private String id;

  @NotEmpty
  private String name;

  @Column(unique = true, nullable = false)
  private String documentNumber;

  @NotNull
  @Column(columnDefinition = "DATE")
  private LocalDate birthDate;

  @Embedded
  private Contact contact;

  @Embedded
  private Address address;

  @Embedded
  @AttributeOverrides({
      @AttributeOverride(name = "cardNumber", column = @Column(name = "food_card_number")),
      @AttributeOverride(name = "cardBalance", column = @Column(name = "food_card_balance"))
  })
  private Card foodCard;

  @Embedded
  @AttributeOverrides({
      @AttributeOverride(name = "cardNumber", column = @Column(name = "fuel_card_number")),
      @AttributeOverride(name = "cardBalance", column = @Column(name = "fuel_card_balance"))
  })
  private Card fuelCard;

  @Embedded
  @AttributeOverrides({
      @AttributeOverride(name = "cardNumber", column = @Column(name = "drugstore_card_number")),
      @AttributeOverride(name = "cardBalance", column = @Column(name = "drugstore_card_balance"))
  })
  private Card drugstoreCard;

  public Card getCardByNumber(Long cardNumber) {
    if (foodCard != null && Objects.equals(foodCard.getCardNumber(), cardNumber)) return foodCard;
    if (drugstoreCard != null && Objects.equals(drugstoreCard.getCardNumber(), cardNumber)) return drugstoreCard;
    if (fuelCard != null && Objects.equals(fuelCard.getCardNumber(), cardNumber)) return fuelCard;
    return null;
  }

  public Card getCardByNumber(Long cardNumber, EstablishmentTypesEntity establishmentType) {

    if ((foodCard != null && Objects.equals(foodCard.getCardNumber(), cardNumber))
        && establishmentType.getName().equalsIgnoreCase(FOOD.name())) {
      return foodCard;
    }

    if ((drugstoreCard != null && Objects.equals(drugstoreCard.getCardNumber(), cardNumber))
        && establishmentType.getName().equalsIgnoreCase(DRUGSTORE.name())) {
      return drugstoreCard;
    }

    if ((fuelCard != null
        && Objects.equals(fuelCard.getCardNumber(), cardNumber))
        && establishmentType.getName().equalsIgnoreCase(FUEL.name())) {
      return fuelCard;
    }

    throw new IllegalArgumentException("the card number does not match the establishment type");
  }
}



