package br.com.alelo.consumer.consumerpat.mappers;

import br.com.alelo.consumer.consumerpat.entities.Address;
import br.com.alelo.consumer.consumerpat.entities.Card;
import br.com.alelo.consumer.consumerpat.entities.ConsumerEntity;
import br.com.alelo.consumer.consumerpat.entities.Contact;
import br.com.alelo.consumer.consumerpat.models.AddressInfo;
import br.com.alelo.consumer.consumerpat.models.CardInfo;
import br.com.alelo.consumer.consumerpat.models.ContactInfo;
import br.com.alelo.consumer.consumerpat.models.request.ConsumerRequest;
import br.com.alelo.consumer.consumerpat.models.response.ConsumerResponse;

public class ConsumerMapper {

  //NA VIDA REAL PODERIAMOS UTILIZAR O MAPSTRUCT OU OUTRA LIB PRA EVITAR O CODIGO ABAIXO

  /**
   * Converts a ConsumerRequest into a ConsumerEntity, which represents a consumer in the database.
   *
   * @param consumerRequest the ConsumerRequest to be converted
   * @return a ConsumerEntity with the fields filled in from the given ConsumerRequest
   */
  public ConsumerEntity toConsumerEntity(ConsumerRequest consumerRequest) {
    ConsumerEntity consumerEntity = new ConsumerEntity();

    // Verify if the fields are not null before accessing them
    if (consumerRequest.getName() != null) {
      consumerEntity.setName(consumerRequest.getName());
    }
    if (consumerRequest.getDocumentNumber() != null) {
      consumerEntity.setDocumentNumber(consumerRequest.getDocumentNumber());
    }
    if (consumerRequest.getBirthDate() != null) {
      consumerEntity.setBirthDate(consumerRequest.getBirthDate());
    }

    if (consumerRequest.getContactInfo() != null) {
      consumerEntity.setContact(toContact(consumerRequest));
    }

    if (consumerRequest.getAddress() != null) {
      consumerEntity.setAddress(toAddress(consumerRequest));
    }

    if (consumerRequest.getFoodCard() != null) {
      consumerEntity.setFoodCard(toCard(consumerRequest.getFoodCard()));
    }

    if (consumerRequest.getFuelCard() != null) {
      consumerEntity.setFuelCard(toCard(consumerRequest.getFuelCard()));
    }

    if (consumerRequest.getDrugstoreCard() != null) {
      consumerEntity.setDrugstoreCard(toCard(consumerRequest.getDrugstoreCard()));
    }

    return consumerEntity;
  }

  /**
   * Converts a ConsumerEntity into a ConsumerResponse object.
   *
   * @param consumerEntity the ConsumerEntity to be converted
   * @return a ConsumerResponse with the fields filled in from the given
   * ConsumerEntity
   */
  public ConsumerResponse toConsumerResponse(ConsumerEntity consumerEntity) {
    ConsumerResponse consumerResponse = new ConsumerResponse();

    // Verify if the fields are not null before accessing them
    if (consumerEntity.getName() != null) {
      consumerResponse.setName(consumerEntity.getName());
    }
    if (consumerEntity.getDocumentNumber() != null) {
      consumerResponse.setDocumentNumber(consumerEntity.getDocumentNumber());
    }
    if (consumerEntity.getBirthDate() != null) {
      consumerResponse.setBirthDate(consumerEntity.getBirthDate());
    }

    if (consumerEntity.getContact() != null) {
      consumerResponse.setContactInfo(toContact(consumerEntity));
    }

    if (consumerEntity.getAddress() != null) {
      consumerResponse.setAddress(toAddress(consumerEntity));
    }

    if (consumerEntity.getFoodCard() != null) {
      consumerResponse.setFoodCard(toCard(consumerEntity.getFoodCard()));
    }

    if (consumerEntity.getFuelCard() != null) {
      consumerResponse.setFuelCard(toCard(consumerEntity.getFuelCard()));
    }

    if (consumerEntity.getDrugstoreCard() != null) {
      consumerResponse.setDrugstoreCard(toCard(consumerEntity.getDrugstoreCard()));
    }

    return consumerResponse;
  }

  /**
   * Converts a Card into a CardInfo.
   *
   * @param card the Card to be converted
   * @return a CardInfo with the card number and balance from the given Card
   */
  private CardInfo toCard(Card card) {
    CardInfo cardInfo = new CardInfo();

    if (card.getCardNumber() != null) {
      cardInfo.setCardNumber(card.getCardNumber());
    }
    if (card.getCardBalance() != null) {
      cardInfo.setCardBalance(card.getCardBalance());
    }

    return cardInfo;
  }

  /**
   * Converts a CardInfo object into a Card entity.
   *
   * @param cardInfo the CardInfo object containing card details
   * @return a Card entity with the card number and balance from the CardInfo
   */
  private Card toCard(CardInfo cardInfo) {
    Card card = new Card();

    if (cardInfo.getCardNumber() != null) {
      card.setCardNumber(cardInfo.getCardNumber());
    }
    if (cardInfo.getCardBalance() != null) {
      card.setCardBalance(cardInfo.getCardBalance());
    }

    return card;
  }


  /**
   * Converts an Address entity into an Address object.
   *
   * @param consumerEntity the ConsumerEntity containing the address
   * @return an Address object with the street, number, city, country and postal code
   * from the given ConsumerEntity
   */
  private AddressInfo toAddress(ConsumerEntity consumerEntity) {
    AddressInfo address = new AddressInfo();

    if (consumerEntity.getAddress().getStreet() != null) {
      address.setStreet(consumerEntity.getAddress().getStreet());
    }
    if (consumerEntity.getAddress().getNumber() != 0) {
      address.setNumber(consumerEntity.getAddress().getNumber());
    }
    if (consumerEntity.getAddress().getCity() != null) {
      address.setCity(consumerEntity.getAddress().getCity());
    }
    if (consumerEntity.getAddress().getCountry() != null) {
      address.setCountry(consumerEntity.getAddress().getCountry());
    }
    if (consumerEntity.getAddress().getPostalCode() != 0) {
      address.setPostalCode(consumerEntity.getAddress().getPostalCode());
    }

    return address;
  }

  /**
   * Converts an Address object into an Address entity.
   *
   * @param consumerRequest the ConsumerRequest object containing the address
   * @return an Address entity with the street, number, city, country and postal code
   * from the given ConsumerRequest
   */
  private Address toAddress(ConsumerRequest consumerRequest) {
    Address address = new Address();

    if (consumerRequest.getAddress().getStreet() != null) {
      address.setStreet(consumerRequest.getAddress().getStreet());
    }
    if (consumerRequest.getAddress().getNumber() != null) {
      address.setNumber(consumerRequest.getAddress().getNumber());
    }
    if (consumerRequest.getAddress().getCity() != null) {
      address.setCity(consumerRequest.getAddress().getCity());
    }
    if (consumerRequest.getAddress().getCountry() != null) {
      address.setCountry(consumerRequest.getAddress().getCountry());
    }
    if (consumerRequest.getAddress().getPostalCode() != null) {
      address.setPostalCode(consumerRequest.getAddress().getPostalCode());
    }

    return address;
  }

  /**
   * Converts a ConsumerRequest into a ContactInfo object.
   *
   * @param consumerRequest the consumer request containing contact details
   * @return a ContactInfo object with the details from the consumer request
   */
  private Contact toContact(ConsumerRequest consumerRequest) {
    Contact contact = new Contact();

    if (consumerRequest.getContactInfo() != null) {
      if (consumerRequest.getContactInfo().getPhoneNumber() != null) {
        contact.setPhoneNumber(consumerRequest.getContactInfo().getPhoneNumber());
      }
      if (consumerRequest.getContactInfo().getMobilePhoneNumber() != null) {
        contact.setMobilePhoneNumber(consumerRequest.getContactInfo().getMobilePhoneNumber());
      }
      if (consumerRequest.getContactInfo().getResidencePhoneNumber() != null) {
        contact.setResidencePhoneNumber(consumerRequest.getContactInfo().getResidencePhoneNumber());
      }
      if (consumerRequest.getContactInfo().getEmail() != null) {
        contact.setEmail(consumerRequest.getContactInfo().getEmail());
      }
    }

    return contact;
  }

  /**
   * Converts a ConsumerEntity's Contact into a ContactInfo.
   *
   * @param consumerEntity the ConsumerEntity to be converted
   * @return a ContactInfo with the phone numbers and email from the given
   * ConsumerEntity
   */
  private ContactInfo toContact(ConsumerEntity consumerEntity) {
    ContactInfo contact = new ContactInfo();

    if (consumerEntity.getContact() != null) {
      if (consumerEntity.getContact().getPhoneNumber() != null) {
        contact.setPhoneNumber(consumerEntity.getContact().getPhoneNumber());
      }
      if (consumerEntity.getContact().getMobilePhoneNumber() != null) {
        contact.setMobilePhoneNumber(consumerEntity.getContact().getMobilePhoneNumber());
      }
      if (consumerEntity.getContact().getResidencePhoneNumber() != null) {
        contact.setResidencePhoneNumber(consumerEntity.getContact().getResidencePhoneNumber());
      }
      if (consumerEntity.getContact().getEmail() != null) {
        contact.setEmail(consumerEntity.getContact().getEmail());
      }
    }

    return contact;
  }
}
